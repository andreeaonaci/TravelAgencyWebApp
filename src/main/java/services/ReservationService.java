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

    public void makeReservation(Long projectId, Long clientId) {
        Project project = projectService.findById(projectId);
        Client client = clientService.getClientById(clientId);
        Reservation reservation = new Reservation();
        reservation.setProject(project);
        reservation.setClient(client);
        reservationRepository.save(reservation);
    }
}
