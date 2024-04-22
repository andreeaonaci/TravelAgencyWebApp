package models;

import jakarta.persistence.*;

/**
 * Represents a Country entity in the system.
 * A country has an ID and a name.
 */
@Entity
@Table(name = "country")
public class Country {
    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;

    @Column(name = "country_name")
    private String countryName;

    /**
     * Gets the unique identifier for the country.
     *
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the unique identifier for the country.
     *
     * @param countryId the new country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the name of the country.
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the name for the country.
     *
     * @param countryName the new country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
