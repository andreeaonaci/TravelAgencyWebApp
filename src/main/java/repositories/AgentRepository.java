package repositories;
import models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Agent findByAgentMailAndAgentPassword(String agentMail, String agentPassword);

    Agent findAgentById(int agentId);
}
