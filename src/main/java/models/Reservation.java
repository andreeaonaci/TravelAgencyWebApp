package models;
import jakarta.persistence.*;

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
    @JoinColumn(name = "reservation_project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "reservation_customer")
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
