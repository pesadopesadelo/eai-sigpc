package eaismart.util.generator;

import java.util.List;

import eaismart.util.constants.CRUDArtifactType;
import eaismart.util.constants.DataBaseAudit;
import eaismart.util.generator.EntityGenerator.EntityField;
import eaismart.util.generator.entity.EntityGenerator;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class ViewFormGenerator extends ViewGenerator{
	
	public ViewFormGenerator(String fileName, String appCode, String entityName, List<EntityField> entityFields) {
		super(fileName, appCode, entityName, entityFields);
		type = CRUDArtifactType.VIEW_FORM; 
	}
	
	public ViewFormGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName);
		type = CRUDArtifactType.VIEW_FORM; 
	}

	@Override
	protected void buildContent() {
		content.append("<!DOCTYPE html>" + System.lineSeparator()); 
		content.append("<html lang=\"en\">" + System.lineSeparator()); 
		content.append("<head th:replace=\"fragments/head :: head\"> </head>" + System.lineSeparator()); 
		content.append("<body class=\"app header-fixed sidebar-fixed aside-menu-fixed sidebar-lg-show\">" + System.lineSeparator()); 
		content.append("<header class=\"app-header navbar\" th:replace=\"fragments/header :: header\" />" + System.lineSeparator()); 
		content.append("<div class=\"app-body\">" + System.lineSeparator()); 
		content.append("<div th:replace=\"fragments/left-side :: left-side\"/>" + System.lineSeparator()); 
		content.append("<main class=\"main\">" + System.lineSeparator()); 
		
		// Breadcrumbs 
		breadCrumbs(); 
		
		content.append("<div class=\"container-fluid\">" + System.lineSeparator()); 
		content.append("<div class=\"animated fadeIn\">" + System.lineSeparator()); 
		content.append("<!-- /.row-->" + System.lineSeparator()); 
		content.append("<div class=\"row\">" + System.lineSeparator()); 
		content.append("<div class=\"col-lg-12\">" + System.lineSeparator()); 
		content.append("<div class=\"card\">" + System.lineSeparator()); 
		content.append("<div class=\"card-header\"> <i class=\"fa fa-edit\"></i> Novo <strong>" + entityName + "</strong></div>" + System.lineSeparator()); 
		
		// Form 
		content.append("<div class=\"card-body\">" + System.lineSeparator()); 
		content.append("<form action=\"#\" method=\"post\" th:action=\"@{/" + appCode + "/" + entityName.toLowerCase() + "/form}\" th:object=\"${" + entityName.toLowerCase() + "}\">" + System.lineSeparator()); 
		
		fields();
		
		content.append("</div>" + System.lineSeparator());
		
		
		// card footer 
		content.append("<div class=\"card-footer\">" + System.lineSeparator());
		content.append("<button class=\"btn btn-sm btn-primary\" type=\"submit\">" + System.lineSeparator());
		content.append("<i class=\"fa fa-dot-circle-o\"></i> Submit</button>" + System.lineSeparator());
		content.append("<button class=\"btn btn-sm btn-danger\" type=\"reset\">" + System.lineSeparator());
		content.append("<i class=\"fa fa-ban\"></i> Reset</button>" + System.lineSeparator());
		content.append("</div>" + System.lineSeparator());
		
		content.append("</form>" + System.lineSeparator());
		
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</main>" + System.lineSeparator());  
		content.append("</div>" + System.lineSeparator());  
		
		// footer 
		content.append("<footer class=\"app-footer\" th:replace=\"fragments/footer :: footer\"></footer>" + System.lineSeparator()); 
		
		content.append("<div th:replace=\"fragments/script-include :: script-include\"/>" + System.lineSeparator()); 
		
		content.append("</body>" + System.lineSeparator()); 
		content.append("</html>" + System.lineSeparator()); 
	}

	private void breadCrumbs() {
		content.append("<!-- Breadcrumb-->" + System.lineSeparator()); 
		content.append("<ol class=\"breadcrumb\">" + System.lineSeparator()); 
		content.append("<li class=\"breadcrumb-item\">" + System.lineSeparator()); 
		content.append("<a href=\"#\" th:href=\"@{/home}\">Home</a>" + System.lineSeparator()); 
		content.append("</li>" + System.lineSeparator()); 
		content.append("<li class=\"breadcrumb-item\">" + System.lineSeparator()); 
		content.append("<a href=\"#\" th:href=\"@{/" + appCode + "/" + entityName.toLowerCase() + "}\">Lista " + entityName + "s</a>" + System.lineSeparator()); 
		content.append("</li>" + System.lineSeparator()); 
		content.append("<li class=\"breadcrumb-item active\">Novo(a) " + entityName + "</li>" + System.lineSeparator()); 
		content.append("</ol>" + System.lineSeparator()); 
		content.append(System.lineSeparator()); 
	}
	
	private void fields() { 
		for(EntityField entityField : this.entityFields) { 
			if(entityField.isPrimaryKey() && entityField.isAutoIncrement()) {
				//Id
				content.append(" <div class=\"form-group\">" + System.lineSeparator());
				content.append("<input class=\"form-control\" id=\"" + EntityGenerator.resolveName1Dw(entityField.getName()) + "\" type=\"hidden\" th:field=\"*{" + EntityGenerator.resolveName1Dw(entityField.getName()) + "}\" />" + System.lineSeparator());
				content.append("</div>" + System.lineSeparator());
			}else if(!entityField.getName().equals(DataBaseAudit.AUDIT_CREATED_AT_COLUMN_NAME) && !entityField.getName().equals(DataBaseAudit.AUDIT_UPDATED_AT_COLUMN_NAME) && !entityField.getName().equals(DataBaseAudit.AUDIT_CREATED_BY_COLUMN_NAME) && !entityField.getName().equals(DataBaseAudit.AUDIT_LAST_MODIFIED_BY_COLUMN_NAME)){
				// Fields 
				content.append("<div class=\"form-group\">" + System.lineSeparator());
				content.append("<label for=\"" + EntityGenerator.resolveName1Dw(entityField.getName()) + "\">" + EntityGenerator.nameNormalize(entityField.getName()) + "</label>" + System.lineSeparator());
				content.append("<input class=\"form-control\" th:class=\" ${#fields.hasErrors('" + EntityGenerator.resolveName1Dw(entityField.getName()) + "')} ? 'form-control is-invalid' : 'form-control'\" id=\"" + EntityGenerator.resolveName1Dw(entityField.getName()) + "\" type=\"text\" th:field=\"*{" + EntityGenerator.resolveName1Dw(entityField.getName()) + "}\" " + (!entityField.isNullable() ? "required=\"required\"" : "") + "placeholder = \"Introduzir " + EntityGenerator.nameNormalize(entityField.getName()) + " ... \" />" + System.lineSeparator()); 
				content.append("<div th:if=\"${#fields.hasErrors('" + EntityGenerator.resolveName1Dw(entityField.getName()) + "')}\" th:errors=\"*{" + EntityGenerator.resolveName1Dw(entityField.getName()) + "}\" class=\"invalid-feedback\">Please provide a valid informations.</div>" + System.lineSeparator());
				content.append("</div> " + System.lineSeparator());
			}
		}
	}

}
