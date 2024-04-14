package models;
import jakarta.persistence.*;

@Entity
@Table(name = "travel.Services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "services_id")
    private int id;

    @Column(name = "services_transport")
    private String transport;

    @Column(name = "services_menu")
    private String menu;

    public String getTransport() {
        return transport;
    }

    public String getMenu() {
        return menu;
    }
}
