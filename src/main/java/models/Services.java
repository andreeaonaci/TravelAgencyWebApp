package models;
import jakarta.persistence.*;

@Entity
@Table(name = "Services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "services_id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @Column(name = "services_transport")
    private String transport;

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(int projectDuration) {
        this.projectDuration = projectDuration;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    @Column(name = "services_reservation")
    private int reservationId;

    @Column(name = "services_project_duration")
    private int projectDuration;

    @Column(name = "services_hotel")
    private String hotel;

    @Column(name = "services_menu")
    private String menu;

    public String getTransport() {
        return transport;
    }

    public String getMenu() {
        return menu;
    }
}
