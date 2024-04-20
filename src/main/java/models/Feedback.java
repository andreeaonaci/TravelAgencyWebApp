package models;

import jakarta.persistence.*;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getFeedbackMail() {
        return feedbackMail;
    }

    public void setFeedbackMail(String feedbackMail) {
        this.feedbackMail = feedbackMail;
    }

//    public Client getClient() {
//        return client;
//    }

    public int getFeedbackProject() {
        return Math.toIntExact(feedbackProject);
    }

    public void setFeedbackProject(Long feedbackProject) {
        this.feedbackProject = feedbackProject;
    }

//    public void setClient(Client client) {
//        this.client = client;
//    }

    // Define a Many-to-One relationship with Person
//    @Column(name = "feedback_client")
//    private Client client;
}
