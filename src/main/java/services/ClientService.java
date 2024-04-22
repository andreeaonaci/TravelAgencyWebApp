package services;

import models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Transactional
    public boolean validateCredentials(String username, String password) {
        Client client = clientRepository.findByClientMailAndClientPassword(username, password);
        return client != null;
    }
    @Transactional
    public Client registerClient(Client client) {
        try {
            System.out.println("Registering client: " + client.getClientName());
            return clientRepository.save(client);
        } catch (Exception e) {
            System.err.println("Error registering client: " + e.getMessage());
            throw e;
        }
    }
    @Transactional
    public Client getClientByMail(String clientMail) {
        Client client = clientRepository.findByClientMail(clientMail).orElse(null);
        if (client == null) {
            throw new IllegalArgumentException("Client with given email does not exist.");
        }
        return client;
    }

    @Transactional
    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }
}
