package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id")
    private int agentId;

    @Column(name = "agent_name")
    private String agentName;

    @Column(name = "agent_function")
    private String agentFunction;

    @Column(name = "agent_password")
    private String agentPassword;

    @Column(name = "agent_mail", unique = true)
    private String agentMail;

    @Column(name = "agent_phone")
    private String agentPhone;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Agent() {
    }

    public Agent(String agentName, String agentFunction, String agentPassword, String agentMail, String agentPhone) {
        this.agentName = agentName;
        this.agentFunction = agentFunction;
        this.agentPassword = agentPassword;
        this.agentMail = agentMail;
        this.agentPhone = agentPhone;
    }

    // Getters and setters
    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentFunction() {
        return agentFunction;
    }

    public void setAgentFunction(String agentFunction) {
        this.agentFunction = agentFunction;
    }

    public String getAgentPassword() {
        return agentPassword;
    }

    public void setAgentPassword(String agentPassword) {
        this.agentPassword = agentPassword;
    }

    public String getAgentMail() {
        return agentMail;
    }

    public void setAgentMail(String agentMail) {
        this.agentMail = agentMail;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
