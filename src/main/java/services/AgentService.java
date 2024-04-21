package services;

import models.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import repositories.AgentRepository;

import java.util.Optional;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    public boolean validateCredentials(String username, String password) {
        Agent agent = agentRepository.findByAgentMailAndAgentPassword(username, password);
        System.out.println(agent.getAgentMail() + " " + agent.getAgentPassword());
        return agent != null;
    }

    public void registerAgent(Agent agent) {
        agentRepository.save(agent);
    }

    public Agent findByIdAgent (int agentId) {
        return agentRepository.findAgentById(agentId);
    }

    public int findByIdAgentMail (String agentMail) {
        return agentRepository.findAgentByAgentMail(agentMail).getAgentId();
    }

    public int findByAgentName(String agentName) {
        return agentRepository.findAgentByAgentName(agentName).getAgentId();
    }

    public Optional<Integer> getAgentIdByName(String agentName) {
        return Optional.ofNullable(agentRepository.findIdByName(agentName));
    }
}
