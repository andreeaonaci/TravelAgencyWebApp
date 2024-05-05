package services;

import models.Project;
import models.Client;
import models.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ReservationRepository;

import java.util.List;
import java.util.ArrayList;

/**
 * Service for managing reservations.
 * This service handles creating, deleting, and fetching reservations for projects and clients.
 */
@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    /**
     * Creates a new reservation for a project by a given client.
     *
     * @param projectId  The ID of the project.
     * @param clientMail The email of the client.
     */
    public void makeReservation(Long projectId, String clientMail) {
        logger.info("Making reservation for project ID: {} by client email: {}", projectId, clientMail);

        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);

        Reservation reservation = new Reservation();
        reservation.setProject(project);
        reservation.setClient(client);

        reservationRepository.save(reservation);

        logger.debug("Reservation made for project {} by client {} ({})", project.getName(), client.getClientName(), client.getClientMail());
    }

    /**
     * Gets the reservation ID for a given project and client.
     *
     * @param projectId  The ID of the project.
     * @param clientMail The email of the client.
     * @return The reservation ID, or null if no reservation is found.
     */
    public Long getReservationId(Long projectId, String clientMail) {
        logger.info("Fetching reservation ID for project ID: {} and client email: {}", projectId, clientMail);

        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);

        Reservation reservation = reservationRepository.findByProjectAndClient(project, client);

        return (reservation != null) ? reservation.getId() : null;
    }

    /**
     * Checks if a reservation exists for a given project and client.
     *
     * @param projectId  The ID of the project.
     * @param clientMail The email of the client.
     * @return True if a reservation exists, false otherwise.
     */
    public boolean checkExistingReservation(Long projectId, String clientMail) {
        logger.info("Checking existing reservation for project ID: {} and client email: {}", projectId, clientMail);

        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);

        Reservation existingReservation = reservationRepository.findByProjectAndClient(project, client);

        return existingReservation != null;
    }

    /**
     * Gets all reservations for a given project and client.
     *
     * @param projectId  The ID of the project.
     * @param clientMail The email of the client.
     * @return A list of reservations.
     */
    public List<Reservation> getReservations(Long projectId, String clientMail) {
        logger.info("Fetching reservations for project ID: {} and client email: {}", projectId, clientMail);

        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);

        return reservationRepository.findAllByProjectAndClient(project, client);
    }

    /**
     * Deletes all existing reservations for a given project and client.
     *
     * @param projectId  The ID of the project.
     * @param clientMail The email of the client.
     */
    public void deleteExistingReservations(Long projectId, String clientMail) {
        logger.info("Deleting all existing reservations for project ID: {} and client email: {}", projectId, clientMail);

        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);

        List<Reservation> existingReservations = reservationRepository.findAllByProjectAndClient(project, client);

        reservationRepository.deleteAll(existingReservations);
    }

    /**
     * Retrieves all project IDs associated with a given client email.
     *
     * @param clientMail The email of the client.
     * @return A list of project IDs.
     */
    public List<Long> getAllProjectsByClient(String clientMail) {
        logger.info("Fetching all project IDs for client email: {}", clientMail);

        Client client = clientService.getClientByMail(clientMail);
        List<Reservation> reservations = reservationRepository.findAllByClient(client);
        List<Long> projectIds = new ArrayList<>();

        for (Reservation reservation : reservations) {
            projectIds.add((long) reservation.getProject().getId());
        }

        return projectIds;
    }

    /**
     * Deletes all reservations by project ID.
     *
     * @param projectId The ID of the project.
     */
    @Transactional
    public void deleteReservationsByProjectId(Long projectId) {
        logger.info("Deleting all reservations for project ID: {}", projectId);

        List<Reservation> reservations = reservationRepository.findByProjectId(projectId);

        if (reservations != null && !reservations.isEmpty()) {
            reservationRepository.deleteAll(reservations);
        }
    }
}
