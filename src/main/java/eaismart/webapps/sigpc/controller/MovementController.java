package eaismart.webapps.sigpc.controller;

import eaismart.webapps.sigpc.dto.DomainDto;
import eaismart.webapps.sigpc.dto.MovementDto;
import eaismart.webapps.sigpc.service.MovementService;
import eaismart.webapps.sigpc.util.constants.Currency;
import eaismart.webapps.sigpc.util.constants.TransitionType;

import java.security.Principal;
 
import java.util.Optional;
 

import org.hibernate.exception.ConstraintViolationException;
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

/**
 *
 * @author elton
 */
@Controller
public class MovementController {
    
    @Autowired
    private MovementService movementService;
    
    
    @GetMapping("/sigpc/movement/form")
    public String form(Model model) {
        MovementDto movementDto = new MovementDto();
        movementDto.setCurrency(Currency.CVE);
        movementService.loadCurrenciesFromDomain(movementDto);
        movementService.loadMonthsFromDomain(movementDto);
        movementService.loadCompanies(movementDto);
        model.addAttribute("movement", movementDto);
        return "/sigpc/movement/form";
    }

    @RequestMapping(value = "/sigpc/movement/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("upload_") MultipartFile file, @ModelAttribute("movement") MovementDto movement, BindingResult bindingResult, RedirectAttributes redirectAttrs) {

        if (bindingResult.hasErrors()) 
            return "/sigpc/movement/form";
        
        try {
        	this.movementService.populateMovementsFromExcelToDb(file.getInputStream(), movement);
        	redirectAttrs.addFlashAttribute("messageSuccess", "Diário importado com sucesso !");
        }catch (Exception e) {
			e.printStackTrace();
			if(e.getCause() instanceof ConstraintViolationException) {
				this.movementService.loadMonthsFromDomain(movement); 
				Optional<DomainDto> optional = movement.getMonths().stream().filter(p->p.getCodigo().equals(movement.getMonth())).findFirst(); 
				redirectAttrs.addFlashAttribute("messageWarning", "O diário do mês " + (optional.isPresent() ? optional.get().getSignificado() : movement.getMonth()) + " já se encontra registado.");
			}else 
				redirectAttrs.addFlashAttribute("messageError", "Ocorreu um erro na importação do Diário.");
		}

       

        return "redirect:/sigpc/movement/form";
    }
    
    @GetMapping(path = {"/sigpc/movement", "/sigpc/movement/index"})
	public String index(Principal principal, Model model) {
    	MovementDto movementDto = new MovementDto();
    	movementDto.setType(TransitionType.DEBIT);
    	
        model.addAttribute("principal", principal);
        model.addAttribute("movementDto", movementDto);
       
        model.addAttribute("recebimentos", this.movementService.getAllRecievementMovements());
        model.addAttribute("pagamentos", this.movementService.getAllPaymentMovements());
        model.addAttribute("transferencias", this.movementService.getAllTransferenceMovements());
       
		return "/sigpc/movement/index"; 
	}
    
    @RequestMapping(value = "/sigpc/movement/receivement", method = RequestMethod.POST)
	public String searchReceivement(@ModelAttribute("movementDto") MovementDto movementDto, Model model) { 
		
    	movementDto.setType(TransitionType.DEBIT);
    	
    	model.addAttribute("recebimentos", this.movementService.getAllRecievementMovements(movementDto));
        model.addAttribute("pagamentos", this.movementService.getAllPaymentMovements());
        model.addAttribute("transferencias", this.movementService.getAllTransferenceMovements());
    	
		return "/sigpc/movement/index"; 
	}
    
    @RequestMapping(value = "/sigpc/movement/payment", method = RequestMethod.POST)
   	public String searchPayment(@ModelAttribute("movementDto") MovementDto paymentDto, Model model) { 
   		
    	paymentDto.setType(TransitionType.CREDIT);
    	
    	model.addAttribute("recebimentos", this.movementService.getAllRecievementMovements());
        model.addAttribute("pagamentos", this.movementService.getAllPaymentMovements(paymentDto));
        model.addAttribute("transferencias", this.movementService.getAllTransferenceMovements());
   		
   		return "/sigpc/movement/index"; 
   	}
    
    @RequestMapping(value = "/sigpc/movement/transference", method = RequestMethod.POST)
   	public String searchTransference(@ModelAttribute("movementDto") MovementDto transferenceDto, Model model) { 
   		
    	transferenceDto.setType(TransitionType.TRANSFER);
    	
    	model.addAttribute("recebimentos", this.movementService.getAllRecievementMovements());
        model.addAttribute("pagamentos", this.movementService.getAllPaymentMovements());
        model.addAttribute("transferencias", this.movementService.getAllTransferenceMovements(transferenceDto));
   		
   		return "/sigpc/movement/index"; 
   	}
    
}
