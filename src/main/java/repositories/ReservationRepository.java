package repositories;
import models.Client;
import models.Project;
import models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Reservation findByProjectAndClient(Project project, Client client);
}
