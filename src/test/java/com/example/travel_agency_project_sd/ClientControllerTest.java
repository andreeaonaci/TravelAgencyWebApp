package com.example.travel_agency_project_sd;

import controller.ClientController;
import models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import services.ClientService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void testClientRegistration_Success() throws Exception {
        // Create a mock Client object to be returned by the registerClient method
        Client mockClient = new Client();
        mockClient.setClientName("Andreea");
        mockClient.setClientFunction("Client");
        mockClient.setClientMail("andreea@yahoo.com");
        mockClient.setClientPhone("0748895562");
        mockClient.setClientPassword("000000000");

        // Mock the registerClient method to return the mockClient object
        when(clientService.registerClient(any(Client.class))).thenReturn(mockClient);

        // Perform the test
        String requestBody = "{ \"clientName\": \"Andreea\", \"clientFunction\": \"Client\", \"clientMail\": \"andreea@yahoo.com\", \"clientPhone\": \"0748895562\", \"clientPassword\": \"000000000\" }";

        mockMvc.perform(post("/api/clients/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    public void testClientLogin_ValidCredentials() throws Exception {
        when(clientService.validateCredentials("andreea@yahoo.com", "000000000")).thenReturn(true);

        String requestBody = "{ \"clientMail\": \"andreea@yahoo.com\", \"clientPassword\": \"000000000\" }";

        mockMvc.perform(post("/api/clients/clientLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("/api/clients/client_dashboard.html?username=andreea@yahoo.com"));
    }

    @Test
    public void testClientLogin_InvalidCredentials() throws Exception {
        when(clientService.validateCredentials("andreea@yahoo.com", "wrongpassword")).thenReturn(false);

        String requestBody = "{ \"clientMail\": \"andreea@yahoo.com\", \"clientPassword\": \"wrongpassword\" }";

        mockMvc.perform(post("/api/clients/clientLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Invalid username or password"));
    }
}
