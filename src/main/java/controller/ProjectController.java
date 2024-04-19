package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.ProjectService;

import java.util.List;
import java.util.Map;

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
}
