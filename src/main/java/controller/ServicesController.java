package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.PriceService;
import services.ProjectService;
import services.ReservationService;
import services.ServicesService;

import java.sql.SQLException;

@Controller
@RequestMapping("/api/projects")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PriceService priceService;

    @PostMapping("/services")
    public ResponseEntity<?> selectServices(@RequestParam("projectId") Long projectId, @RequestParam("clientMail") String clientMail,  @RequestParam("selectedTransport") String transport,  @RequestParam("selectedMenu") String menu) {
        try {
            System.out.println("transport: " + transport);
            System.out.println("menu: " + menu);
            if (ClientController.getClientSecurity() == null || !ClientController.getClientSecurity().getClientMail().equals(clientMail)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
            }
            Long reservationId = reservationService.getReservationId(projectId, clientMail);
            int projectDuration = projectService.getProjectDuration(projectId);
            String hotelName = projectService.getHotelNameById(Math.toIntExact(projectId));

            if (reservationId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
            }

            servicesService.addService(reservationId, projectDuration, hotelName, transport, menu);
            return ResponseEntity.status(HttpStatus.OK).body("Reservation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to make reservation.");
        }
    }
    @GetMapping("/price")
    public ResponseEntity<Double> calculatePrice(
            @RequestParam("service_id") int serviceId,
            @RequestParam("project_id") Long projectId,
            @RequestParam("menu") String menu,
            @RequestParam("transport") String transport
    ) throws SQLException, ClassNotFoundException {
        if (ClientController.getClientSecurity() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        int duration = projectService.getProjectDuration(projectId);
        int priceHotel = (int) (priceService.getPriceForHotelPerNight(projectService.getHotelNameById(Math.toIntExact(projectId))) * duration);
        double priceMenu = 0, priceTransport = 0;

        switch (menu) {
            case "Ultra All Inclusive":
                priceMenu = 300;
                break;
            case "Only Breakfast":
                priceMenu = 50;
                break;
            case "Breakfast + Dinner":
                priceMenu = 80;
                break;
            case "All Inclusive":
                priceMenu = 200;
                break;
        }

        switch (transport) {
            case "plane":
                priceTransport = (projectService.findById(projectId).getDistance() * 0.5);
                break;
            case "train":
                priceTransport = (projectService.findById(projectId).getDistance() * 0.4);
                break;
            case "bus":
                priceTransport = (projectService.findById(projectId).getDistance() * 0.3);
                break;
        }

        priceMenu *= duration;
        double totalPrice = priceMenu + priceTransport + priceHotel;

        return ResponseEntity.ok(totalPrice);
    }
}
