package eaismart.util.generator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import eaismart.util.constants.CRUDArtifact;
import eaismart.util.constants.CRUDArtifactType;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public abstract class GenericFileGenerator implements IGenerator{
	
	protected String pathName;
	protected StringBuilder content;
	protected String appCode;
	protected String entityName;
	protected CRUDArtifactType type; 
	
	public GenericFileGenerator() {
		content = new StringBuilder(); 
	}
	
	public GenericFileGenerator(String pathName, String appCode, String entityName) {
		this();
		this.pathName = pathName;
		this.appCode = appCode;
		this.entityName = entityName; 
	}

	public String getPathName() {
		return pathName;
	}

	public StringBuilder getContent() {
		return content;
	}

	public CRUDArtifactType getType() {
		return type;
	}
	
	protected void save() throws IOException {
		Path path = Paths.get(pathName);
		Path parentPath = path.getParent(); 
		if(Files.notExists(parentPath)) 
			Files.createDirectories(parentPath);
		Files.write(path, this.content.toString().getBytes(StandardCharsets.UTF_8));
	}
	
}
