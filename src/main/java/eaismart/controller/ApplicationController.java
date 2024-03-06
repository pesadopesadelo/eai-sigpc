package eaismart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eaismart.dto.ApplicationDto;
import eaismart.service.ApplicationService;
import java.security.Principal;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Controller
public class ApplicationController { 
	
	@Autowired
	private ApplicationService applicationService;
	
	@GetMapping("/application")
	public String index(Principal principal, Model model) {
		List<ApplicationDto> applicationDtos = applicationService.getAll(); 
                model.addAttribute("principal", principal);
		model.addAttribute("applications", applicationDtos);
		return "application/index"; 
	}
	
	@GetMapping("/application/create")
	public String formCreate(Model model) {
		ApplicationDto application = new ApplicationDto();
		model.addAttribute("application_", application);
		return "application/form"; 
	}
        
	@GetMapping("/application/update/{id}")
	public String formUpdate(@PathVariable("id") Long id, Model model) {
		ApplicationDto application = applicationService.get(id); 
		model.addAttribute("application_", application);
		return "application/form"; 
	}
	
	@RequestMapping(value = "/application/form", method = RequestMethod.POST)
	public String saveOrUpdate(@Valid @ModelAttribute("application_") ApplicationDto application, BindingResult bindingResult,  RedirectAttributes redirectAttrs) { 
		if(bindingResult.hasErrors()) 
			return "application/form"; 
		applicationService.create(application); 
		 redirectAttrs.addFlashAttribute("message", "Account created!");
		return "redirect:/application"; 
	}
	
	@GetMapping("/application/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		applicationService.delete(id);
		return "redirect:/application"; 
	}
	
}
