package services;

import models.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ClientRepository;

/**
 * Service class for handling operations related to Client entities.
 */
@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Validates the credentials of a client by comparing the username and password with the database.
     *
     * @param username The client's email address.
     * @param password The client's password.
     * @return True if the credentials are valid, false otherwise.
     */
    @Transactional
    public boolean validateCredentials(String username, String password) {
        logger.info("Validating credentials for username: {}", username);
        Client client = clientRepository.findByClientMailAndClientPassword(username, password);

        boolean isValid = client != null;
        if (!isValid) {
            logger.warn("Invalid credentials for username: {}", username);
        }
        return isValid;
    }

    /**
     * Registers a new client in the repository.
     *
     * @param client The client to be registered.
     * @return The saved client entity.
     */
    @Transactional
    public Client registerClient(Client client) {
        try {
            logger.info("Registering client: {}", client.getClientName());
            return clientRepository.save(client);
        } catch (Exception e) {
            logger.error("Error registering client: {}", client.getClientName(), e);
            throw e; // Ensure to rethrow the exception to maintain transactional behavior
        }
    }

    /**
     * Fetches a client by their email address.
     *
     * @param clientMail The email address of the client.
     * @return The client found with the given email, or null if not found.
     */
    @Transactional
    public Client getClientByMail(String clientMail) {
        logger.info("Fetching client by email: {}", clientMail);
        Client client = clientRepository.findByClientMail(clientMail).orElse(null);

        if (client == null) {
            logger.warn("Client with email {} does not exist.", clientMail);
            throw new IllegalArgumentException("Client with given email does not exist.");
        }

        return client;
    }

    /**
     * Fetches a client by their ID.
     *
     * @param clientId The ID of the client.
     * @return The client found with the given ID, or null if not found.
     */
    @Transactional
    public Client getClientById(Long clientId) {
        logger.info("Fetching client by ID: {}", clientId);
        Client client = clientRepository.findById(clientId).orElse(null);

        if (client == null) {
            logger.warn("Client with ID {} not found.", clientId);
        }

        return client;
    }
}
