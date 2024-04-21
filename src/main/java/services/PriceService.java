package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PriceService {
    @Autowired
    private ProjectService projectService;
    public float getPriceForHotelPerNight(String hotel) throws ClassNotFoundException, SQLException {
        float price = 0;
        if (hotel.equals("Japan"))
            price = 400;
        if (hotel.equals("Romania"))
            price = 200;
        if(hotel.equals("Germany"))
            price = 300;
        if(hotel.equals("China"))
            price = 600;
        if (hotel.equals("France"))
            price = 500;
        return price;
    }
}
