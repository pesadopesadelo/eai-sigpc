package eaismart.util.generator;

import java.util.Arrays;

import org.springframework.ui.Model;

import eaismart.util.constants.CRUDArtifactType;
/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class ControllerGenerator extends JavaFileGenerator{
	
	public ControllerGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName);
		type = CRUDArtifactType.CONTROLLER; 
		imports = Arrays.asList(
				"java.util.List",
				"javax.validation.Valid",
				"org.springframework.beans.factory.annotation.Autowired",
				"org.springframework.stereotype.Controller",
				"org.springframework.ui.Model",
				"org.springframework.validation.BindingResult",
				"org.springframework.web.bind.annotation.GetMapping",
				"org.springframework.web.bind.annotation.ModelAttribute",
				"org.springframework.web.bind.annotation.PathVariable",
				"org.springframework.web.bind.annotation.RequestMapping",
				"org.springframework.web.bind.annotation.RequestMethod",
				"org.springframework.web.servlet.mvc.support.RedirectAttributes",
				"eaismart.webapps." + appCode + ".dto." + this.entityName + "Dto",
				"eaismart.webapps." + appCode + ".service." + this.entityName + "Service",
				"java.security.Principal"
				);
	}

	@Override
	protected void addPackageDefinition() {
		this.content.append("package eaismart.webapps." + this.appCode + ".controller;" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
	}

	@Override
	protected void addClassDefinition() {
		this.content.append("@Controller" + System.lineSeparator()); 
		this.content.append("public class " + entityName + "Controller {" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		
		// Fields 
		this.content.append("\t	@Autowired" + System.lineSeparator()); 
		this.content.append("\t	private " + entityName + "Service " + entityName.toLowerCase() + "Service;" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		
		// Methods (index) 
		this.content.append("\t	@GetMapping(\"/" + appCode + "/" + entityName.toLowerCase() + "\")" + System.lineSeparator()); 
		this.content.append("\t	public String index(Principal principal, Model model) {" + System.lineSeparator()); 
		this.content.append("\t\t\tList<" + entityName + "Dto> " + entityName.toLowerCase() + "Dtos = " + entityName.toLowerCase() + "Service.getAll();" + System.lineSeparator()); 
		this.content.append("\t\t\tmodel.addAttribute(\"principal\", principal);" + System.lineSeparator()); 
		this.content.append("\t\t\tmodel.addAttribute(\"" + entityName.toLowerCase() + "s\", " + entityName.toLowerCase() + "Dtos);" + System.lineSeparator()); 
		this.content.append("\t\t	return \"" + appCode + "/" + entityName.toLowerCase() + "/index\";" + System.lineSeparator()); 
		this.content.append("\t	}" + System.lineSeparator());
		this.content.append(System.lineSeparator());
		
		// Methods (formCreate) 
		this.content.append("\t	@GetMapping(\"/" + appCode + "/" + entityName.toLowerCase() + "/create\")" + System.lineSeparator()); 
		this.content.append("\t	public String formCreate(Model model) {" + System.lineSeparator()); 
		this.content.append("\t\t\t" + entityName + "Dto " + entityName.toLowerCase() + "Dto = new " + entityName + "Dto();" + System.lineSeparator());
		this.content.append("\t\t\tmodel.addAttribute(\"" + entityName.toLowerCase() + "\", " + entityName.toLowerCase() + "Dto);" + System.lineSeparator());
		this.content.append("\t\t	return \"" + appCode + "/" + entityName.toLowerCase() + "/form\";" + System.lineSeparator());
		this.content.append("\t	}" + System.lineSeparator());
		this.content.append(System.lineSeparator());
		
		// Methods (formUpdate) 
		this.content.append("\t	@GetMapping(\"/" + appCode + "/" + entityName.toLowerCase() + "/update/{id}\")" + System.lineSeparator());
		this.content.append("\t	public String formUpdate(@PathVariable(\"id\") Long id, Model model) {" + System.lineSeparator());
		this.content.append("\t\t\t" + entityName + "Dto " + entityName.toLowerCase() + "Dto = " + entityName.toLowerCase() + "Service.get(id);" + System.lineSeparator());
		this.content.append("\t\t\tmodel.addAttribute(\"" + entityName.toLowerCase() + "\", " + entityName.toLowerCase() + "Dto);" + System.lineSeparator());
		this.content.append("\t\t	return \"/" + appCode + "/" + entityName.toLowerCase() + "/form\";" + System.lineSeparator());
		this.content.append("\t	}" + System.lineSeparator());
		this.content.append(System.lineSeparator());
		
		// Methods (saveOrUpdate) 
		this.content.append("\t	@RequestMapping(value = \"/" + appCode + "/" + entityName.toLowerCase() + "/form\", method = RequestMethod.POST)" + System.lineSeparator());
		this.content.append("\t	public String saveOrUpdate(@Valid @ModelAttribute(\"" + entityName.toLowerCase() + "\") " + entityName + "Dto " + entityName.toLowerCase() + ", BindingResult bindingResult,  RedirectAttributes redirectAttrs, Model model) {" + System.lineSeparator());
		this.content.append("\t\t\tif(bindingResult.hasErrors())" + System.lineSeparator());
		this.content.append("\t\t\t	return \"" + appCode + "/" + entityName.toLowerCase() + "/form\"; " + System.lineSeparator());
		this.content.append("\t\t\ttry {" + System.lineSeparator()); 
		this.content.append("\t\t\t\t" + entityName.toLowerCase() + "Service.create(" + entityName.toLowerCase() + "); " + System.lineSeparator());
		this.content.append("\t\t\t\tredirectAttrs.addFlashAttribute(\"messageSuccess\", \" Operação efectuada com sucesso!\");" + System.lineSeparator());
		this.content.append("\t\t\t} catch (Exception e) {" + System.lineSeparator()); 
		this.content.append("\t\t\t\te.printStackTrace();" + System.lineSeparator()); 
		this.content.append("\t\t\t\tmodel.addAttribute(\"messageError\", \"Ocorreu um erro na execução da operação!\");" + System.lineSeparator()); 
		this.content.append("\t\t\t\treturn \"" + appCode + "/" + entityName.toLowerCase() + "/form\";" + System.lineSeparator()); 
		this.content.append("\t\t\t}" + System.lineSeparator()); 
		this.content.append("\t\t	return \"redirect:/" + appCode + "/" + entityName.toLowerCase() + "\";" + System.lineSeparator());
		this.content.append("\t	}" + System.lineSeparator());
		this.content.append(System.lineSeparator());
		
		// Methods (delete) 
		this.content.append("\t	@GetMapping(\"/" + appCode + "/" + entityName.toLowerCase() + "/delete/{id}\")" + System.lineSeparator());
		this.content.append("\t	public String delete(@PathVariable(\"id\") Long id, RedirectAttributes redirectAttrs) {" + System.lineSeparator());
		this.content.append("\t\t\t try{" + System.lineSeparator());
		this.content.append("\t\t\t\t" + entityName.toLowerCase() + "Service.delete(id);" + System.lineSeparator());
		this.content.append("\t\t\t\tredirectAttrs.addFlashAttribute(\"messageSuccess\", \" Operação efectuada com sucesso!\");" + System.lineSeparator());
		this.content.append("\t\t\t } catch(Exception e){" + System.lineSeparator());
		this.content.append("\t\t\t\te.printStackTrace();" + System.lineSeparator()); 
		this.content.append("\t\t\t\tredirectAttrs.addFlashAttribute(\"messageError\", \"Ocorreu um erro na execução da operação!\");" + System.lineSeparator()); 
		this.content.append("\t\t\t }" + System.lineSeparator());
		this.content.append("\t\t\treturn \"redirect:/" + appCode + "/" + entityName.toLowerCase() + "\";" + System.lineSeparator());
		this.content.append("\t	}" + System.lineSeparator());
		this.content.append(System.lineSeparator());
		
		this.content.append("}" + System.lineSeparator());
	}
	
}
