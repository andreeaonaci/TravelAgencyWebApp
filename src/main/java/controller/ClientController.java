package controller;

import models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.ClientService;
import services.ProjectService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/clientLogin")
    public ResponseEntity<?> loginClient(@RequestBody Client client) {
        boolean isValidUser = clientService.validateCredentials(client.getClientMail(), client.getClientPassword());
        if (isValidUser) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(client.getClientMail(), null);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticationToken);
            return ResponseEntity.status(HttpStatus.OK).body("/api/clients/client_dashboard.html?username=" + client.getClientMail());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> clientData) {
        try {
            String name = clientData.get("clientName");
            String func = clientData.get("clientFunction");
            String email = clientData.get("clientMail");
            String phone = clientData.get("clientPhone");
            String password = clientData.get("clientPassword");
            System.out.println("Registering user: " + name + " " + func + " " + email + " " + phone + " " + password);

            Client client = new Client(name, func, password, email, phone);
            clientService.registerClient(client);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }
    @GetMapping("/client_dashboard.html")
    public String clientDashboard(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "client_dashboard";
    }

    @GetMapping("/api/feedback/give_feedback.html")
    public String giveFeedback(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "give_feedback";
    }
}
