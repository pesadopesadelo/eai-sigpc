package eaismart.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eaismart.dto.GeneratorDto;
import eaismart.service.CRUDGeneratorService;

/**
 * @author Iekiny Marcel
 * Feb 4, 2021
 */
@Controller
public class CRUDGeneratorController { 
	
	@Autowired
	private CRUDGeneratorService crudGeneratorService; 

	@GetMapping("/generator/crud")
    public String index(Principal principal, Model model) {
		GeneratorDto generatorDto = new GeneratorDto(); 
		try {
			 this.crudGeneratorService.loadSchemes(generatorDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        model.addAttribute("principal", principal);
		model.addAttribute("generator", generatorDto);
		return "generator/crud"; 
    }
	
	@RequestMapping(value = "/generator/save", method = RequestMethod.POST, params = "gerar")
	public String generate(@Valid @ModelAttribute("generator") GeneratorDto generatorDto, Model model, 
			BindingResult bindingResult) { 
		
		if(bindingResult.hasErrors()) 
			return "generator/crud"; 
		
		try {
			
			this.crudGeneratorService.loadSchemes(generatorDto);
			this.crudGeneratorService.loadTable(generatorDto);
			
			List<String> fileCodes = new ArrayList<String>(generatorDto.getFileCode()); 
			
			if(generatorDto.getTable() != null && !generatorDto.getTable().isEmpty())
				this.crudGeneratorService.loadArtifacts(generatorDto); 
			
			generatorDto.setFileCode(fileCodes);
			
			this.crudGeneratorService.generate(generatorDto); 
			
			model.addAttribute("messageSuccess", "CRUD gerado com sucesso! ");
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageError", "Ocorreu um erro ao gerar CRUD! ");
		} 
		return "generator/crud"; 
	}
	
	@RequestMapping(value = "/generator/save", method = RequestMethod.POST, params = "search")
	public String search(@Valid @ModelAttribute("generator") GeneratorDto generatorDto,  Model model, BindingResult bindingResult) { 
		
		if(bindingResult.hasErrors()) 
			return "generator/crud"; 
		
		try {
			 this.crudGeneratorService.loadSchemes(generatorDto);
			 this.crudGeneratorService.loadTable(generatorDto);
			 
			 if(generatorDto.getTable() != null && !generatorDto.getTable().isEmpty())
				this.crudGeneratorService.loadArtifacts(generatorDto); 
				
			if(generatorDto.getTableFiles().isEmpty() && generatorDto.getTables().isEmpty()) 
				model.addAttribute("messageInfo", "Nenhum registo foi encontrado!"); 
				
			if(!generatorDto.getTables().isEmpty() && (generatorDto.getTable() == null || generatorDto.getTable().isEmpty()))
				model.addAttribute("messageWarning", "Por favor escolher Tabela !"); 
			 
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageError", "Ocorreu um erro: " + e.getMessage());
		}
		
		return "generator/crud"; 
	}

}
