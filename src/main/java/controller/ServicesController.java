package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.ProjectService;
import services.ReservationService;
import services.ServicesService;

@Controller
@RequestMapping("/api/projects")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ReservationService reservationService; // Assuming you have a ReservationService

    @PostMapping("/services")
    public ResponseEntity<?> selectServices(@RequestParam("projectId") Long projectId, @RequestParam("clientMail") String clientMail,  @RequestParam("selectedTransport") String transport,  @RequestParam("selectedMenu") String menu) {
        try {
            System.out.println("transport: " + transport);
            System.out.println("menu: " + menu);
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
}
