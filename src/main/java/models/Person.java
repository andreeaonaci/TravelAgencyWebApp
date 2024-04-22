package models;

import jakarta.persistence.*;

/**
 * Represents a person in the travel system.
 * This entity can have various roles within the system, characterized by function, name, and other attributes.
 */
@Entity
@Table(name = "travel.Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_function")
    private String function;

    @Column(name = "person_password")
    private String password;

    @Column(name = "person_mail")
    private String mail;

    @Column(name = "person_phone")
    private String phone;

    /**
     * Gets the unique identifier for this person.
     *
     * @return the person ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this person.
     *
     * @param id the new person ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the person.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the function/role of the person.
     *
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the function/role of the person.
     *
     * @param function the new function
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * Gets the password of the person.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the person.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email of the person.
     *
     * @return the email
     */
    public String getMail() {
        return mail;
    }

    /**
     * Sets the email of the person.
     *
     * @param mail the new email
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Gets the phone number of the person.
     *
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phone the new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
