package eaismart.util.generator;

import java.util.ArrayList;
import java.util.List;

import eaismart.util.constants.CRUDArtifactType;
import eaismart.util.constants.DataBaseAudit;
import eaismart.util.generator.EntityGenerator.EntityField;
import eaismart.util.generator.entity.EntityGenerator;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class ViewIndexGenerator extends ViewGenerator{

	public ViewIndexGenerator(String fileName, String appCode, String entityName, List<EntityField> entityFields) {
		super(fileName, appCode, entityName, entityFields);
		type = CRUDArtifactType. VIEW_INDEX; 
	}
	
	public ViewIndexGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName);
		type = CRUDArtifactType. VIEW_INDEX; 
	}

	@Override
	protected void buildContent() {
		content.append("<!DOCTYPE html>" + System.lineSeparator()); 
		content.append("<html lang=\"en\">" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t<head th:replace=\"fragments/head :: head\"> </head>" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t<body class=\"app header-fixed sidebar-fixed aside-menu-fixed sidebar-lg-show\">" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t<header class=\"app-header navbar\" th:replace=\"fragments/header :: header\" />" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t<div class=\"app-body\">" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t<div th:replace=\"fragments/left-side :: left-side\"/>" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t<main class=\"main\">" + System.lineSeparator() + System.lineSeparator()); 
		
		// Breadcrumbs 
		breadCrumbs();
		
		// Panel + table (Begin)
		content.append("\t\t\t\t<div class=\"container-fluid\">" + System.lineSeparator()); 
		content.append("\t\t\t\t\t<div class=\"animated fadeIn\">" + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t<!-- /.row-->" + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t<div class=\"row\">" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t<div class=\"col-lg-12\">" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t\t<div class=\"card\">" + System.lineSeparator() + System.lineSeparator()); 
		
		header();
		
		// Panel Body (Begin)
		content.append("<div class=\"card-body\">" + System.lineSeparator() + System.lineSeparator()); 
		
		// Alert message
		msgAlert();
		
		content.append("<div class=\"card\">" + System.lineSeparator()); 
		content.append("<div class=\"card-header\">" + System.lineSeparator()); 
		content.append("<i class=\"fa fa-align-justify\"></i>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("<div class=\"card-body\">" + System.lineSeparator() + System.lineSeparator()); 
		
		// Table  
		table();
		
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append("</main>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		
		// Footer 
		footer();
		
		// Datatable script 
		dataTableScript();
		
		content.append("</body>" + System.lineSeparator()); 
		content.append("</html>" + System.lineSeparator()); 
		
	}
	
	private void breadCrumbs() {
		content.append("\t\t\t\t<!-- Breadcrumb-->" + System.lineSeparator()); 
		content.append("\t\t\t\t<ol class=\"breadcrumb\">" + System.lineSeparator()); 
		content.append("\t\t\t\t\t<li class=\"breadcrumb-item\">" + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t<a href=\"#\" th:href=\"@{/home}\">Home</a>" + System.lineSeparator()); 
		content.append("\t\t\t\t\t</li>" + System.lineSeparator()); 
		content.append("\t\t\t\t\t<li class=\"breadcrumb-item active\">Lista de " + entityName + "</li>" + System.lineSeparator()); 
		content.append("\t\t\t\t</ol>" + System.lineSeparator()); 
		content.append(System.lineSeparator()); 
	}
	
	private void dataTableScript() { 
		content.append(" <script type=\"text/javascript\">" + System.lineSeparator()); 
		content.append("$(document).ready( function () {" + System.lineSeparator()); 
		content.append("$('#" + entityName.toLowerCase() + "Table').DataTable();" + System.lineSeparator()); 
		content.append(" } );" + System.lineSeparator()); 
		content.append(" </script>" + System.lineSeparator()); 
	}
	
	private void header() {
		// Panel Header 
		content.append("\t\t\t\t\t\t\t\t\t<div class=\"card-header\">Lista <strong>" + entityName + "s</strong>" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t\t\t <div class=\"card-header-actions\">" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t\t\t	<a class=\"card-header-action btn-setting\" href=\"#\" th:href=\"@{/" + appCode + "/" + entityName.toLowerCase() + "/create}\">" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t\t\t\t	<i class=\"fa fa-plus-square\"></i> Novo" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t\t\t\t	</a>" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t\t\t\t</div>" + System.lineSeparator() + System.lineSeparator()); 
		content.append("\t\t\t\t\t\t\t\t\t</div>" + System.lineSeparator() + System.lineSeparator()); 
	}
	
	private void footer() {
		content.append(System.lineSeparator()); 
		content.append("<footer class=\"app-footer\" th:replace=\"fragments/footer :: footer\"></footer>" + System.lineSeparator()); 
		content.append("<div th:replace=\"fragments/script-include :: script-include\"/>" + System.lineSeparator()); 
	}
	
	private void table() {
		
		defineColumnPosition();
		
		content.append("<table class=\"table table-responsive-sm table-bordered table-striped table-sm\" id=\"" + entityName.toLowerCase() + "Table\" name=\"" + entityName.toLowerCase() + "Table\">" + System.lineSeparator()); 
		content.append("<thead>" + System.lineSeparator()); 
		content.append("<tr>" + System.lineSeparator()); 
		
		// Fields 
		for(EntityField entityField : this.entityFields) 
			content.append("<th>" + EntityGenerator.nameNormalize(entityField.getName()) + "</th>" + System.lineSeparator()); 
		content.append("<th></th>" + System.lineSeparator());  
		
		content.append("</tr>" + System.lineSeparator());  
		content.append("</thead>" + System.lineSeparator());  
		content.append("<tbody>" + System.lineSeparator());  
		
		content.append("<tr th:each=\"" + entityName.toLowerCase() + " : ${" + entityName.toLowerCase() + "s}\">" + System.lineSeparator());  
		// Fields
		for(EntityField entityField : this.entityFields) {
			content.append("<td th:text=\"${" + entityName.toLowerCase() + "." + EntityGenerator.resolveName1Dw(entityField.getName()) + "}\">Vishnu Serghei</td>" + System.lineSeparator());  
		}
		for(EntityField entityField : this.entityFields) { 
			if(entityField.isPrimaryKey()) {
				content.append("<td>" + System.lineSeparator());  
				content.append("<a href=\"#\" th:href=\"@{'/" + appCode + "/" + entityName.toLowerCase() + "/update/' + ${" + entityName.toLowerCase() + "." + EntityGenerator.resolveName1Dw(entityField.getName()) + "}}\" class=\"btn btn-inline btn-light\" title=\"Editar\"><i class=\"icons cui-pencil\"></i> </a>" + System.lineSeparator());  
				content.append("<a  href=\"#\" th:href=\"@{'/" + appCode + "/" + entityName.toLowerCase() + "/delete/' + ${" + entityName.toLowerCase() + "." + EntityGenerator.resolveName1Dw(entityField.getName()) + "}}\" class=\"btn btn-inline btn-light\" title=\"Eliminar\" onclick=\"return confirm('Deseja eliminar o registo?');\"><i class=\"icons cui-delete\"></i></a>" + System.lineSeparator());  
				content.append("</td>" + System.lineSeparator()); 
			}
		}
		content.append("</tr>" + System.lineSeparator()); 
		
		content.append("</tbody>" + System.lineSeparator()); 
		content.append("</table>" + System.lineSeparator() + System.lineSeparator()); 
	}
	
	private void msgAlert() {
		// Message Success 
		content.append("<div th:if=\"${messageSuccess != null}\" class=\"alert alert-success alert-dismissible fade show\" role=\"alert\">" + System.lineSeparator()); 
		content.append("<span th:text=\"${messageSuccess}\"></span>" + System.lineSeparator()); 
		content.append("<button class=\"close\" type=\"button\" data-dismiss=\"alert\" aria-label=\"Close\">" + System.lineSeparator()); 
		content.append("<span aria-hidden=\"true\">×</span>" + System.lineSeparator()); 
		content.append("</button>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append(System.lineSeparator()); 
		
		// Message Error  
		content.append("<div th:if=\"${messageError != null}\" class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">" + System.lineSeparator()); 
		content.append("<span th:text=\"${messageError}\"></span>" + System.lineSeparator()); 
		content.append("<button class=\"close\" type=\"button\" data-dismiss=\"alert\" aria-label=\"Close\">" + System.lineSeparator()); 
		content.append("<span aria-hidden=\"true\">×</span>" + System.lineSeparator()); 
		content.append("</button>" + System.lineSeparator()); 
		content.append("</div>" + System.lineSeparator()); 
		content.append(System.lineSeparator()); 
	}
	
	private void defineColumnPosition() {
		List<EntityField> head = new ArrayList<EntityField>(); 
		List<EntityField> tail = new ArrayList<EntityField>(); 
		for(EntityField field : this.entityFields) {
			if(!field.getName().equals(DataBaseAudit.AUDIT_CREATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_UPDATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_BY_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_LAST_MODIFIED_BY_COLUMN_NAME)) 
				head.add(field); 
			else 
				tail.add(field); 
		}
		entityFields = head; 
		entityFields.addAll(tail);
	}

}
