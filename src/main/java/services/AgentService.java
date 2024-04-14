package services;

import models.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AgentRepository;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    public boolean validateCredentials(String username, String password) {
        Agent agent = agentRepository.findByAgentMailAndAgentPassword(username, password);
        System.out.println(agent.getAgentMail() + " " + agent.getAgentPassword());
        return agent != null;
    }
}
