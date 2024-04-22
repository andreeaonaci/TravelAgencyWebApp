package com.example.travel_agency_project_sd;

import controller.ProjectController;
import models.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import services.AgentService;
import services.CountryService;
import services.ProjectService;

import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private CountryService countryService;

    @MockBean
    private AgentService agentService;

    @Test
    public void testAddProject() throws Exception {
        when(countryService.getCountryIdByName("Japan")).thenReturn(1);
        when(agentService.findByIdAgentMail("agent@example.com")).thenReturn(1);

        String requestBody = "{ \"name\": \"New Project\", \"country\": \"2\", \"hotel\": \"Tokyo Hotel\", \"distance\": 10, \"start\": \"2024-12-21 00:00:00\", \"end\": \"2024-12-24 00:00:00\", \"agent\": \"1\" }";

        Project project = new Project();
        project.setName("New Project");
        project.setCountry(2);
        project.setHotel("Tokyo Hotel");
        project.setDistance(10);
        project.setFormattedStartDate("21.12.2024 12:00am");
        project.setFormattedStopDate("24.12.2024 12:00am");
        project.setAgent(1);

        when(projectService.saveProject(any(Project.class))).thenReturn(project);

        // Perform the request and assert the response
        mockMvc.perform(post("/api/projects/add_project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Project"))
                .andExpect(jsonPath("$.hotel").value("Tokyo Hotel"))
                .andExpect(jsonPath("$.start").value("2024-12-20T22:00:00.000+00:00"));
    }
}
