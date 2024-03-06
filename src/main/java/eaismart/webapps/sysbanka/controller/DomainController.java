package eaismart.webapps.sysbanka.controller;

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

import eaismart.webapps.sysbanka.dto.DomainDto;
import eaismart.webapps.sysbanka.service.DomainService;
import eaismart.webapps.sysbanka.util.constants.Status;

import java.security.Principal;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Controller("sysbankaDomainController")
public class DomainController { 
	
	@Autowired
	private DomainService domainService;
	
	@GetMapping("/sysbanka/domain")
	public String index(Principal principal, Model model) {
		List<DomainDto> domainDtos = domainService.getAll(); 
                model.addAttribute("principal", principal);
		model.addAttribute("domains", domainDtos);
		return "sysbanka/domain/index"; 
	}
	
	@GetMapping("/sysbanka/domain/create")
	public String formCreate(Model model) {
		DomainDto domainDto = new DomainDto();
		domainDto.setEstado(Status.ATIVO);
		model.addAttribute("domain", domainDto);
		return "sysbanka/domain/form"; 
	}
        
	@GetMapping("/sysbanka/domain/update/{id}")
	public String formUpdate(@PathVariable("id") Long id, Model model) {
		DomainDto domainDto = domainService.get(id); 
		model.addAttribute("domain", domainDto);
		return "/sysbanka/domain/form"; 
	}
	
	@RequestMapping(value = "/sysbanka/domain/form", method = RequestMethod.POST)
	public String saveOrUpdate(@Valid @ModelAttribute("domain") DomainDto domain, BindingResult bindingResult,  RedirectAttributes redirectAttrs, Model model) { 
		if(bindingResult.hasErrors()) 
			return "sysbanka/domain/form"; 
		try {
			domainService.create(domain); 
			redirectAttrs.addFlashAttribute("messageSuccess", "Operação efectuada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageError", "Ocorreu um erro na execução da operação!");
			return "sysbanka/domain/form"; 
		}
		return "redirect:/sysbanka/domain"; 
	}
	
	@GetMapping("/sysbanka/domain/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		DomainDto domainDto = domainService.get(id); 
		domainDto.setEstado(Status.INATIVO);
		domainService.update(domainDto);
		return "redirect:/sysbanka/domain"; 
	}
	
}
