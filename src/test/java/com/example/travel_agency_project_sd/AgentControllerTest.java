package com.example.travel_agency_project_sd;

import controller.AgentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import services.AgentService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AgentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgentService agentService;

    @Test
    public void testAgentLogin_Success() throws Exception {
        when(agentService.validateCredentials("bogdan@yahoo.com", "000000000")).thenReturn(true);

        String requestBody = "{ \"agentMail\": \"bogdan@yahoo.com\", \"agentPassword\": \"000000000\" }";

        mockMvc.perform(post("/api/agents/agentLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("/api/agents/agent_dashboard.html?username=bogdan@yahoo.com"));
    }

    @Test
    public void testAgentLogin_Failure() throws Exception {
        when(agentService.validateCredentials("bogdan@yahoo.com", "wrongpassword")).thenReturn(false);

        String requestBody = "{ \"agentMail\": \"bogdan@yahoo.com\", \"agentPassword\": \"wrongpassword\" }";

        mockMvc.perform(post("/api/agents/agentLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));
    }
}
