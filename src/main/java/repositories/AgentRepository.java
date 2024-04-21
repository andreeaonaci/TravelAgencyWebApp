package repositories;
import models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Agent findByAgentMailAndAgentPassword(String agentMail, String agentPassword);
    Agent findAgentById(int agentId);
    Agent findAgentByAgentMail(String agentMail);
    Agent findAgentByAgentName(String agentName);
    @Query("SELECT a.id FROM Agent a WHERE a.agentName = :agentName")
    Integer findIdByName(String agentName);
}
