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

import eaismart.webapps.sigpc.dto.ClassifierDto; 
import eaismart.webapps.sigpc.service.ClassifierService; 
import eaismart.webapps.sigpc.util.constants.Status;

import java.security.Principal;

/**
 * @author Iekiny Marcel
 * Dec 2, 2020
 */

@Controller("sigpcClassifierController")
public class ClassifierController { 
	
	@Autowired
	private ClassifierService classifierService;
	
	@GetMapping("/sigpc/classifier")
	public String index(Principal principal, Model model) {
		List<ClassifierDto> classifierDtos = classifierService.getAll(); 
                model.addAttribute("principal", principal);
		model.addAttribute("classifiers", classifierDtos);
		return "sigpc/classifier/index"; 
	}
	
	@GetMapping("/sigpc/classifier/create")
	public String formCreate(Model model) {
		ClassifierDto classifierDto = new ClassifierDto();
		classifierDto.setStatus(Status.ATIVO);
		model.addAttribute("classifier", classifierDto);
		return "sigpc/classifier/form"; 
	}
        
	@GetMapping("/sigpc/classifier/update/{id}")
	public String formUpdate(@PathVariable("id") Long id, Model model) {
		ClassifierDto classifierDto = classifierService.get(id); 
		model.addAttribute("classifier", classifierDto);
		return "/sigpc/classifier/form"; 
	}
	
	@RequestMapping(value = "/sigpc/classifier/form", method = RequestMethod.POST)
	public String saveOrUpdate(@Valid @ModelAttribute("classifier") ClassifierDto classifier, BindingResult bindingResult,  RedirectAttributes redirectAttrs, Model model) { 
		if(bindingResult.hasErrors()) 
			return "sigpc/classifier/form"; 
		try {
			classifierService.create(classifier); 
			redirectAttrs.addFlashAttribute("messageSuccess", "Operação efectuada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageError", "Ocorreu um erro na execução da operação!");
			return "sigpc/classifier/form"; 
		}
		return "redirect:/sigpc/classifier"; 
	}
	
	@GetMapping("/sigpc/classifier/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		ClassifierDto classifierDto = classifierService.get(id); 
		classifierDto.setStatus(Status.INATIVO);
		classifierService.update(classifierDto);
		return "redirect:/sigpc/classifier"; 
	}
	
}
