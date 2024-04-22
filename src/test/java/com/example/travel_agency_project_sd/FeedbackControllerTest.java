package com.example.travel_agency_project_sd;

import controller.FeedbackController;
import models.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import services.AgentService;
import services.FeedbackService;
import services.ProjectService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private AgentService agentService;

    @MockBean
    private ProjectService projectService;

    @Test
    public void testGiveFeedback() throws Exception {
        String feedbackText = "I enjoyed it very much and I want to do it again!";
        Long projectId = 2L;
        String clientMail = "miruna@yahoo.com";

        doNothing().when(feedbackService).saveFeedback(any(Feedback.class));

        String requestBody = "{ \"projectId\": 2, \"clientMail\": \"miruna@yahoo.com\", \"feedback\": \"I enjoyed it very much and I want to do it again!\" }";

        mockMvc.perform(post("/api/feedback/give_feedback")
                        .param("projectId", "2")
                        .param("clientMail", "miruna@yahoo.com")
                        .param("feedback", "I enjoyed it very much and I want to do it again!"))
                .andExpect(status().isOk());
    }
}
