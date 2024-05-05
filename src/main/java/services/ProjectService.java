package services;

import models.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ProjectRepository;

import java.util.*;

/**
 * Service class for managing Project entities.
 * Provides operations to find, save, and manipulate projects.
 */
@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Finds a project by its ID.
     *
     * @param projectId The ID of the project.
     * @return The found project, or null if not found.
     */
    public Project findById(Long projectId) {
        logger.info("Finding project by ID: {}", projectId);
        return projectRepository.findById(projectId).orElse(null);
    }

    /**
     * Retrieves all projects in a simplified format.
     *
     * @return A list of simplified project maps containing project ID and name.
     */
    public List<Map<String, Object>> getAllProjects() {
        logger.info("Fetching all projects");
        List<Project> projects = projectRepository.findAll();
        List<Map<String, Object>> simplifiedProjects = new ArrayList<>();

        for (Project project : projects) {
            Map<String, Object> simplifiedProject = new HashMap<>();
            simplifiedProject.put("projectId", project.getId());
            simplifiedProject.put("projectName", project.getName());
            simplifiedProjects.add(simplifiedProject);
        }

        return simplifiedProjects;
    }

    /**
     * Retrieves all projects for table representation.
     *
     * @return A list of all projects.
     */
    public List<Project> getAllProjectsForTable() {
        logger.info("Fetching all projects for table representation");
        return projectRepository.findAll();
    }

    /**
     * Gets the duration of a project in days.
     *
     * @param projectId The ID of the project.
     * @return The duration in days.
     * @throws IllegalArgumentException if the project does not exist.
     */
    public int getProjectDuration(Long projectId) {
        logger.info("Calculating project duration for project ID: {}", projectId);
        Project project = findById(projectId);

        if (project == null) {
            logger.warn("Project with ID {} not found", projectId);
            throw new IllegalArgumentException("Project not found");
        }

        Date projectStart = project.getStart();
        Date projectStop = project.getStop();

        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        long startMillis = projectStart.getTime();
        long stopMillis = projectStop.getTime();
        long differenceInMillis = stopMillis - startMillis;
        long durationInDays = differenceInMillis / millisecondsPerDay;

        return (int) durationInDays;
    }

    /**
     * Gets the hotel name by its ID.
     *
     * @param hotelId The ID of the hotel.
     * @return The name of the hotel.
     * @throws IllegalArgumentException if the hotel does not exist.
     */
    public String getHotelNameById(int hotelId) {
        logger.info("Getting hotel name by hotel ID: {}", hotelId);
        Project project = projectRepository.findById((long) hotelId);

        if (project == null) {
            logger.warn("Hotel with ID {} not found", hotelId);
            throw new IllegalArgumentException("Hotel not found");
        }

        return project.getHotel();
    }

    /**
     * Saves a new project to the repository.
     *
     * @param project The project to save.
     * @return The saved project.
     */
    public Project saveProject(Project project) {
        logger.info("Saving project: {}", project.getName());
        projectRepository.save(project);
        return project;
    }

    /**
     * Retrieves projects by agent ID.
     *
     * @param agentId The ID of the agent.
     * @return A list of projects assigned to the given agent.
     */
    public List<Project> getProjectsByAgentId(int agentId) {
        logger.info("Fetching projects for agent ID: {}", agentId);
        return projectRepository.findByAgent(agentId);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param projectId The ID of the project to delete.
     */
    public void deleteProject(Long projectId) {
        logger.info("Deleting project with ID: {}", projectId);
        projectRepository.deleteById(projectId);
    }

    /**
     * Gets the country ID by hotel name.
     *
     * @param hotel The name of the hotel.
     * @return The country ID where the hotel is located.
     */
    public int getCountryIdByHotel(String hotel) {
        logger.info("Getting country ID by hotel name: {}", hotel);
        Project project = projectRepository.findByHotel(hotel);

        if (project == null) {
            logger.warn("Project with hotel {} not found", hotel);
            throw new IllegalArgumentException("Project with given hotel not found");
        }

        return project.getCountry();
    }
}
