package eaismart.webapps.sigpc.controller;

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

import eaismart.webapps.sigpc.dto.DomainDto;
import eaismart.webapps.sigpc.service.DomainService;
import eaismart.webapps.sigpc.util.constants.Status;

import java.security.Principal;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Controller("sigpcDomainController")
public class DomainController { 
	
	@Autowired
	private DomainService domainService;
	
	@GetMapping("/sigpc/domain")
	public String index(Principal principal, Model model) {
		List<DomainDto> domainDtos = domainService.getAll(); 
                model.addAttribute("principal", principal);
		model.addAttribute("domains", domainDtos);
		return "sigpc/domain/index"; 
	}
	
	@GetMapping("/sigpc/domain/create")
	public String formCreate(Model model) {
		DomainDto domainDto = new DomainDto();
		domainDto.setEstado(Status.ATIVO);
		model.addAttribute("domain", domainDto);
		return "sigpc/domain/form"; 
	}
        
	@GetMapping("/sigpc/domain/update/{id}")
	public String formUpdate(@PathVariable("id") Long id, Model model) {
		DomainDto domainDto = domainService.get(id); 
		model.addAttribute("domain", domainDto);
		return "/sigpc/domain/form"; 
	}
	
	@RequestMapping(value = "/sigpc/domain/form", method = RequestMethod.POST)
	public String saveOrUpdate(@Valid @ModelAttribute("domain") DomainDto domain, BindingResult bindingResult,  RedirectAttributes redirectAttrs, Model model) { 
		if(bindingResult.hasErrors()) 
			return "sigpc/domain/form"; 
		try {
			domainService.create(domain); 
			redirectAttrs.addFlashAttribute("messageSuccess", "Operação efectuada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageError", "Ocorreu um erro na execução da operação!");
			return "sigpc/domain/form"; 
		}
		return "redirect:/sigpc/domain"; 
	}
	
	@GetMapping("/sigpc/domain/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		DomainDto domainDto = domainService.get(id); 
		domainDto.setEstado(Status.INATIVO);
		domainService.update(domainDto);
		return "redirect:/sigpc/domain"; 
	}
	
}
