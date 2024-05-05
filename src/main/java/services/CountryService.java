package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CountryRepository;

/**
 * Service class for operations related to Country entities.
 * Implements the facade pattern for managing countries.
 */
@Service
public class CountryService {
    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Retrieves the country name based on the country ID.
     *
     * @param countryId The ID of the country.
     * @return The name of the country.
     * @throws IllegalArgumentException if no country is found with the given ID.
     */
    public String getCountryNameById(int countryId) {
        logger.info("Fetching country name by ID: {}", countryId);
        return countryRepository.findById((long) countryId)
                .map(c -> c.getCountryName())
                .orElseThrow(() -> {
                    logger.warn("Country not found with ID: {}", countryId);
                    return new IllegalArgumentException("Country not found with ID: " + countryId);
                });
    }

    /**
     * Retrieves the country ID based on the country name.
     *
     * @param countryName The name of the country.
     * @return The ID of the country.
     * @throws IllegalArgumentException if no country is found with the given name.
     */
    public int getCountryIdByName(String countryName) {
        logger.info("Fetching country ID by name: {}", countryName);
        Integer countryId = countryRepository.findByName(countryName);

        if (countryId == null) {
            logger.warn("Country not found with name: {}", countryName);
            throw new IllegalArgumentException("Country not found with name: " + countryName);
        }

        return countryId;
    }
}
