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

import eaismart.webapps.sigpc.dto.CompanyDto;
import eaismart.webapps.sigpc.service.CompanyService;
import eaismart.webapps.sigpc.util.constants.Status;

import java.security.Principal;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Controller("sigpcCompanyController")
public class CompanyController { 
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping("/sigpc/company")
	public String index(Principal principal, Model model) {
		List<CompanyDto> companyDtos = companyService.getAll(); 
                model.addAttribute("principal", principal);
		model.addAttribute("companies", companyDtos);
		return "sigpc/company/index"; 
	}
	
	@GetMapping("/sigpc/company/create")
	public String formCreate(Model model) {
		CompanyDto companyDto = new CompanyDto();
		companyDto.setStatus(Status.ATIVO);
		model.addAttribute("company", companyDto);
		return "sigpc/company/form"; 
	}
        
	@GetMapping("/sigpc/company/update/{id}")
	public String formUpdate(@PathVariable("id") Long id, Model model) {
		CompanyDto companyDto = companyService.get(id); 
		model.addAttribute("company", companyDto);
		return "/sigpc/company/form"; 
	}
	
	@RequestMapping(value = "/sigpc/company/form", method = RequestMethod.POST)
	public String saveOrUpdate(@Valid @ModelAttribute("domain") CompanyDto company, BindingResult bindingResult,  RedirectAttributes redirectAttrs, Model model) { 
		if(bindingResult.hasErrors()) 
			return "sigpc/company/form"; 
		try {
			companyService.create(company); 
			redirectAttrs.addFlashAttribute("messageSuccess", "Operação efectuada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageError", "Ocorreu um erro na execução da operação!");
			return "sigpc/company/form"; 
		}
		return "redirect:/sigpc/company"; 
	}
	
	@GetMapping("/sigpc/company/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		CompanyDto companyDto = companyService.get(id); 
		companyDto.setStatus(Status.INATIVO);
		companyService.update(companyDto);
		return "redirect:/sigpc/company"; 
	}
	
}
