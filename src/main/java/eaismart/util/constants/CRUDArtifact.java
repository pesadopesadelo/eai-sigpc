package eaismart.util.constants;

import java.io.File;

/**
 * @author Iekiny Marcel
 * Feb 20, 2021
 */
public final class CRUDArtifact {
	
	private CRUDArtifact() {}
	
	public static final String CONTROLLER_SUFFIX = "Controller";
	public static final String DTO_SUFFIX = "Dto";
	public static final String JAVA_FILE_SUFFIX = ".java"; // ENTITY_SUFIX 
	public static final String REPOSITORY_SUFFIX = "Repository";
	public static final String SERVICE_SUFFIX = "Service";
	public static final String REPOSITORY_PREFIX = "I";
	public static final String VIEW_FORM_NAME = "form";
	public static final String VIEW_INDEX_NAME = "index";
	public static final String VIEW_SUFFIX = ".html";
	public static final String VIEW_PREFIX_NAME = "VIEW_";
	public static final String JAVA_RESOURCES_BASE_PATH = File.separator + "src" + File.separator + "main" + File.separator + "java"; 
	public static final String EAI_SMART_WEBAPPS_BASE_PATH = File.separator + "eaismart" + File.separator + "webapps"; 
	public static final String RESOURCES_BASE_PATH = File.separator + "src" + File.separator + "main" + File.separator + "resources"; 
	public static final String VIEW_BASE_PATH = File.separator + "templates"; 
	
}
