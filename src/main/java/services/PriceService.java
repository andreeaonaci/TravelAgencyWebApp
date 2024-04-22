package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//template pattern
@Service
public class PriceService {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CountryService countryService;

    // Define a map to store hotel prices
    private static final Map<String, Float> hotelPrices = new HashMap<>();

    static {
        // Populate the map with hotel prices
        hotelPrices.put("Japan", 400f);
        hotelPrices.put("Romania", 200f);
        hotelPrices.put("Germany", 300f);
        hotelPrices.put("China", 600f);
        hotelPrices.put("France", 500f);
    }

    // Template method for getting the price for a given hotel
    public float getPriceForHotelPerNight(String hotel) {
        return getHotelPrice(countryService.getCountryNameById(projectService.getCountryIdByHotel(hotel)));
    }

    // Step in the template method
    private float getHotelPrice(String hotel) {
        Float price = hotelPrices.get(hotel);

        if (price == null) {
            throw new IllegalArgumentException("Unknown hotel: " + hotel);
        }

        return price;
    }
}
