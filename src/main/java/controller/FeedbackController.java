package controller;

import models.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.ClientService;
import services.FeedbackService;

@Controller
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ClientService clientService;
    @GetMapping("/give_feedback.html")
    public String giveFeedback() {
        return "give_feedback";
    }

    @PostMapping("/give_feedback")
    public String giveFeedbackDashboard(@RequestParam("projectId") Long projectId,
                                        @RequestParam("clientMail") String clientMail,
                                        @RequestParam("feedback") String feedbackText) {
        Feedback feedback = new Feedback();
        feedback.setFeedbackProject(projectId);
        feedback.setFeedbackMail(clientMail);
        feedback.setFeedbackText(feedbackText);

        feedbackService.saveFeedback(feedback);
        return "give_feedback";
    }
}
