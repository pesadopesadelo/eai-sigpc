package eaismart.webapps.sysbanka.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eaismart.webapps.sysbanka.dto.AccountDto;
import eaismart.webapps.sysbanka.service.AccountService;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */

@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/sysbanka/account/form")
	public String form(Model model) {
		AccountDto accountDto = new AccountDto(); 
		model.addAttribute("account", accountDto);
		return "/sysbanka/account/form"; 
	}
	
	@RequestMapping(value = "/sysbanka/account/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("upload_") MultipartFile file, @Valid @ModelAttribute("account") AccountDto accountDto, BindingResult bindingResult,  RedirectAttributes redirectAttrs) { 
		
		if(bindingResult.hasErrors()) 
			return "/sysbanka/account/form"; 
		
		System.out.println("file: " + file.getContentType() + " " + file.getName() + " " + file.getOriginalFilename());
		 redirectAttrs.addFlashAttribute("message", "Conta uploaded com sucesso !");
		 
		return "redirect:/sysbanka/account/form"; 
	}

}
