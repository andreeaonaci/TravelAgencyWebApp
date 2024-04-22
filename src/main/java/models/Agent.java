package models;

import jakarta.persistence.*;

/**
 * Represents an Agent entity in the system.
 * An agent has unique credentials and can be associated with a Person.
 */
@Entity
@Table(name = "Agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_id")
    private int id;

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

    /**
     * Default constructor for Agent.
     */
    public Agent() {
    }

    /**
     * Constructs an Agent with specified details.
     *
     * @param agentName      the name of the agent
     * @param agentFunction  the function/role of the agent
     * @param agentPassword  the password of the agent
     * @param agentMail      the email of the agent (must be unique)
     * @param agentPhone     the phone number of the agent
     */
    public Agent(String agentName, String agentFunction, String agentPassword, String agentMail, String agentPhone) {
        this.agentName = agentName;
        this.agentFunction = agentFunction;
        this.agentPassword = agentPassword;
        this.agentMail = agentMail;
        this.agentPhone = agentPhone;
    }

    /**
     * Gets the ID of the agent.
     *
     * @return the agent's ID
     */
    public int getAgentId() {
        return id;
    }

    /**
     * Sets the ID for the agent.
     *
     * @param agentId  the new ID for the agent
     */
    public void setAgentId(int agentId) {
        this.id = agentId;
    }

    /**
     * Gets the name of the agent.
     *
     * @return the agent's name
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the name for the agent.
     *
     * @param agentName the new name for the agent
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * Gets the function or role of the agent.
     *
     * @return the agent's function
     */
    public String getAgentFunction() {
        return agentFunction;
    }

    /**
     * Sets the function or role for the agent.
     *
     * @param agentFunction the new function for the agent
     */
    public void setAgentFunction(String agentFunction) {
        this.agentFunction = agentFunction;
    }

    /**
     * Gets the password of the agent.
     *
     * @return the agent's password
     */
    public String getAgentPassword() {
        return agentPassword;
    }

    /**
     * Sets the password for the agent.
     *
     * @param agentPassword the new password for the agent
     */
    public void setAgentPassword(String agentPassword) {
        this.agentPassword = agentPassword;
    }

    /**
     * Gets the email of the agent.
     *
     * @return the agent's email
     */
    public String getAgentMail() {
        return agentMail;
    }

    /**
     * Sets the email for the agent.
     *
     * @param agentMail the new email for the agent
     */
    public void setAgentMail(String agentMail) {
        this.agentMail = agentMail;
    }

    /**
     * Gets the phone number of the agent.
     *
     * @return the agent's phone number
     */
    public String getAgentPhone() {
        return agentPhone;
    }

    /**
     * Sets the phone number for the agent.
     *
     * @param agentPhone the new phone number for the agent
     */
    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    /**
     * Gets the associated person for the agent.
     *
     * @return the associated person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the associated person for the agent.
     *
     * @param person the new person associated with the agent
     */
    public void setPerson(Person person) {
        this.person = person;
    }
}
