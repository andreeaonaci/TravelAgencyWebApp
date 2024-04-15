package services;

import models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    public boolean validateCredentials(String username, String password) {
        Client client = clientRepository.findByClientMailAndClientPassword(username, password);
        return client != null;
    }
    public Client registerClient(Client client) {
        try {
            System.out.println("Registering client: " + client.getClientName());
            return clientRepository.save(client);
        } catch (Exception e) {
            System.err.println("Error registering client: " + e.getMessage());
            throw e;
        }
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }
}
