package models;

import jakarta.persistence.*;

/**
 * Represents a feedback record in the system.
 * Each feedback is associated with a project and a client email.
 */
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int id;

    @Column(name = "feedback_text")
    private String feedbackText;

    @Column(name = "feedback_project")
    private Long feedbackProject;

    @Column(name = "feedback_mail")
    private String feedbackMail;

    /**
     * Gets the unique identifier for this feedback.
     *
     * @return the feedback ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this feedback.
     *
     * @param id the new feedback ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the text of the feedback.
     *
     * @return the feedback text
     */
    public String getFeedbackText() {
        return feedbackText;
    }

    /**
     * Sets the text of the feedback.
     *
     * @param feedbackText the new feedback text
     */
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    /**
     * Gets the project ID associated with this feedback.
     *
     * @return the project ID
     */
    public int getFeedbackProject() {
        return Math.toIntExact(feedbackProject);
    }

    /**
     * Sets the project ID associated with this feedback.
     *
     * @param feedbackProject the new project ID
     */
    public void setFeedbackProject(Long feedbackProject) {
        this.feedbackProject = feedbackProject;
    }

    /**
     * Gets the email of the client who provided this feedback.
     *
     * @return the client's email
     */
    public String getFeedbackMail() {
        return feedbackMail;
    }

    /**
     * Sets the email of the client who provided this feedback.
     *
     * @param feedbackMail the new client's email
     */
    public void setFeedbackMail(String feedbackMail) {
        this.feedbackMail = feedbackMail;
    }
}
