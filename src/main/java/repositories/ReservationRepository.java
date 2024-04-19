package repositories;
import models.Client;
import models.Project;
import models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Reservation findByProjectAndClient(Project project, Client client);
    List<Reservation> findAllByProjectAndClient(Project project, Client client);
    List<Reservation> findAllByClient(Client client);
}
