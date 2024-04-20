package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.AgentService;
import services.CountryService;
import services.ProjectService;

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
    private CountryService countryService;

    @Autowired
    private AgentService agentService;
    @GetMapping("/make_reservation.html")
    public String MakeReservationDashboard(@RequestParam("clientMail") String username, Model model) {
        model.addAttribute("clientMail", username);
        List<Map<String, Object>> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "make_reservation";
    }
    @GetMapping("/view_projects_client.html")
    public String ViewProjectsDashboard(@RequestParam("username") String username, Model model) {
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
            projectMap.put("projectId", project.getProjectId());
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
                return ResponseEntity.badRequest().body("No project IDs provided.");
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
        model.addAttribute("username", username);
        return "add_project";
    }

    public static Date convertStringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }
    @PostMapping("/add_project")
    public ResponseEntity<Project> addProjectDashboard(@RequestBody Map<String, String> formData) throws ParseException {
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
}
