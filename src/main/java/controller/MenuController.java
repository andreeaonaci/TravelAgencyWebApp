package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/api/clients/clientLogin")
    public String clientLogin(Model model) {
        return "clientLogin";
    }

    @GetMapping("/api/agents/agentLogin")
    public String agentLogin(Model model) {
        return "agentLogin";
    }

    @GetMapping("/api/clients/register")
    public String register(Model model) {
        return "register";
    }

}
