package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.Project;
import repositories.ProjectRepository;

import java.util.*;

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

    public List<Project> getAllProjectsForTable() {
        List<Project> projectsTable = projectRepository.findAll();
        return projectsTable;
    }

    public int getProjectDuration(Long projectId) {
        Project project = findById(projectId);
        Date projectStart = project.getStart();
        Date projectStop = project.getStop();
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        long startMillis = projectStart.getTime();
        long stopMillis = projectStop.getTime();
        long differenceInMillis = stopMillis - startMillis;
        long durationInDays = differenceInMillis / millisecondsPerDay;
        return (int) durationInDays;
    }

    public String getHotelNameById(int hotelId) {
        Project project = projectRepository.findById(hotelId);
        return project.getHotel();
    }
}
