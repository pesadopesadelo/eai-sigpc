/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.util.generator.entity;

import eaismart.util.db.DatabaseMetadataHelper;
import eaismart.util.db.DatabaseMetadataHelper.Column;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author elton
 */

public class EntityGeneratorDataBaseMetaData {
    
    private EntityGeneratorDataBaseMetaData(){}

    //GET ATTRIBUTE
    public static final List<String> getAttribute(Connection connection, String schema, String tableName) throws Exception {

        List<Column> getColumn = DatabaseMetadataHelper.getCollumns(connection, schema, tableName);
        List<String> getAttibute = new ArrayList<>();

        for (int i = 0; i < getColumn.size(); i++) {
            getAttibute.add(getColumn.get(i).getAttribute());
        }

        return getAttibute;
    }

    //GET GETTER AND SETTER
    public static final List<String> getGetterSetter(Connection connection, String schema, String tableName) throws Exception {

        List<Column> getColumn = DatabaseMetadataHelper.getCollumns(connection, schema, tableName);
        List<String> getGetterSetter = new ArrayList<>();

        for (int i = 0; i < getColumn.size(); i++) {
            getGetterSetter.add(getColumn.get(i).getGetterSetter());
        }

        return getGetterSetter;
    }
    
    //GETTER AND SETTER
    public static final List<String> getImport(Connection connection, String schema, String tableName) throws Exception {
    	List<Column> getColumn = DatabaseMetadataHelper.getCollumns(connection, schema, tableName);
        return getImport(getColumn);
    }
    
    public static final List<String> getImport(List<DatabaseMetadataHelper.Column> getColumn) throws Exception{
    	List<String> getImport = new ArrayList<>();
        try {

            for (int i = 0; i < getColumn.size(); i++) {
                
                if (getColumn.get(i).getAttribute() != null) {

                    if (getColumn.get(i).getAttribute().contains("@Column")) {
                        getColumn.get(i).setImported("import javax.persistence.Column;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("@NotEmpty")) {
                        getColumn.get(i).setImported("import javax.validation.constraints.NotEmpty;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("@Size")) {
                        getColumn.get(i).setImported("import javax.validation.constraints.Size;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("@GeneratedValue")) {
                        getColumn.get(i).setImported("import javax.persistence.GeneratedValue;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("GenerationType")) {
                        getColumn.get(i).setImported("import javax.persistence.GenerationType;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("@Id")) {
                        getColumn.get(i).setImported("import javax.persistence.Id;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("@NotNull")) {
                        getColumn.get(i).setImported("import javax.validation.constraints.NotNull;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getType().equals(java.sql.Date.class) && getColumn.get(i).getAttribute().contains("Date")) {
                        getColumn.get(i).setImported("import java.util.Date;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getType().equals(java.util.Arrays.class) && getColumn.get(i).getAttribute().contains("Arrays")) {
                        getColumn.get(i).setImported("import java.util.Arrays;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getType().equals(java.math.BigDecimal.class) && getColumn.get(i).getAttribute().contains("BigDecimal")) {
                        getColumn.get(i).setImported("import java.math.BigDecimal;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getType().equals(java.util.List.class) && getColumn.get(i).getAttribute().contains("List")) {
                        getColumn.get(i).setImported("import java.util.List;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("ArrayList")) {
                        getColumn.get(i).setImported("import java.util.ArrayList;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("HashMap")) {
                        getColumn.get(i).setImported("import java.util.HashMap;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("@ManyToOne")) {
                        getColumn.get(i).setImported("import javax.persistence.ManyToOne;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("@JoinColumn")) {
                        getColumn.get(i).setImported("import javax.persistence.JoinColumn;");
                        getImport.add(getColumn.get(i).getImported());
                    }

                    if (getColumn.get(i).getAttribute().contains("Timestamp")) {
                        getColumn.get(i).setImported("import java.sql.Timestamp;");
                        getImport.add(getColumn.get(i).getImported());
                    }
                    if (getColumn.get(i).getAttribute().contains("FetchType")) {
                        getColumn.get(i).setImported("import javax.persistence.FetchType;");
                        getImport.add(getColumn.get(i).getImported());
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("ERROR in get getter and setter!");
        }
        return getImport;
    }

}
