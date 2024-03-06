/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.service;

import eaismart.dto.GeneratorDto;
import eaismart.entity.Application;
import eaismart.repository.IApplicationRepository;
import static eaismart.util.constants.CRUDArtifact.*;
import eaismart.util.db.DatabaseMetadataHelper;
import eaismart.util.db.DatabaseMetadataHelper.Column;
import eaismart.util.generator.entity.EntityGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author elton
 */
@Service
@Transactional
public class EntityGeneratorService {

    @Autowired
    DataSource dataSource;

    @Autowired
    IApplicationRepository applicationRepository;

    @Value("${eai-smart.workspace}")
    private String createDir;

    public void loadTable(GeneratorDto generatorDto) throws Exception {
        generatorDto.getTables().addAll(DatabaseMetadataHelper.getTables(DataSourceUtils.getConnection(dataSource), generatorDto.getSchema(), generatorDto.getType()));
    }

    public void loadSchemes(GeneratorDto generatorDto) throws Exception {
        generatorDto.getSchemas().addAll(DatabaseMetadataHelper.getSchemas(DataSourceUtils.getConnection(dataSource)));
    }

    public void entityGenerator(GeneratorDto generatorDto) throws Exception {
        Optional<List<Application>> code = applicationRepository.findByCode(generatorDto.getApplicationCode());

        if (code.isPresent()) {
            for (Application application : code.get()) {
                for (int i = 0; i < generatorDto.getSelectedTables().size(); i++) {
                    entityGenerator(application.getCode(), DataSourceUtils.getConnection(dataSource), generatorDto.getSchema(), generatorDto.getSelectedTables().get(i));
                }
            }
        }
    }

    public void entityGenerator(String applicationCode, Connection connection, String schema, String tableName) throws Exception {
        
        StringBuilder builder = new StringBuilder();

        String txt = new EntityGenerator().addClassDefinition(applicationCode, connection, schema, tableName);
        List<Column> column = DatabaseMetadataHelper.getCollumns(connection, schema, tableName);
       
        BufferedWriter bufferedWriter = null;
       
        final String fileName = column.get(0).getClassName() + ".java";
        final String locationName = createDir + JAVA_RESOURCES_BASE_PATH + EAI_SMART_WEBAPPS_BASE_PATH + File.separator + applicationCode.toLowerCase() + File.separator + "entity";
        builder.append(locationName);
        
        Path fileLocation = Paths.get(builder.toString());
        Files.createDirectories(fileLocation);

        try {

            Path newFilePath = Paths.get(fileLocation.toString() + File.separator + fileName);
            if (!Files.exists(newFilePath)) {
                Files.createFile(newFilePath);
            } else {
                System.out.println("Directory already exists");
            }

            if (newFilePath != null) {
                FileWriter fileWriter = new FileWriter(newFilePath.toString());
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(txt);
            }

        } catch (Exception ex) {
            throw new Exception("Could not create the directory where the files will be Created!" + ex);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (Exception ex) {
                throw new Exception("Error in closing BufferedWriter!" + ex);
            }
        }
    }
}
