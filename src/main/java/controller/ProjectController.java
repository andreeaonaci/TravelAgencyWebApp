package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import models.Agent;
import models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.AgentService;
import services.CountryService;
import services.ProjectService;
import services.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private AgentService agentService;
    @GetMapping("/make_reservation.html")
    public String MakeReservationDashboard(@RequestParam("clientMail") String username, Model model) {
        if (ClientController.getClientSecurity() == null || !ClientController.getClientSecurity().getClientMail().equals(username)) {
            return "redirect:/api/clients/clientLogin";
        }
        model.addAttribute("clientMail", username);
        List<Map<String, Object>> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "make_reservation";
    }
    @GetMapping("/view_projects_client.html")
    public String ViewProjectsDashboard(@RequestParam("username") String username, Model model) {
        if (ClientController.getClientSecurity() == null || !ClientController.getClientSecurity().getClientMail().equals(username)) {
            return "redirect:/api/clients/clientLogin";
        }
        model.addAttribute("username", username);
        return "view_projects_client";
    }
    @GetMapping("/all_projects")
    @ResponseBody
    public List<Map<String, Object>> getAllProjects() {
        List<Map<String, Object>> projects = projectService.getAllProjects();
        return projects;
    }

    @GetMapping("/all_projects_dashboard")
    @ResponseBody
    public List<Map<String, Object>> getAllProjectsDashboard() {
        List<Project> projects = projectService.getAllProjectsForTable();
        List<Map<String, Object>> projectsWithCountryAndAgent = new ArrayList<>();

        for (Project project : projects) {
            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("projectId", project.getId());
            projectMap.put("projectName", project.getName());
            String countryName = countryService.getCountryNameById(project.getCountry());
            projectMap.put("countryName", countryName);
            projectMap.put("hotel", project.getHotel());
            projectMap.put("distance", project.getDistance());
            projectMap.put("start", project.getFormattedStartDate());
            projectMap.put("stop", project.getFormattedStopDate());
            String agentName = agentService.findByIdAgent(project.getAgent()).getAgentName();
            projectMap.put("agentName", agentName);
            projectsWithCountryAndAgent.add(projectMap);
        }

        return projectsWithCountryAndAgent;
    }

    @PostMapping("/all_projects_duration")
    @ResponseBody
    public ResponseEntity<?> getProjectByListId(@RequestParam("projectIds") String projectIds) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Long> projectIdList = objectMapper.readValue(projectIds, new TypeReference<List<Long>>() {});

            if (projectIdList == null || projectIdList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project IDs are required");
            }

            Set<Long> uniqueProjectIds = new HashSet<>(projectIdList);
            List<Map<String, Object>> projects = new ArrayList<>();
            LocalDate currentDate = LocalDate.now();

            for (Long projectId : uniqueProjectIds) {
                Project project = projectService.findById(projectId);
                if (project == null) {
                    return ResponseEntity.badRequest().body("Project not found for ID: " + projectId);
                }
                LocalDate stopDate = convertToLocalDate(project.getStop());
                if (stopDate != null && stopDate.isBefore(currentDate)) {
                    Map<String, Object> projectMap = new HashMap<>();
                    projectMap.put("projectId", project.getId());
                    projectMap.put("projectName", project.getName());
                    projects.add(projectMap);
                }
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch project durations: " + e.getMessage());
        }
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @GetMapping("/add_project.html")
    public String addProject(@RequestParam String username, Model model) {
        if (AgentController.getAgentSecurity() == null || !AgentController.getAgentSecurity().getAgentMail().equals(username)) {
            return "redirect:/api/agents/agentLogin";
        }
        model.addAttribute("username", username);
        return "add_project";
    }

    public static Date convertStringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }
    @PostMapping("/add_project")
    public ResponseEntity<Project> addProjectDashboard(@RequestBody Map<String, String> formData) throws ParseException {
        if (AgentController.getAgentSecurity() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }
        if (formData == null || formData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Form data is required");
        }
        Project project = new Project();
        String projectName = formData.get("name");
        String country = formData.get("country");
        int countryId = countryService.getCountryIdByName(country);
        String hotel = formData.get("hotel");
        int distance = Integer.parseInt(formData.get("distance"));
        String startDateString = formData.get("start");
        String endDateString = formData.get("end");
        String agent = formData.get("agent");
        int agentId = agentService.findByIdAgentMail(agent);
        Agent agentObj = agentService.findByIdAgent(agentId);
        if (AgentController.getAgentSecurity() == null || !AgentController.getAgentSecurity().getAgentMail().equals(agentObj.getAgentMail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(startDateString);
        Date endDate = dateFormat.parse(endDateString);

        project.setName(projectName);
        project.setCountry(countryId);
        project.setHotel(hotel);
        project.setDistance(distance);
        project.setStart(startDate);
        project.setStop(endDate);
        project.setAgent(agentId);

        Project projectSaved = projectService.saveProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectSaved);
    }

    @GetMapping("/all_projects_update_dashboard")
    @ResponseBody
    public List<Map<String, Object>> getAllProjectsForAgent(@RequestParam(value = "agentMail", required = true) String agentMail) {
        if (AgentController.getAgentSecurity() == null || !AgentController.getAgentSecurity().getAgentMail().equals(agentMail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }
        if (agentMail == null || agentMail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "agentMail parameter is required");
        }
        int agentId = agentService.findByIdAgentMail(agentMail);
        if (agentId == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found");
        }
        List<Project> projects = projectService.getProjectsByAgentId(agentId);
        List<Map<String, Object>> projectsWithCountryAndAgent = new ArrayList<>();

        for (Project project : projects) {
            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("projectId", project.getId());
            projectMap.put("projectName", project.getName());
            String countryName = countryService.getCountryNameById(project.getCountry());
            projectMap.put("countryName", countryName);
            projectMap.put("hotel", project.getHotel());
            projectMap.put("distance", project.getDistance());
            projectMap.put("start", project.getFormattedStartDate());
            projectMap.put("stop", project.getFormattedStopDate());
            String agentName = agentService.findByIdAgent(project.getAgent()).getAgentName();
            projectMap.put("agentName", agentName);
            projectsWithCountryAndAgent.add(projectMap);
        }

        return projectsWithCountryAndAgent;
    }
    @GetMapping("/view_projects_admin.html")
    public String viewProjectsAgentDashboard(@RequestParam("username") String agentMail, Model model) {
        if (AgentController.getAgentSecurity() == null || !AgentController.getAgentSecurity().getAgentMail().equals(agentMail)) {
            return "redirect:/api/agents/agentLogin";
        }
        model.addAttribute("username", agentMail);
        return "view_projects_admin";
    }
    @PutMapping("/update/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable("projectId") Long projectId, @RequestBody Map<String, Object> updatedProjectData) throws ParseException {
        // Check if project exists
        if (AgentController.getAgentSecurity() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        System.out.println(updatedProjectData.get("country"));
        System.out.println(updatedProjectData.get("agent"));
        System.out.println(updatedProjectData.get("start"));
        System.out.println(updatedProjectData.get("stop"));
        System.out.println(updatedProjectData.get("distance"));

        Project existingProject = projectService.findById(projectId);
        if (existingProject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
        }

        // Extract and validate data
        String projectName = (String) updatedProjectData.get("projectName");
        if (projectName == null || projectName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Project name cannot be empty.");
        }
        System.out.println(updatedProjectData.get("country"));
        // Convert names to IDs
        String countryName = (String) updatedProjectData.get("country");
        int countryId = countryService.getCountryIdByName(countryName); // Convert country name to ID

        String agentName = (String) updatedProjectData.get("agent");
        int agentId = agentService.findByAgentName(agentName); // Convert agent name to ID

        // Update the project
        existingProject.setName(projectName);
        existingProject.setCountry(countryId);
        existingProject.setAgent(agentId);

        // Set other project properties
        existingProject.setHotel((String) updatedProjectData.get("hotel"));
        existingProject.setDistance((Integer) updatedProjectData.get("distance"));
        existingProject.setFormattedStartDate((String) updatedProjectData.get("start"));
        existingProject.setFormattedStopDate((String) updatedProjectData.get("stop"));

        projectService.saveProject(existingProject);

        return ResponseEntity.ok("Project updated successfully.");
    }
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        if (AgentController.getAgentSecurity() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }
        Project existingProject = projectService.findById(projectId);
        if (existingProject == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        }
        Project project = projectService.findById(projectId);
        reservationService.deleteReservationsByProjectId(projectId);
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/updateCountry/{countryName}")
    public ResponseEntity<?> getCountryIdByName(@PathVariable String countryName) {
        if (AgentController.getAgentSecurity() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        int countryId = countryService.getCountryIdByName(countryName);
        if (countryId == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countryId);
    }
}
