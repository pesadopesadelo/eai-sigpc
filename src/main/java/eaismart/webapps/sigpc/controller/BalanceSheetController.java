/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.webapps.sigpc.controller;

import eaismart.webapps.sigpc.dto.BalanceSheetDto;
import eaismart.webapps.sigpc.service.BalanceSheetService;
import eaismart.webapps.sigpc.util.constants.BalanceSheetType;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author elton
 */
@Controller
public class BalanceSheetController {

    @Autowired
    private BalanceSheetService balanceSheetService;

    @GetMapping(path = {"/sigpc/balancesheet", "/sigpc/balancesheet/index"})
    public String index(Principal principal, Model model) {
        BalanceSheetDto balanceSheetDto = new BalanceSheetDto();
        balanceSheetDto.setType(BalanceSheetType.Monthly);

        try {
            this.balanceSheetService.loadComanyNames(balanceSheetDto);
            this.balanceSheetService.loadYearsFromDomain(balanceSheetDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("principal", principal);
        model.addAttribute("balanceSheetDto", balanceSheetDto);

        return "/sigpc/balancesheet/index";
    }

    @RequestMapping(value = "/sigpc/balancesheet/monthly", method = RequestMethod.GET)
    public String searchBalanceSheetByMonthly(@Valid @ModelAttribute("balanceSheetDto") BalanceSheetDto balanceSheetDto, Model model,
            BindingResult bindingResult, Principal principal) throws Exception {
//        balanceSheetDto.setType(BalanceSheetType.Monthly);

        if (bindingResult.hasErrors()) {
            return "/sigpc/balancesheet/index";
        }

        try {

            model.addAttribute("monthly", this.balanceSheetService.getAllBalanceSheetByMonthly(balanceSheetDto));
            model.addAttribute("month", this.balanceSheetService.getAllTotalBalanceSheetValueByMonthy(balanceSheetDto));
            System.out.println("Month: " + balanceSheetDto.getMonth());
            System.out.println("Year: " + balanceSheetDto.getYear());
            System.out.println("Company: " + balanceSheetDto.getCompany());
            
            model.addAttribute("messageSuccess", "Relatorio gerado com sucesso! ");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("messageError", "Ocorreu um erro ao gerar Relatorio! ");
        }

        return "/sigpc/balancesheet/monthly";
    }

    @GetMapping(path = {"/sigpc/balancesheet/quarter"})
    public String searchBalanceSheetByQuarter(Principal principal, Model model) {
        BalanceSheetDto balanceSheetDto = new BalanceSheetDto();
        balanceSheetDto.setType(BalanceSheetType.Quarter);

        model.addAttribute("principal", principal);
        model.addAttribute("balanceSheetDto", balanceSheetDto); 

        System.out.println("Company: " + balanceSheetDto.getCompany());

        return "/sigpc/balancesheet/quarter";
    }

}
