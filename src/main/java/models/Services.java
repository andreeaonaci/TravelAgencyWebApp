package models;

import jakarta.persistence.*;

/**
 * Represents a set of services in the travel agency system.
 * This class describes the transportation, accommodation, menu, and other service-related details.
 */
@Entity
@Table(name = "Services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "services_id")
    private int id;

    @Column(name = "services_transport")
    private String transport;

    @Column(name = "services_reservation")
    private int reservationId;

    @Column(name = "services_project_duration")
    private int projectDuration;

    @Column(name = "services_hotel")
    private String hotel;

    @Column(name = "services_menu")
    private String menu;

    /**
     * Gets the unique identifier for this set of services.
     *
     * @return the service ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this set of services.
     *
     * @param id the service ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type of transport service.
     *
     * @return the type of transport
     */
    public String getTransport() {
        return transport;
    }

    /**
     * Sets the type of transport service.
     *
     * @param transport the type of transport
     */
    public void setTransport(String transport) {
        this.transport = transport;
    }

    /**
     * Gets the associated reservation ID.
     *
     * @return the reservation ID
     */
    public int getReservationId() {
        return reservationId;
    }

    /**
     * Sets the associated reservation ID.
     *
     * @param reservationId the reservation ID
     */
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * Gets the project duration associated with these services.
     *
     * @return the project duration in days
     */
    public int getProjectDuration() {
        return projectDuration;
    }

    /**
     * Sets the project duration associated with these services.
     *
     * @param projectDuration the duration in days
     */
    public void setProjectDuration(int projectDuration) {
        this.projectDuration = projectDuration;
    }

    /**
     * Gets the name of the hotel associated with these services.
     *
     * @return the hotel name
     */
    public String getHotel() {
        return hotel;
    }

    /**
     * Sets the name of the hotel associated with these services.
     *
     * @param hotel the hotel name
     */
    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    /**
     * Gets the type of menu service.
     *
     * @return the type of menu
     */
    public String getMenu() {
        return menu;
    }

    /**
     * Sets the type of menu service.
     *
     * @param menu the type of menu
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }
}
