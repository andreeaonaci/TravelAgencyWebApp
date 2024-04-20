package models;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "reservation_project", referencedColumnName = "project_id")
    private Project project;

    public Project getProject() {
        return project;
    }

    public Client getClient() {
        return client;
    }

    @ManyToOne
    @JoinColumn(name = "reservation_customer", referencedColumnName = "client_id")
    private Client client;

    public void setProject(Project project) {
        this.project = project;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Reservation() {
    }

    public Reservation(Project project, Client client) {
        this.project = project;
        this.client = client;
    }

}
