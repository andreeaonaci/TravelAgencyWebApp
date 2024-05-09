package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.ReservationService;

import java.util.List;

@Controller
@RequestMapping("/api/projects")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @PostMapping("/make_reservation")
    @ResponseBody
    public ResponseEntity<?> makeReservationDashboard(@RequestParam("projectId") Long projectId,
                                                      @RequestParam("clientMail") String clientMail) {
        try {
            if (ClientController.getClientSecurity() == null || !ClientController.getClientSecurity().getClientMail().equals(clientMail)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
            }
            if (projectId == null || projectId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project ID is required");
            }
            reservationService.makeReservation(projectId, clientMail);
            return ResponseEntity.status(HttpStatus.OK).body("/api/projects/services.html?projectId=" + projectId + "&clientMail=" + clientMail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to make reservation.");
        }
    }
    @GetMapping("/services.html")
    public String services(@RequestParam("projectId") Long projectId,
                           @RequestParam("clientMail") String clientMail) {
        if (ClientController.getClientSecurity() == null || !ClientController.getClientSecurity().getClientMail().equals(clientMail)) {
            return "redirect:/api/clients/clientLogin";
        }
        return "services";
    }

    @GetMapping("/all_projects_by_client/{clientMail}")
    @ResponseBody
    public List<Long> getAllProjectsByClient(@PathVariable("clientMail") String clientMail) {
        if (ClientController.getClientSecurity() == null || !ClientController.getClientSecurity().getClientMail().equals(clientMail)) {
            return null;
        }
        return reservationService.getAllProjectsByClient(clientMail);
    }

}