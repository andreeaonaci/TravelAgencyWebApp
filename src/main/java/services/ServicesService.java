package services;

import models.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ServicesRepository;

@Service
public class ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ReservationService reservationService;

    public void addService(Long reservationId, int projectDuration, String hotelName, String transport, String menu) {
        Services service = new Services();
        service.setReservationId(Math.toIntExact(reservationId));
        service.setProjectDuration(projectDuration);
        service.setHotel(hotelName);
        service.setTransport(transport);
        service.setMenu(menu);
        servicesRepository.save(service);
    }
}
