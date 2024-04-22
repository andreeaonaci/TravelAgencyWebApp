package models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Represents a reservation in the travel agency system.
 * A reservation connects a client with a project, indicating that the client has reserved a spot or service.
 */
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_project", referencedColumnName = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "reservation_customer", referencedColumnName = "client_id")
    private Client client;

    /**
     * Default constructor.
     */
    public Reservation() {
    }

    /**
     * Constructs a reservation with a specified project and client.
     *
     * @param project the project associated with this reservation
     * @param client the client associated with this reservation
     */
    public Reservation(Project project, Client client) {
        this.project = project;
        this.client = client;
    }

    /**
     * Gets the unique identifier for this reservation.
     *
     * @return the reservation ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this reservation.
     *
     * @param id the reservation ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the project associated with this reservation.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project associated with this reservation.
     *
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets the client associated with this reservation.
     *
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client associated with this reservation.
     *
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
