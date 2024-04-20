package models;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

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

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    @Column(name = "project_agent")
    private int agent;

    public String getName() {
        return name;
    }

    public int getProjectId() {
        return id;
    }

    public String getFormattedStartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mma");
        return sdf.format(start);
    }

    public String getFormattedStopDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mma");
        return sdf.format(stop);
    }
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations;
}
