package eaismart.util.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eaismart.util.constants.CRUDArtifactType;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public class RepositoryGenerator extends JavaFileGenerator{ 
	
	public RepositoryGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName);
		type = CRUDArtifactType.REPOSITORY;
		imports = Arrays.asList(
				"org.springframework.data.jpa.repository.JpaRepository", 
				"org.springframework.stereotype.Repository", 
				"eaismart.webapps." + appCode + ".entity." + entityName);
	}
	
	@Override
	protected void addPackageDefinition() {
		this.content.append("package eaismart.webapps." + this.appCode + ".repository;" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
	}
	
	@Override
	protected void addClassDefinition() {
		this.content.append("@Repository" + System.lineSeparator()); 
		this.content.append("public interface I" + this.entityName + "Repository extends JpaRepository<" + this.entityName + ", Long>{" + System.lineSeparator());
		this.content.append(System.lineSeparator()); 
		this.content.append("}" + System.lineSeparator()); 
	}
	
	/*public static void main(String[] args) {
		List<String> imports = new ArrayList<String>(); 
		imports.add("import java.util.List"); 
		List<EntityGenerator.EntityField> fields = new ArrayList<EntityGenerator.EntityField>(); 
		EntityGenerator.EntityField field1 = new EntityGenerator.EntityField();
		field1.setName("codigo");
		field1.setType("INTEGER");
		EntityGenerator.EntityField field2 = new EntityGenerator.EntityField();
		field1.setName("nome");
		field1.setType("VARCHAR");
		fields.add(field1);
		fields.add(field2); 
		EntityGenerator generator = new EntityGenerator("", "sysbanka", "Dominio", imports, fields);
		generator.setSchema("public");
		generator.setTableName("tbl_domninio");
		generator.generate();
	}*/
}
