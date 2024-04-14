package models;

import jakarta.persistence.*;

@Entity
@Table(name = "travel.feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int id;

    @Column(name = "feedback_text")
    private String feedbackText;

    @Column(name = "feedback_mail")
    private String feedbackMail;

    // Define a Many-to-One relationship with Person
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Person client;
}
