package eaismart.util.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import eaismart.util.constants.CRUDArtifact;
import eaismart.util.constants.CRUDArtifactType;
import eaismart.util.generator.EntityGenerator.EntityField;

import static eaismart.util.constants.CRUDArtifact.*;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class CRUDGenerator implements IGenerator{ 
	
	private List<IGenerator> generators;
	
	public CRUDGenerator(String parentDirName, String fileNameKey) {
		IGenerator serviceGenerator = new ServiceGenerator(File.separator + parentDirName + File.separator + CRUDArtifactType.SERVICE.name().toLowerCase() + File.separator + fileNameKey + SERVICE_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator repositoryGenerator = new RepositoryGenerator(File.separator + parentDirName + File.separator + CRUDArtifactType.REPOSITORY.name().toLowerCase() + File.separator + REPOSITORY_PREFIX + fileNameKey + REPOSITORY_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator controllerGenerator = new ControllerGenerator(File.separator + parentDirName + File.separator + CRUDArtifactType.CONTROLLER.name().toLowerCase() + File.separator + fileNameKey + CONTROLLER_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator entityGenerator = new EntityGenerator(File.separator + parentDirName + File.separator + CRUDArtifactType.ENTITY.name().toLowerCase() + File.separator + fileNameKey + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator dtoGenerator = new DtoGenerator(File.separator + parentDirName + File.separator + CRUDArtifactType.DTO.name().toLowerCase() + File.separator + fileNameKey + DTO_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator viewFormGenerator = new ViewFormGenerator(File.separator + parentDirName + File.separator + fileNameKey.toLowerCase() + File.separator + CRUDArtifactType.VIEW_FORM.name().replace(VIEW_PREFIX_NAME, "").toLowerCase() + VIEW_SUFFIX, parentDirName, fileNameKey); 
		IGenerator viewIndexGenerator = new ViewIndexGenerator(File.separator + parentDirName + File.separator + fileNameKey.toLowerCase() + File.separator  + CRUDArtifactType.VIEW_INDEX.name().replace(VIEW_PREFIX_NAME, "").toLowerCase() + VIEW_SUFFIX, parentDirName, fileNameKey); 
		generators = new ArrayList<IGenerator>(Arrays.asList(serviceGenerator, repositoryGenerator, controllerGenerator, entityGenerator, dtoGenerator, viewFormGenerator, viewIndexGenerator)); 
	}
	
	public CRUDGenerator(String workspace, String parentDirName, String fileNameKey, List<String> imports, List<EntityField> fields, String tableName, String schema) {
		String javaResourcesPath = workspace + CRUDArtifact.JAVA_RESOURCES_BASE_PATH + CRUDArtifact.EAI_SMART_WEBAPPS_BASE_PATH;
		String resourcesPath = workspace + CRUDArtifact.RESOURCES_BASE_PATH + CRUDArtifact.VIEW_BASE_PATH; 
		IGenerator serviceGenerator = new ServiceGenerator(javaResourcesPath + File.separator + parentDirName + File.separator + CRUDArtifactType.SERVICE.name().toLowerCase() + File.separator + fileNameKey + SERVICE_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator repositoryGenerator = new RepositoryGenerator(javaResourcesPath + File.separator + parentDirName + File.separator + CRUDArtifactType.REPOSITORY.name().toLowerCase() + File.separator + REPOSITORY_PREFIX + fileNameKey + REPOSITORY_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator controllerGenerator = new ControllerGenerator(javaResourcesPath + File.separator + parentDirName + File.separator + CRUDArtifactType.CONTROLLER.name().toLowerCase() + File.separator + fileNameKey + CONTROLLER_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey); 
		IGenerator entityGenerator = new EntityGenerator(javaResourcesPath + File.separator + parentDirName + File.separator + CRUDArtifactType.ENTITY.name().toLowerCase() + File.separator + fileNameKey + JAVA_FILE_SUFFIX, parentDirName, fileNameKey, imports, fields, tableName, schema); 
		IGenerator dtoGenerator = new DtoGenerator(javaResourcesPath + File.separator + parentDirName + File.separator + CRUDArtifactType.DTO.name().toLowerCase() + File.separator + fileNameKey + DTO_SUFFIX + JAVA_FILE_SUFFIX, parentDirName, fileNameKey, imports, fields); 
		IGenerator viewFormGenerator = new ViewFormGenerator(resourcesPath + File.separator + parentDirName + File.separator + fileNameKey.toLowerCase() + File.separator + CRUDArtifactType.VIEW_FORM.name().replace(VIEW_PREFIX_NAME, "").toLowerCase() + VIEW_SUFFIX, parentDirName, fileNameKey, fields); 
		IGenerator viewIndexGenerator = new ViewIndexGenerator(resourcesPath + File.separator + parentDirName + File.separator + fileNameKey.toLowerCase() + File.separator  + CRUDArtifactType.VIEW_INDEX.name().replace(VIEW_PREFIX_NAME, "").toLowerCase() + VIEW_SUFFIX, parentDirName, fileNameKey, fields); 
		generators = new ArrayList<IGenerator>(Arrays.asList(serviceGenerator, repositoryGenerator, controllerGenerator, entityGenerator, dtoGenerator, viewFormGenerator, viewIndexGenerator)); 
	}

	@Override
	public void generate() throws IOException {
		for(IGenerator generator : generators) {
			GenericFileGenerator genericGenerator = (GenericFileGenerator) generator; 
			genericGenerator.generate();
		}
	}
	
	public List<IGenerator> getGenerators() {
		return generators;
	}

	public void excludeArtifacts(List<String> includedArtiFactCode) {
		this.generators.removeIf(generator ->{ 
			GenericFileGenerator genericGenerator = (GenericFileGenerator) generator;
			return !includedArtiFactCode.contains(genericGenerator.getType().name());
		});
	}
	
	public Map<String, String> getArtifactPaths(){
		Map<String, String> result = new LinkedHashMap<String, String>(); 
		for(IGenerator generator : this.generators) {
			GenericFileGenerator genericFileGenerator = (GenericFileGenerator) generator;
			result.put(genericFileGenerator.getType().name(), genericFileGenerator.getPathName()); 
		}
		return result;
	}
	
}
