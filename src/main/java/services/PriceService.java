package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//template pattern

/**
 * Service for managing hotel prices.
 * Utilizes the template pattern to determine hotel prices by country.
 */
@Service
public class PriceService {
    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CountryService countryService;

    // Define a map to store hotel prices by country
    private static final Map<String, Float> hotelPrices = new HashMap<>();

    static {
        // Populate the map with hotel prices
        hotelPrices.put("Japan", 400f);
        hotelPrices.put("Romania", 200f);
        hotelPrices.put("Germany", 300f);
        hotelPrices.put("China", 600f);
        hotelPrices.put("France", 500f);
    }

    /**
     * Gets the price for a hotel per night by resolving the country of the hotel.
     *
     * @param hotel The name of the hotel.
     * @return The price for one night.
     * @throws IllegalArgumentException if the hotel or country is unknown.
     */
    public float getPriceForHotelPerNight(String hotel) {
        logger.info("Getting price for hotel: {}", hotel);
        String countryName = countryService.getCountryNameById(projectService.getCountryIdByHotel(hotel));
        return getHotelPrice(countryName);
    }

    /**
     * Retrieves the hotel price based on the country name.
     *
     * @param countryName The name of the country.
     * @return The price for hotels in that country.
     * @throws IllegalArgumentException if no price is found for the given country.
     */
    private float getHotelPrice(String countryName) {
        logger.info("Fetching hotel price for country: {}", countryName);
        Float price = hotelPrices.get(countryName);

        if (price == null) {
            logger.warn("No hotel price found for country: {}", countryName);
            throw new IllegalArgumentException("Unknown country: " + countryName);
        }

        return price;
    }
}
