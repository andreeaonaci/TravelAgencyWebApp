package controller;

import models.Agent;
import models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import services.AgentService;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;

    @PostMapping("/agentLogin")
    public ResponseEntity<?> loginAgent(@RequestBody Agent agent) {
        boolean isValidUser = agentService.validateCredentials(agent.getAgentMail(), agent.getAgentPassword());
        if (isValidUser) {
            String redirectUrl = "/api/agents/agent_dashboard.html?username=" + agent.getAgentMail();
            return ResponseEntity.status(HttpStatus.OK).body(redirectUrl);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/agent_dashboard.html")
    public String agentDashboard(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "agent_dashboard";
    }

    @GetMapping("/updateAgent/{agentName}")
    public ResponseEntity<?> getAgentIdByName(@PathVariable String agentName) {
        Optional<Integer> agentId = agentService.getAgentIdByName(agentName);
        if (agentId.isPresent()) {
            return ResponseEntity.ok().body(Map.of("agentId", agentId.get()));
        } else {
            return ResponseEntity.status(404).body("Agent not found.");
        }
    }
}
