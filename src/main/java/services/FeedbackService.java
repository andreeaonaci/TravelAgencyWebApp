package services;

import models.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.FeedbackRepository;

import java.util.List;

/**
 * Service class for managing Feedback entities.
 * Provides operations to save and retrieve feedback.
 */
@Service
public class FeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    @Autowired
    private FeedbackRepository feedbackRepository;

    /**
     * Saves a feedback entry to the repository.
     *
     * @param feedback The feedback object to be saved.
     */
    public void saveFeedback(Feedback feedback) {
        logger.info("Saving feedback from user: {}", feedback.getFeedbackMail());
        feedbackRepository.save(feedback);
    }

    /**
     * Retrieves all feedback from the repository.
     *
     * @return A list of all feedback entries.
     */
    public List<Feedback> getAllFeedbacks() {
        logger.info("Retrieving all feedback");
        return feedbackRepository.findAll();
    }
}
