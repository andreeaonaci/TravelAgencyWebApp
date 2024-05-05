package controller;

import models.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static Agent agentSecurity;

    @GetMapping("/agentSecurity")
    public static Agent getAgentSecurity() {
        return agentSecurity;
    }

    @PostMapping("/agentLogin")
    public ResponseEntity<?> loginAgent(@RequestBody Agent agent) {
        boolean isValidUser = agentService.validateCredentials(agent.getAgentMail(), agent.getAgentPassword());
        if (isValidUser) {
            agentSecurity = agent;
            String redirectUrl = "/api/agents/agent_dashboard.html?username=" + agent.getAgentMail();
            return ResponseEntity.status(HttpStatus.OK).body(redirectUrl);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping("/agent_dashboard.html")
    public String agentDashboard(@RequestParam("username") String username, Model model) {
        if (agentSecurity == null || !agentSecurity.getAgentMail().equals(username) || !agentService.validateCredentials(agentSecurity.getAgentMail(), agentSecurity.getAgentPassword())) {
            return "redirect:/api/agents/agentLogin";
        }
        model.addAttribute("username", username);
        return "agent_dashboard";
    }

    @GetMapping("/updateAgent/{agentName}")
    public ResponseEntity<?> getAgentIdByName(@PathVariable String agentName) {
        if (agentSecurity == null || !agentService.validateCredentials(agentSecurity.getAgentMail(), agentSecurity.getAgentPassword()) || !agentSecurity.getAgentMail().equals(agentName)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        Optional<Integer> agentId = agentService.getAgentIdByName(agentName);
        if (agentId.isPresent()) {
            return ResponseEntity.ok().body(Map.of("agentId", agentId.get()));
        } else {
            return ResponseEntity.status(404).body("Agent not found.");
        }
    }
}
