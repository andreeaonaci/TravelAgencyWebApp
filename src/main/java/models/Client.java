package models;

import jakarta.persistence.*;

/**
 * Represents a Client entity in the system.
 * A client has unique credentials and personal information.
 */
@Entity
@Table(name = "Client")
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_function")
    private String clientFunction;

    @Column(name = "client_password")
    private String clientPassword;

    @Column(name = "client_mail", unique = true)
    private String clientMail;

    @Column(name = "client_phone")
    private String clientPhone;

    /**
     * Default constructor for Client.
     */
    public Client() {
    }

    /**
     * Constructs a Client with the specified details.
     *
     * @param clientName     the name of the client
     * @param clientFunction the function/role of the client
     * @param clientPassword the password for the client
     * @param clientMail     the email address of the client (must be unique)
     * @param clientPhone    the phone number of the client
     */
    public Client(String clientName, String clientFunction, String clientPassword, String clientMail, String clientPhone) {
        this.clientName = clientName;
        this.clientFunction = clientFunction;
        this.clientPassword = clientPassword;
        this.clientMail = clientMail;
        this.clientPhone = clientPhone;
    }

    /**
     * Gets the ID of the client.
     *
     * @return the client's ID
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Sets the ID for the client.
     *
     * @param clientId the new ID for the client
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the name of the client.
     *
     * @return the client's name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the name for the client.
     *
     * @param clientName the new name for the client
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets the function or role of the client.
     *
     * @return the client's function
     */
    public String getClientFunction() {
        return clientFunction;
    }

    /**
     * Sets the function or role for the client.
     *
     * @param clientFunction the new function for the client
     */
    public void setClientFunction(String clientFunction) {
        this.clientFunction = clientFunction;
    }

    /**
     * Gets the password of the client.
     *
     * @return the client's password
     */
    public String getClientPassword() {
        return clientPassword;
    }

    /**
     * Sets the password for the client.
     *
     * @param clientPassword the new password for the client
     */
    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    /**
     * Gets the email address of the client.
     *
     * @return the client's email address
     */
    public String getClientMail() {
        return clientMail;
    }

    /**
     * Sets the email address for the client.
     *
     * @param clientMail the new email address for the client
     */
    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    /**
     * Gets the phone number of the client.
     *
     * @return the client's phone number
     */
    public String getClientPhone() {
        return clientPhone;
    }

    /**
     * Sets the phone number for the client.
     *
     * @param clientPhone the new phone number for the client
     */
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
}
