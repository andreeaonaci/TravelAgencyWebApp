package controller;

import models.Agent;
import models.Feedback;
import models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.AgentService;
import services.ClientService;
import services.FeedbackService;
import services.ProjectService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ClientService clientService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/give_feedback.html")
    public String giveFeedback() {
        return "give_feedback";
    }

    @PostMapping("/give_feedback")
    public String giveFeedbackDashboard(@RequestParam("projectId") Long projectId,
                                        @RequestParam("clientMail") String clientMail,
                                        @RequestParam("feedback") String feedbackText) {
        if (ClientController.getClientSecurity() == null || !ClientController.getClientSecurity().getClientMail().equals(clientMail)) {
            return "redirect:/api/clients/clientLogin";
        }
        Feedback feedback = new Feedback();
        feedback.setFeedbackProject(projectId);
        feedback.setFeedbackMail(clientMail);
        feedback.setFeedbackText(feedbackText);

        feedbackService.saveFeedback(feedback);
        return "give_feedback";
    }

    @GetMapping("/see_feedbacks_dashboard")
    @ResponseBody
    public List<Map<String, Object>> getFeedbacks(@RequestParam(value = "username", required = true) String username) {
        if (AgentController.getAgentSecurity() == null || !AgentController.getAgentSecurity().getAgentMail().equals(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }
        if (username == null || username.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        int agentId = agentService.findByIdAgentMail(username);
        if (agentId == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agent not found");
        }

        List<Project> agentProjects = projectService.getProjectsByAgentId(agentId);
        Set<Integer> agentProjectIds = agentProjects.stream().map(Project::getId).collect(Collectors.toSet());

        // Get all feedbacks
        List<Feedback> allFeedbacks = feedbackService.getAllFeedbacks();

        // Filter feedbacks related to agent's projects
        List<Feedback> agentFeedbacks = allFeedbacks.stream()
                .filter(feedback -> agentProjectIds.contains(feedback.getFeedbackProject()))
                .collect(Collectors.toList());

        // Convert feedbacks to map structure
        List<Map<String, Object>> feedbacksWithAdditionalInfo = new ArrayList<>();
        for (Feedback feedback : agentFeedbacks) {
            Map<String, Object> feedbackMap = new HashMap<>();
            feedbackMap.put("id", feedback.getId());
            feedbackMap.put("projectId", feedback.getFeedbackProject());
            feedbackMap.put("feedbackText", feedback.getFeedbackText());
            feedbackMap.put("clientMail", feedback.getFeedbackMail());
            feedbacksWithAdditionalInfo.add(feedbackMap);
        }

        return feedbacksWithAdditionalInfo;
    }
    @GetMapping("/see_feedbacks.html")
    public String seeFeedbacksDashboard(@RequestParam("username") String username, Model model) {
        if (AgentController.getAgentSecurity() == null || !AgentController.getAgentSecurity().getAgentMail().equals(username)) {
            return "redirect:/api/agents/agentLogin";
        }
        model.addAttribute("username", username);
        List<Map<String, Object>> feedbacks = getFeedbacks(username);
        model.addAttribute("feedbacks", feedbacks);
        return "see_feedbacks";
    }
}
