/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.controller.api;

import eaismart.repository.IApplicationRepository;
import eaismart.service.EntityGeneratorService;
import eaismart.util.generator.entity.EntityGeneratorDataBaseMetaData;
import eaismart.util.db.DatabaseMetadataHelper;
import eaismart.util.file.excel.Excel2DataBaseService;
import eaismart.webapps.sigpc.dto.BalanceSheetDto;
import eaismart.webapps.sigpc.entity.Movement;
import eaismart.webapps.sigpc.service.BalanceSheetService;
import eaismart.webapps.sigpc.util.constants.Month;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javassist.NotFoundException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author elton
 */
@RestController
@RequestMapping("/api/v1/")
@Tag(name = "FILE SERVICES", description = "FILE SERVICES TO GENERATE DAO")
public class EntityAPIController {

    @Autowired
    DataSource dataSource;

    EntityGeneratorDataBaseMetaData java;

    @Autowired
    private BalanceSheetService balance;
            
    @Autowired
    private IApplicationRepository applicationRepository;

    @Autowired
    private EntityGeneratorService filesService;

    Logger logger = LoggerFactory.getLogger(EntityAPIController.class);

    @RequestMapping(value = "dao/txt/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Generate a new Table DAO. Send ?table_name = to difine the type of table.")
    public ResponseEntity<List<Movement>> create(
//            @PathVariable String table_name,
            @RequestParam("file") MultipartFile filename,
            @RequestParam(name = "type", required = false) String type) throws IOException, NotFoundException, Exception {

//        String schema = "public";
//
        if (filename != null) {
//
//              .entityGenerator(filename, schema, table_name);
            List<Movement> list = Excel2DataBaseService.importFromExcelFile(filename.getOriginalFilename());
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "directory not Created.");
        }
    }

    // Get Table Name from a connection
    @RequestMapping(value = "get_table", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getTables() throws Exception {

        Connection connection = dataSource.getConnection();
        String schema = "public";
        String table = "TABLE";

        return new ResponseEntity<>(DatabaseMetadataHelper.getTables(connection, schema, table), HttpStatus.OK);
    }

    // Get column MetaData from a table
//    @RequestMapping(value = "get_columns/{table_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<?>> getColumns(@PathVariable String table_name) throws Exception {
//
//        Connection connection = dataSource.getConnection();
//        String schema = "public";
//        return new ResponseEntity<>(EntityGeneratorDataBaseMetaData.getColumn(connection, schema, table_name), HttpStatus.OK);
//    }

    // Get collumns name of query
    @RequestMapping(value = "get_collumns/{table_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DatabaseMetadataHelper.Column>> getCollumns(@PathVariable String table_name) throws Exception {

        Connection connection = dataSource.getConnection();
        String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = " + table_name.toString();
        return new ResponseEntity<>(DatabaseMetadataHelper.getCollumns(connection, sql), HttpStatus.OK);
    }

    // Get schemas names from connection 
    @RequestMapping(value = "get_schema", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getSchema() throws Exception {
        Connection connection = dataSource.getConnection();
        return new ResponseEntity<>(DatabaseMetadataHelper.getSchemas(connection), HttpStatus.OK);
    }

    // Get Primary Key 
    @RequestMapping(value = "get_primary_key/{table_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<?>> getPrimaryKey(@PathVariable String table_name) throws Exception {

        Connection connection = dataSource.getConnection();
        String schema = "public";
        return new ResponseEntity<>(DatabaseMetadataHelper.getPrimaryKeys(connection, schema, table_name), HttpStatus.OK);
    }

    // Get IMPORTED 
    @RequestMapping(value = "get_imported/{table_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<?>> getImported(@PathVariable String table_name) throws Exception {

        Connection connection = dataSource.getConnection();
        String schema = "public";
        return new ResponseEntity<>(EntityGeneratorDataBaseMetaData.getImport(connection, schema, table_name), HttpStatus.OK);
    }

    // Get Foreign Keys Table Name
    @RequestMapping(value = "get_foreign_keys_table_name/{table_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getForeignKeysTableName(@PathVariable String table_name) throws Exception {

        Connection connection = dataSource.getConnection();
        String schema = "public";
        return new ResponseEntity<>(DatabaseMetadataHelper.getForeignKeysTableName(connection, schema, table_name), HttpStatus.OK);
    }

    // Get Foreign Keys Constrain Name
    @RequestMapping(value = "get_foreign_keys_constrain_name/{table_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getForeignKeysConstrainName(@PathVariable String table_name) throws Exception {

        Connection connection = dataSource.getConnection();
        String schema = "public";
        return new ResponseEntity<>(DatabaseMetadataHelper.getForeignKeysConstrainName(connection, schema, table_name), HttpStatus.OK);
    }
//
//    @RequestMapping(value = "balancete/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<BalanceSheetDto>> getAllBalanceSheetMonthly() throws Exception {
//        BalanceSheetDto _balanBalanceSheetDto = new BalanceSheetDto();
//        BalanceSheetDto __balanBalanceSheetDto = new BalanceSheetDto();
//        
//        final Long value = 2019L;
//        _balanBalanceSheetDto.setMonth("Diario_Jan");
//        _balanBalanceSheetDto.setYear(value);
//        
//        System.out.println("MONTH - " + Month.returnMonth(_balanBalanceSheetDto.getMonth()));
////        System.out.println("YEAR" + balanceSheetDto.getYear());
////        System.out.println("YEAR" + balanceSheetDto.getCompany());
//        
//        return new ResponseEntity<>(balance.getAllBalanceSheetByMonthly(_balanBalanceSheetDto), HttpStatus.OK);
//    }

}
