package eaismart.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import eaismart.dto.GeneratorDto;
import eaismart.util.constants.CRUDArtifact;
import eaismart.util.constants.CRUDArtifactType;
import eaismart.util.db.DatabaseMetadataHelper;
import eaismart.util.generator.CRUDGenerator;
import eaismart.util.generator.EntityGenerator.EntityField;
import eaismart.util.generator.entity.EntityGeneratorDataBaseMetaData;

/**
 * @author Iekiny Marcel
 * Feb 4, 2021
 */
@Service
@Transactional
public class CRUDGeneratorService { 
	
	@Autowired
    private DataSource dataSource;
	
	 @Value("${eai-smart.workspace}")
	 private String workspace;
	 
	 
	 public void loadTable(GeneratorDto generatorDto) throws Exception {
		 generatorDto.getTables().addAll(DatabaseMetadataHelper.getTables(DataSourceUtils.getConnection(dataSource), generatorDto.getSchema(), generatorDto.getType())); 
	 }
	 
	 public void loadSchemes(GeneratorDto generatorDto) throws Exception {
		 generatorDto.getSchemas().addAll(DatabaseMetadataHelper.getSchemas(DataSourceUtils.getConnection(dataSource))); 
	 }
	 
	 public void generate(GeneratorDto generatorDto) throws Exception { 
		 List<EntityField> entityFields = new ArrayList<EntityField>();
		 List<DatabaseMetadataHelper.Column> dbColumns = DatabaseMetadataHelper.getCollumns(DataSourceUtils.getConnection(dataSource), generatorDto.getSchema(), generatorDto.getTable());
		 for(DatabaseMetadataHelper.Column dbColumn : dbColumns) {
			 EntityField entityField = new EntityField();
			 new ModelMapper().map(dbColumn, entityField);
			 entityFields.add(entityField);
		 }
		 List<String> imports = EntityGeneratorDataBaseMetaData.getImport(dbColumns);
		 CRUDGenerator crudGenerator = new CRUDGenerator(workspace, generatorDto.getApplicationCode(), sanitizeTableName(generatorDto.getTable(), generatorDto.getTablePrefix()), imports, entityFields, generatorDto.getTable(), generatorDto.getSchema()); 
		 crudGenerator.excludeArtifacts(generatorDto.getFileCode());
		 crudGenerator.generate();
	 }
	 
	 public void loadArtifacts(GeneratorDto generatorDto) {
		 CRUDGenerator crudGenerator = new CRUDGenerator(generatorDto.getApplicationCode(), sanitizeTableName(generatorDto.getTable(), generatorDto.getTablePrefix())); 
		 Map<String, String> artifactPaths = crudGenerator.getArtifactPaths(); 
		 generatorDto.setFileCode(new ArrayList<String>());
		 artifactPaths.forEach((k,v)->{
			 GeneratorDto.TableFile file = new GeneratorDto.TableFile(); 
			 file.setCode(k);
			 file.setValue(v);
			 String path = null; 
			 if(k.equals(CRUDArtifactType.VIEW_FORM.name()) || k.equals(CRUDArtifactType.VIEW_INDEX.name())) 
				 path = workspace + CRUDArtifact.RESOURCES_BASE_PATH + CRUDArtifact.VIEW_BASE_PATH + v;
			 else 
				 path = workspace + CRUDArtifact.JAVA_RESOURCES_BASE_PATH + CRUDArtifact.EAI_SMART_WEBAPPS_BASE_PATH + v;
			 file.setOverwrite(Files.exists(Paths.get(path))); 
			 if(!file.isOverwrite())	 
				 generatorDto.getFileCode().add(k);
			 generatorDto.getTableFiles().add(file); 
		 });
	 }
	 
	 public String sanitizeTableName(String tableName, String tablePrefix) {
		 tableName = tableName.replace(tablePrefix, "");
		 StringBuilder newName  = new StringBuilder(); 
		 for (String aux : tableName.split("_")) 
			 newName.append(aux.substring(0, 1).toUpperCase() + aux.substring(1).toLowerCase());
		 return StringUtils.capitalize(newName.toString()); 
	 }

}
