package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import services.AgentService;

import java.util.Map;

@Controller
public class AgentController {
    @Autowired
    private AgentService agentService;

    @PostMapping("/agentLogin")
    public ResponseEntity<?> loginAgent(@RequestBody Map<String, String> agentCredentials) {
        String mail = agentCredentials.get("mail");
        String password = agentCredentials.get("password");
        boolean isValidUser = agentService.validateCredentials(mail, password);
        if (isValidUser) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(mail, null);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticationToken);
            if (securityContext.getAuthentication().isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.OK).body("/agent_dashboard" + mail);
            }
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        return null;
    }

}
