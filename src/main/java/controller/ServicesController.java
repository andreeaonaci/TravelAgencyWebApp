package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/projects")
public class ServicesController {
    @PostMapping("/services")
    public String services() {
        return "services";
    }
}
