package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.Project;
import models.Client;
import models.Reservation;
import repositories.ReservationRepository;

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
}
