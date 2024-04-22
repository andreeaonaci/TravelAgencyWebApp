package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CountryRepository;

//facade pattern
@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public String getCountryNameById(int countryId) {
        return countryRepository.findById((long) countryId)
                .map(c -> c.getCountryName())
                .orElseThrow(() -> new IllegalArgumentException("Country not found with ID: " + countryId));
    }

    public int getCountryIdByName(String countryName) {
        Integer countryId = countryRepository.findByName(countryName);
        if (countryId == null) {
            throw new IllegalArgumentException("Country not found with name: " + countryName);
        }
        return countryId;
    }
}
