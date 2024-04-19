package repositories;
import models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    Client findByClientMailAndClientPassword(String clientMail, String clientPassword);
    Optional<Client> findByClientMail(String clientMail);}
