package services;

import models.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.AgentRepository;

import java.util.Optional;
/**
 * Service class for handling operations related to Agent entities.
 */
@Service
public class AgentService {
    private static final Logger logger = LoggerFactory.getLogger(AgentService.class);
    @Autowired
    private AgentRepository agentRepository;
    /**
     * Validates the credentials of an agent using the provided username and password.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return True if the credentials are valid, false otherwise.
     */
    @Transactional
    public boolean validateCredentials(String username, String password) {
        logger.info("Validating credentials for agent with username: {}", username);
        Agent agent = agentRepository.findByAgentMailAndAgentPassword(username, password);

        if (agent != null) {
            logger.debug("Agent credentials validated for email: {}", agent.getAgentMail());
            return true;
        } else {
            logger.warn("Failed to validate credentials for username: {}", username);
            return false;
        }
    }
    /**
     * Registers a new agent in the repository.
     *
     * @param agent The agent to register.
     */
    @Transactional
    public void registerAgent(Agent agent) {
        try {
            logger.info("Registering new agent with name: {}", agent.getAgentName());
            agentRepository.save(agent);
        } catch (Exception e) {
            logger.error("Error registering agent with name: {}", agent.getAgentName(), e);
            throw e; // Rethrow to maintain transaction consistency
        }
    }
    /**
     * Finds an agent by their ID.
     *
     * @param agentId The ID of the agent.
     * @return The found agent, or null if no agent is found.
     */
    @Transactional
    public Agent findByIdAgent (int agentId) {
        logger.info("Fetching agent with ID: {}", agentId);
        Agent agent = agentRepository.findAgentById(agentId);

        if (agent == null) {
            logger.warn("Agent with ID {} not found", agentId);
        }

        return agent;
    }
    /**
     * Finds the agent ID by their email address.
     *
     * @param agentMail The email address to search for.
     * @return The agent ID, or throw an exception if the agent is not found.
     */
    @Transactional
    public int findByIdAgentMail (String agentMail) {
        logger.info("Fetching agent ID by email: {}", agentMail);
        Agent agent = agentRepository.findAgentByAgentMail(agentMail);

        if (agent == null) {
            logger.warn("No agent found with email: {}", agentMail);
            throw new IllegalArgumentException("Agent not found");
        }

        return agent.getAgentId();
    }
    /**
     * Finds the agent ID by their name.
     *
     * @param agentName The name to search for.
     * @return The agent ID, or throw an exception if the agent is not found.
     */
    @Transactional
    public int findByAgentName(String agentName) {
        logger.info("Fetching agent ID by name: {}", agentName);
        Agent agent = agentRepository.findAgentByAgentName(agentName);

        if (agent == null) {
            logger.warn("No agent found with name: {}", agentName);
            throw new IllegalArgumentException("Agent not found");
        }

        return agent.getAgentId();
    }
    /**
     * Gets the agent ID by their name as an optional value.
     *
     * @param agentName The name to search for.
     * @return An optional containing the agent ID, or empty if not found.
     */
    @Transactional
    public Optional<Integer> getAgentIdByName(String agentName) {
        logger.info("Fetching agent ID by agent name: {}", agentName);
        Integer agentId = agentRepository.findIdByName(agentName);

        if (agentId == null) {
            logger.warn("Agent ID not found for agent name: {}", agentName);
        }

        return Optional.ofNullable(agentId);
    }
}
