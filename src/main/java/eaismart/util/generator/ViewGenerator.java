package eaismart.util.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eaismart.util.generator.EntityGenerator.EntityField;

/**
 * @author Iekiny Marcel
 * Feb 21, 2021
 */
public abstract class ViewGenerator extends GenericFileGenerator{
	
	protected List<EntityField> entityFields; 
	
	public ViewGenerator(String fileName, String appCode, String entityName, List<EntityField> entityFields) {
		this(fileName, appCode, entityName); 
		this.entityFields = entityFields != null ? entityFields : new ArrayList<EntityField>(); 
	}
	
	public ViewGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName); 
	}
	
	@Override
	public final void generate() throws IOException{ // Template Method 
		buildContent();
		save(); 
	}
	
	protected abstract void buildContent();
}
