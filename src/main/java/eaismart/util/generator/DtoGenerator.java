package eaismart.util.generator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import eaismart.util.constants.CRUDArtifactType;
import eaismart.util.constants.DataBaseAudit;
import eaismart.util.generator.EntityGenerator.EntityField;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class DtoGenerator extends JavaFileGenerator{
	
	private List<EntityField> fields; 
	
	public DtoGenerator(String fileName, String appCode, String entityName, List<String> imports, List<EntityField> fields) {
		this(fileName, appCode, entityName); 
		this.imports = imports != null ? imports : new ArrayList<String>(); 
		this.imports.add("eaismart.dto.BaseDto;");
		if(this.imports.size() > 1) {
	        this.imports = this.imports.stream().distinct().map(obj->obj.replaceAll(";", "").replace("import", ""))
	        		.filter(
			        		obj->!(obj.contains("javax.persistence") || 
			        		obj.contains("javax.validation") || 
			        		obj.contains("java.io.Serializable") ||
			        		obj.contains("eaismart.entity.BaseEntity")))
	        		.collect(Collectors.toList());
	        this.imports.sort(Comparator.naturalOrder()); 
		}
        this.fields = fields != null ? fields : new ArrayList<EntityField>(); 
	}
	
	public DtoGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName); 
		type = CRUDArtifactType.DTO; 
	}

	@Override
	protected void addPackageDefinition() {
		this.content.append("package eaismart.webapps." + this.appCode + ".dto;" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
	}

	@Override
	protected void addClassDefinition() {
		this.content.append("public class " + entityName + "Dto extends BaseDto {" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		// defining fields ... 
		this.fields.forEach(field->{
			if(field != null && field.getAttribute() != null && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_UPDATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_BY_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_LAST_MODIFIED_BY_COLUMN_NAME)) {
				content.append("\t" + field.getAttribute().substring(field.getAttribute().indexOf("private"))); 
				this.content.append(System.lineSeparator()); 
			}
		});
		// defining setters & getters ... 
		this.content.append(System.lineSeparator()); 
		this.fields.forEach(field->{
			if(field != null && field.getGetterSetter() != null && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_UPDATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_BY_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_LAST_MODIFIED_BY_COLUMN_NAME)) {
				content.append(field.getGetterSetter()); 
				this.content.append(System.lineSeparator());
			}
		});
		this.content.append("}"); 
	}
	
}
