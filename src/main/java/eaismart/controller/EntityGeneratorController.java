/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.controller;

import eaismart.dto.GeneratorDto;
import eaismart.service.EntityGeneratorService;
import java.security.Principal;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author elton
 */
@Controller
public class EntityGeneratorController {

    @Autowired
    private EntityGeneratorService entityGeneratorService;

    @GetMapping("/generator/dao")
    public String index(Principal principal, Model model) {
        GeneratorDto generatorDto = new GeneratorDto();

        try {
            this.entityGeneratorService.loadSchemes(generatorDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("principal", principal);
        model.addAttribute("generator", generatorDto);
        return "generator/dao";
    }

    @RequestMapping(value = "/generator/dao/save", method = RequestMethod.POST, params = "gerar")
    public String generate(@Valid @ModelAttribute("generator") GeneratorDto generatorDto,
            BindingResult bindingResult, RedirectAttributes redirectAttrs) throws Exception {

        if (bindingResult.hasErrors()) {
            return "generator/dao";
        }

        // ... Your bussiness logic here ... 
        System.out.println("Gerar entrado " + generatorDto.getSelectedTables().size());
        System.out.println("Application Code " + generatorDto.getApplicationCode());
        this.entityGeneratorService.entityGenerator(generatorDto);

        redirectAttrs.addFlashAttribute("message", "CRUD gerado com sucesso! ");
        return "redirect:/generator/dao";
    }

    @RequestMapping(value = "/generator/dao/save", method = RequestMethod.POST, params = "search")
    public String search(@Valid @ModelAttribute("generator") GeneratorDto generatorDto, Model model, 
            BindingResult bindingResult, RedirectAttributes redirectAttrs) throws Exception {

        if (bindingResult.hasErrors()) {
            return "generator/dao";
        }

        try {
            
            this.entityGeneratorService.loadSchemes(generatorDto);
            this.entityGeneratorService.loadTable(generatorDto);

            if (generatorDto.getTables().isEmpty()) {
                model.addAttribute("messageInfo", "Nenhum registo foi encontrado!");
            }

            if (!generatorDto.getTables().isEmpty() && (generatorDto.getTable() == null || generatorDto.getTable().isEmpty())) {
                model.addAttribute("messageWarning", "Por favor escolher Tabela !");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messageError", "Ocorreu um erro: " + e.getMessage());
        }
        return "generator/dao";
    }

}
