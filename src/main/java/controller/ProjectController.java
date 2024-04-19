package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.ProjectService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @GetMapping("/make_reservation.html")
    public String MakeReservationDashboard(@RequestParam("clientMail") String username, Model model) {
        model.addAttribute("clientMail", username);
        List<Map<String, Object>> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "make_reservation";
    }
    @GetMapping("/view_projects.html")
    public String ViewProjectsDashboard(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "view_projects";
    }
    @GetMapping("/all_projects")
    @ResponseBody
    public List<Map<String, Object>> getAllProjects() {
        System.out.println("Getting all projects");
        List<Map<String, Object>> projects = projectService.getAllProjects();
        for (Map<String, Object> project : projects) {
            System.out.println(project.get("projectId") + " " + project.get("projectName"));
        }
        return projects;
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
}
