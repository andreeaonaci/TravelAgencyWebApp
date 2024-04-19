package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.Project;
import models.Client;
import models.Reservation;
import repositories.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    public void makeReservation(Long projectId, String clientMail) {
        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);
        Reservation reservation = new Reservation();
        reservation.setProject(project);
        reservation.setClient(client);
        System.out.println("Reservation made for project " + project.getName() + " by " + client.getClientName() + " " + " (" + client.getClientMail() + ").");
        reservationRepository.save(reservation);
    }

    public Long getReservationId(Long projectId, String clientMail) {
        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);
        Reservation reservation = reservationRepository.findByProjectAndClient(project, client);
        if (reservation == null) {
            return null;
        }
        return reservation.getId();
    }

    public boolean checkExistingReservation(Long projectId, String clientMail) {
        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);
        Reservation existingReservation = reservationRepository.findByProjectAndClient(project, client);
        return existingReservation != null;
    }

    public List<Reservation> getReservations(Long projectId, String clientMail) {
        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);
        return reservationRepository.findAllByProjectAndClient(project, client);
    }

    public void deleteExistingReservations(Long projectId, String clientMail) {
        Project project = projectService.findById(projectId);
        Client client = clientService.getClientByMail(clientMail);
        List<Reservation> existingReservations = reservationRepository.findAllByProjectAndClient(project, client);
        reservationRepository.deleteAll(existingReservations);
    }

    public List<Long> getAllProjectsByClient(String clientMail) {
        Client client = clientService.getClientByMail(clientMail);
        List<Reservation> reservations = reservationRepository.findAllByClient(client);
        List<Long> projectIds = new java.util.ArrayList<>();
        for (Reservation reservation : reservations) {
            projectIds.add((long) reservation.getProject().getProjectId());
        }
        return projectIds;
    }
}
