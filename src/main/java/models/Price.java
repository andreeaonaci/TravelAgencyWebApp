package models;

import jakarta.persistence.*;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @Column(name = "price_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceId;
    @Column(name = "price_value")
    private float countryName;

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public float getCountryName() {
        return countryName;
    }

    public void setCountryName(float countryName) {
        this.countryName = countryName;
    }
}
