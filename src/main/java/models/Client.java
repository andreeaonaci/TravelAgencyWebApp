package models;

import jakarta.persistence.*;

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

    public Client() {
    }

    public Client(String clientName, String clientFunction, String clientPassword, String clientMail, String clientPhone) {
        this.clientName = clientName;
        this.clientFunction = clientFunction;
        this.clientPassword = clientPassword;
        this.clientMail = clientMail;
        this.clientPhone = clientPhone;
    }

    // Getters and setters
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientFunction() {
        return clientFunction;
    }

    public void setClientFunction(String clientFunction) {
        this.clientFunction = clientFunction;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public String getClientMail() {
        return clientMail;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
}
