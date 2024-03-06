package eaismart.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Iekiny Marcel Aug 11, 2020
 */
@Controller
public class HomeController {
    
    @GetMapping("/home")
    public String login(Principal principal, Model model) {
        model.addAttribute("principal", principal);
        return "index";
    }

    @GetMapping("/sample")
    public String sample() {
        return "sample/sample";
    }

}
