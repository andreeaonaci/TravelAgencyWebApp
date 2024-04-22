package models;

import jakarta.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Represents a project in the travel agency system.
 * A project might refer to a travel plan, itinerary, or trip organized by the agency.
 */
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;

    @Column(name = "project_name")
    private String name;

    @Column(name = "project_start")
    private Date start;

    @Column(name = "project_stop")
    private Date stop;

    @Column(name = "project_distance")
    private int distance;

    @Column(name = "project_hotel")
    private String hotel;

    @Column(name = "project_country")
    private int country;

    @Column(name = "project_agent")
    private int agent;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations;

    /**
     * Gets the unique identifier for this project.
     *
     * @return the project ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this project.
     *
     * @param id the project ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of this project.
     *
     * @return the project name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this project.
     *
     * @param name the project name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the start date of this project.
     *
     * @return the start date
     */
    public Date getStart() {
        return start;
    }

    /**
     * Sets the start date of this project.
     *
     * @param start the start date
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * Gets the stop date of this project.
     *
     * @return the stop date
     */
    public Date getStop() {
        return stop;
    }

    /**
     * Sets the stop date of this project.
     *
     * @param stop the stop date
     */
    public void setStop(Date stop) {
        this.stop = stop;
    }

    /**
     * Gets the distance related to this project.
     *
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the distance related to this project.
     *
     * @param distance the distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Gets the agent associated with this project.
     *
     * @return the agent ID
     */
    public int getAgent() {
        return agent;
    }

    /**
     * Sets the agent associated with this project.
     *
     * @param agent the agent ID
     */
    public void setAgent(int agent) {
        this.agent = agent;
    }

    /**
     * Gets the name of the hotel associated with this project.
     *
     * @return the hotel name
     */
    public String getHotel() {
        return hotel;
    }

    /**
     * Sets the name of the hotel associated with this project.
     *
     * @param hotel the hotel name
     */
    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    /**
     * Gets the formatted start date as a string.
     *
     * @return the formatted start date
     */
    public String getFormattedStartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mma");
        return sdf.format(start);
    }

    /**
     * Gets the formatted stop date as a string.
     *
     * @return the formatted stop date
     */
    public String getFormattedStopDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mma");
        return sdf.format(stop);
    }

    /**
     * Sets the formatted start date from a string.
     *
     * @param formattedStartDate the formatted start date string
     * @throws ParseException if the date format is invalid
     */
    public void setFormattedStartDate(String formattedStartDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mma");
        this.start = sdf.parse(formattedStartDate);
    }

    /**
     * Sets the formatted stop date from a string.
     *
     * @param formattedStopDate the formatted stop date string
     * @throws ParseException if the date format is invalid
     */
    public void setFormattedStopDate(String formattedStopDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mma");
        this.stop = sdf.parse(formattedStopDate);
    }

    /**
     * Gets the country associated with this project.
     *
     * @return the country ID
     */
    public int getCountry() {
        return country;
    }

    /**
     * Sets the country associated with this project.
     *
     * @param country the country ID
     */
    public void setCountry(int country) {
        this.country = country;
    }
}
