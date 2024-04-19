package models;
import jakarta.persistence.*;
import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "project_agent")
    private Agent agent;

    public String getName() {
        return name;
    }

    public int getProjectId() {
        return id;
    }
}
