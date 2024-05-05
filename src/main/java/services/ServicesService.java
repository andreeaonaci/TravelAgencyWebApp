package services;

import models.Reservation;
import models.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ServicesRepository;

// Null Object pattern for handling null cases in services
@Service
public class ServicesService {

    private static final Logger logger = LoggerFactory.getLogger(ServicesService.class);

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ReservationService reservationService;

    /**
     * Adds a new service associated with a given reservation.
     *
     * @param reservationId   The ID of the reservation.
     * @param projectDuration The duration of the project in days.
     * @param hotelName       The name of the hotel.
     * @param transport       The type of transport.
     * @param menu            The menu choice.
     */
    public void addService(Long reservationId, int projectDuration, String hotelName, String transport, String menu) {
        logger.info("Adding service for reservation ID: {}, project duration: {}, hotel: {}, transport: {}, menu: {}", reservationId, projectDuration, hotelName, transport, menu);

        Services service = new Services();
        service.setReservationId(Math.toIntExact(reservationId));
        service.setProjectDuration(projectDuration);
        service.setHotel(hotelName);
        service.setTransport(transport);
        service.setMenu(menu);

        servicesRepository.save(service);
    }

    /**
     * Creates a null reservation object to represent an empty or invalid reservation.
     *
     * @return A reservation with a default negative ID to indicate a null object.
     */
    private Reservation createNullReservation() {
        logger.info("Creating a null reservation object");

        Reservation nullReservation = new Reservation();
        nullReservation.setId(-1L);  // Represents a null reservation

        return nullReservation;
    }
}
