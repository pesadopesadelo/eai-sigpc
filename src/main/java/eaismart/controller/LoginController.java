package eaismart.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Iekiny Marcel
 * Aug 11, 2020
 */

@Controller
public class LoginController { 
	
	@GetMapping("/login")
	public String login(Principal currentUser) {
		if(currentUser != null) {
			return "redirect:/home";
		}
		return "login"; 
	}

}
