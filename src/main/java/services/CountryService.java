package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CountryRepository;

@Service
public class CountryService {
    @Autowired
    public CountryRepository countryRepository;
    public String getCountryNameById(int countryId) {
        return countryRepository.findById((long) countryId)
                .orElse(null)
                .getCountryName();
    }

    public int getCountryIdByName(String countryName) {
        return countryRepository.findByName(countryName);
    }
}
