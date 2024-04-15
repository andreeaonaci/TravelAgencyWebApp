package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ReservationService;

@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @PostMapping("/makeReservation")
    @ResponseBody
    public String makeReservation(@RequestParam("projectId") Long projectId,
                                  @RequestParam("customerId") Long customerId) {
        try {
            reservationService.makeReservation(projectId, customerId);
            return "Reservation successful!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to make reservation.";
        }
    }
}
