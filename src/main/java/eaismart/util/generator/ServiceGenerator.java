package eaismart.util.generator;

import java.util.Arrays;
import java.util.Optional;

import eaismart.entity.Application;
import eaismart.util.constants.CRUDArtifactType;
/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class ServiceGenerator extends JavaFileGenerator{ 
	
	public ServiceGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName);
		this.type = CRUDArtifactType.SERVICE;
		imports = Arrays.asList("java.util.ArrayList", "java.util.List", "java.util.Optional",
				"org.modelmapper.ModelMapper", 
				"org.springframework.beans.factory.annotation.Autowired", 
				"org.springframework.stereotype.Service", 
				"eaismart.webapps." + this.appCode + ".dto." + this.entityName + "Dto", 
				"eaismart.webapps." + this.appCode + ".entity." + this.entityName, 
				"eaismart.webapps." + this.appCode + ".repository.I" + this.entityName + "Repository");
	}

	@Override
	protected void addPackageDefinition() {
		this.content.append("package eaismart.webapps." + this.appCode + ".service;" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
	}
	
	@Override
	protected void addClassDefinition() {
		this.content.append("@Service" + System.lineSeparator()); 
		this.content.append("public class " + this.entityName + "Service {" + System.lineSeparator());
		this.content.append(System.lineSeparator()); 
		
		// Fields 
		this.content.append("\t@Autowired" + System.lineSeparator()); 
		this.content.append("\tprivate I" + this.entityName + "Repository " + this.entityName.toLowerCase() + "Repository;" + System.lineSeparator());
		this.content.append(System.lineSeparator()); 
		
		// Method (create) 
		this.content.append("\tpublic " + this.entityName + "Dto create(" + this.entityName + "Dto " + this.entityName.toLowerCase() + "Dto) {" + System.lineSeparator()); 
		this.content.append("\t	" + this.entityName + " " + this.entityName.toLowerCase()  + " = new " + this.entityName + "(); " + System.lineSeparator()); 
		this.content.append("\t	new ModelMapper().map(" + this.entityName.toLowerCase() + "Dto, " + this.entityName.toLowerCase() + ");" + System.lineSeparator()); 
		this.content.append("\t	" + this.entityName.toLowerCase() + " = " + this.entityName.toLowerCase() + "Repository.save(" + this.entityName.toLowerCase() + "); " + System.lineSeparator()); 
		this.content.append("\t	new ModelMapper().map(" + this.entityName.toLowerCase() +", " + this.entityName.toLowerCase() + "Dto);" + System.lineSeparator()); 
		this.content.append("\t	return " + this.entityName.toLowerCase() + "Dto; " + System.lineSeparator()); 
		this.content.append("\t}" + System.lineSeparator()); 
		this.content.append(System.lineSeparator());
		
		// Method (update) 
		this.content.append("\tpublic " + this.entityName + "Dto update(" + this.entityName + "Dto " + this.entityName.toLowerCase() + "Dto) {" + System.lineSeparator()); 
		this.content.append("\t	return create(" + this.entityName.toLowerCase() + "Dto);" + System.lineSeparator()); 
		this.content.append("\t}" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		
		// Method (get) 
		this.content.append("\tpublic " + this.entityName + "Dto get(Long id) {" + System.lineSeparator()); 
		this.content.append("\t	Optional<" + this.entityName + "> opt = " + this.entityName.toLowerCase() + "Repository.findById(id); "); 
		this.content.append("\t	" + this.entityName + " " + this.entityName.toLowerCase() + " = opt.isPresent() ? opt.get() : null; " + System.lineSeparator());
		this.content.append("\t	" + this.entityName + "Dto " + this.entityName.toLowerCase() + "Dto = new " + this.entityName + "Dto(); " + System.lineSeparator());
		this.content.append("\t	new ModelMapper().map(" + this.entityName.toLowerCase() + ", " + this.entityName.toLowerCase() + "Dto);" + System.lineSeparator());
		this.content.append("\t	return " + this.entityName.toLowerCase() + "Dto;" + System.lineSeparator());
		this.content.append("\t}" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		
		// Method (getAll) 
		this.content.append("\tpublic List<" + this.entityName + "Dto> getAll() {" + System.lineSeparator()); 
		this.content.append("\t	List<" + this.entityName + "> " + this.entityName.toLowerCase() + "s = " + this.entityName.toLowerCase() + "Repository.findAll(); " + System.lineSeparator()); 
		this.content.append("\t	List<" + this.entityName + "Dto> " + this.entityName.toLowerCase() + "Dtos = new ArrayList<" + this.entityName + "Dto>();" + System.lineSeparator()); 
		this.content.append("\t	" + this.entityName.toLowerCase() + "s.forEach(obj->{" + System.lineSeparator()); 
		this.content.append("\t\t	" + this.entityName + "Dto " + this.entityName.toLowerCase() + "Dto = new " + this.entityName + "Dto(); " + System.lineSeparator()); 
		this.content.append("\t\t	new ModelMapper().map(obj, " + this.entityName.toLowerCase() + "Dto); " + System.lineSeparator()); 
		this.content.append("\t\t	" + this.entityName.toLowerCase() + "Dtos.add(" + this.entityName.toLowerCase() + "Dto);" + System.lineSeparator()); 
		this.content.append("\t	});" + System.lineSeparator()); 
		this.content.append("\t	return " + this.entityName.toLowerCase() + "Dtos;" + System.lineSeparator()); 
		this.content.append("\t}" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		
		// Method (delete) 
		this.content.append("\tpublic void delete(Long id) {" + System.lineSeparator()); 
		this.content.append("\t	" + this.entityName.toLowerCase() + "Repository.deleteById(id);" + System.lineSeparator()); 
		this.content.append("\t}" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		
		this.content.append("}" + System.lineSeparator()); 
	}
	
}
