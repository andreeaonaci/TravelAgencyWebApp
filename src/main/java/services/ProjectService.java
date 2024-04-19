package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.Project;
import repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project findById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElse(null);
    }

    public List<Map<String, Object>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        List<Map<String, Object>> simplifiedProjects = new ArrayList<>();

        for (Project project : projects) {
            System.out.println(project.getProjectId() + " " + project.getName());
            Map<String, Object> simplifiedProject = new HashMap<>();
            simplifiedProject.put("projectId", project.getProjectId());
            simplifiedProject.put("projectName", project.getName());
            simplifiedProjects.add(simplifiedProject);
        }

        return simplifiedProjects;
    }
}
