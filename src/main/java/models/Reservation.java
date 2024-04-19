package models;
import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int id;

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

    public Reservation(int id, Project project, Client client) {
        this.id = id;
        this.project = project;
        this.client = client;
    }
}
