package services;

import models.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.FeedbackRepository;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
}
