package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.Project;
import repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project findById(Long projectId) {
        // Call the findById method of the ProjectRepository
        return projectRepository.findById(projectId)
                .orElse(null); // Handle the case when project is not found
    }
}
