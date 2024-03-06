/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.util.generator.entity;

import eaismart.util.db.DatabaseMetadataHelper;
import eaismart.util.db.DatabaseMetadataHelper.Column;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import org.apache.commons.text.WordUtils;
import org.thymeleaf.util.StringUtils;

/**
 *
 * @author elton
 */

public class EntityGenerator extends GenericEntityFileGenerator {

    public String addVariable(Column column) throws SQLException, Exception {

        Column _column = new Column();
        _column.setName(nameNormalize(column.getName()));
        Column __column = new Column();
        __column.setName(resolveName1Dw(column.getName()));

        if (column.getType().toString().contains("class ")) {
            StringBuilder name = new StringBuilder();
            String value = column.getType().toString().replace("class ", "");
            String[] aux = value.split("\\.");
            value = aux[aux.length -1];

            name.append(value);

            //VARIABLE TYPE PRIMARYKEY
            if (column.isNullable() == false && column.isPrimaryKey() == true) {
                this.content.append("\t@Id" + System.lineSeparator());
                this.content.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)" + System.lineSeparator());
                this.content.append("\t@Column(updatable = false)" + System.lineSeparator());
                this.content.append("\tprivate " + name + " " + __column.getName() + ";" + System.lineSeparator());
            } else if (column.isNullable() == false && column.isPrimaryKey() == false && column.isForeignKey() == false) {
                this.content.append((column.getSize() != null ? "\t@Column(nullable = false, length = " + column.getSize() + ")"
                        : "\t@Column(nullable = false)") + System.lineSeparator());
                this.content.append("\t@NotEmpty(message = \"Provide a " + _column.getName() + "\")" + System.lineSeparator());
                this.content.append((column.getSize() != null ? "\t@Size(max = " + column.getSize() + ", message = \"" + _column.getName() + " must contain a maximum of " + column.getSize() + " characters\")" : "") + System.lineSeparator());
                this.content.append("\tprivate " + name + " " + __column.getName() + ";" + System.lineSeparator());
            } else if (column.isNullable() == true && column.isPrimaryKey() == false && column.isForeignKey() == false) {
                this.content.append((column.getSize() != null ? "\t@Column(length = " + column.getSize() + ")" : "") + System.lineSeparator());
                this.content.append((column.getSize() != null ? "\t@Size(max = " + column.getSize() + ", message = \"" + _column.getName() + " must contain a maximum of " + column.getSize() + " characters\")" : "") + System.lineSeparator());
                this.content.append("\tprivate " + name + " " + __column.getName() + ";" + System.lineSeparator());
            }

            //VARIABLE TYPE FOREING KEY
            if (column.isNullable() == false && column.isForeignKey() == true) {
                this.content.append("\t@ManyToOne(fetch = FetchType.EAGER)" + System.lineSeparator());
                this.content.append("\t@JoinColumn(name = \"" + column.getName() + "\", nullable = false, updatable = false)" + System.lineSeparator());
                this.content.append("\tprivate " + column.getTableRelationClass() + " " + __column.getName() + ";" + System.lineSeparator());
            } else if (column.isNullable() == true && column.isForeignKey() == true) {
                this.content.append("\t@ManyToOne(fetch = FetchType.EAGER)" + System.lineSeparator());
                this.content.append("\t@JoinColumn(name = \"" + column.getName() + "\", updatable = false)" + System.lineSeparator());
                this.content.append("\tprivate " + column.getTableRelationClass() + " " + __column.getName() + ";" + System.lineSeparator());
            }
        }
        return this.content.toString();
    }

    public String addGetterAndSetter(Column column) {

        Column __column = new Column();
        __column.setName(resolveName1Dw(column.getName()));
        Column _column = new Column();
        _column.setName(nameGetterSetterNormalize(column.getName()));

        if (column.getType().toString().contains("class ")) {
            StringBuilder name = new StringBuilder();
            String value = column.getType().toString().replace("class ", "");
            String[] aux = value.split("\\.");
            value = aux[aux.length -1];

            name.append(value);

            if (name != null && column.isForeignKey() == false) {
                this.content.append("\tpublic " + name + " get" + _column.getName() + "() {" + System.lineSeparator());
                this.content.append("\t\treturn " + __column.getName() + ";" + System.lineSeparator());
                this.content.append("\t}" + System.lineSeparator());
                this.content.append(System.lineSeparator());
                this.content.append("\tpublic void set" + _column.getName() + "(" + name + " " + __column.getName() + ") {" + System.lineSeparator());
                this.content.append("\t\tthis." + __column.getName() + " = " + __column.getName() + ";" + System.lineSeparator());
                this.content.append("\t}" + System.lineSeparator());
            } else if (column.isForeignKey() == true) {
                this.content.append("\tpublic " + column.getTableRelationClass() + " get" + _column.getName() + "() {" + System.lineSeparator());
                this.content.append("\t\treturn " + __column.getName() + ";" + System.lineSeparator());
                this.content.append("\t}" + System.lineSeparator());
                this.content.append(System.lineSeparator());
                this.content.append("\tpublic void set" + _column.getName() + "(" + column.getTableRelationClass() + " " + __column.getName() + ") {" + System.lineSeparator());
                this.content.append("\t\tthis." + __column.getName() + " = " + __column.getName() + ";" + System.lineSeparator());
                this.content.append("\t}" + System.lineSeparator());
            }
        }
        return this.content.toString();
    }

    public String classNameNormalize(String value) throws Exception {

        StringBuilder name = new StringBuilder();

        if (value.contains("table_")) {
            value = value.replace("table", "");
            value = convertToTileCaseWordUtilsFull(value.replace("_", " "));
            value = value.replace(" ", "");
            name.append(value);
        } else if (value.contains("tbl_")) {
            value = value.replace("tbl", "");
            value = convertToTileCaseWordUtilsFull(value.replace("_", " "));
            value = value.replace(" ", "");
            name.append(value);
        } else if (value.contains("wdpteste_")) {
            value = value.replace("wdpteste", "");
            value = convertToTileCaseWordUtilsFull(value.replace("_", " "));
            value = value.replace(" ", "");
            name.append(value);
        } else {
            throw new NotFoundException("Dao Class Name Normalize ERROR!");
        }
        return name.toString();
    }

    public static String nameNormalize(String value) {

        StringBuilder name = new StringBuilder();

        if (value.contains("_")) {
            name.append(convertToTileCaseWordUtilsFull(value.replace("_", " ")));
        } else {
            name.append(convertToTileCaseWordUtilsFull(value));
        }
        return name.toString();
    }

    public static String nameGetterSetterNormalize(String value) {

        StringBuilder name = new StringBuilder();

        if (value.contains("_")) {
            value = convertToTileCaseWordUtilsFull(value.replace("_", " "));
            value = value.replace(" ", "");
            name.append(value);
        } else {
            value = convertToTileCaseWordUtilsFull(value);
            name.append(value);
        }
        return name.toString();
    }

    public static String resolveName1Dw(String name) {

        StringBuilder nam = new StringBuilder();

        if(name.contains("fk")) {
            name = name.replace("fk", "");
        }
      
        for (String aux : name.split("_")) {
            nam.append(aux.substring(0, 1).toUpperCase() + aux.substring(1).toLowerCase());
        }
        String nameStr = nam.toString();

        return StringUtils.unCapitalize(nameStr.replace(" ", "_"));
    }

    // METHOD TITLE 
    public static String convertToTileCaseWordUtilsFull(String text) {
        return WordUtils.capitalizeFully(text);
    }

    public String addClassDefinition(String applicationCode, Connection connection, String schema, String tableName) throws Exception {

        List<Column> column = DatabaseMetadataHelper.getCollumns(connection, schema, tableName);

        //LIST GETTER AND SETTER
        List<String> listGetterSetter = EntityGeneratorDataBaseMetaData.getGetterSetter(connection, schema, tableName);
        listGetterSetter.toString().replaceAll(",", "").replaceFirst("^\\[", "").replaceFirst("\\]$", "");

        //LIST ATTRIBUTE
        List<String> listAttribute = EntityGeneratorDataBaseMetaData.getAttribute(connection, schema, tableName);
        listAttribute.toString().replaceAll(",", "").replaceFirst("^\\[", "").replaceFirst("\\]$", "");

        //LIST IMPORT
        List<String> listImport = EntityGeneratorDataBaseMetaData.getImport(connection, schema, tableName);
        listImport.add("import java.io.Serializable;");
        listImport.add("import javax.persistence.Entity;");
        listImport.add("import javax.persistence.Table;");
        listImport.add("import eaismart.entity.BaseEntity;");

        List<String> listWithoutDuplicates = listImport.stream()
                .distinct()
                .collect(Collectors.toList());

        Collections.sort(listWithoutDuplicates);

        listWithoutDuplicates.toString().replaceAll(",", "").replaceFirst("^\\[", "").replaceFirst("\\]$", "");

        this.content.append("package eaismart.webapps." + applicationCode.toLowerCase() + ".entity;" + System.lineSeparator());
        this.content.append(System.lineSeparator());
        this.content.append(StringUtils.join(listWithoutDuplicates, "\n") + System.lineSeparator());
        this.content.append(System.lineSeparator());
        this.content.append("/**" + System.lineSeparator());
        this.content.append("* @author eai-smart" + System.lineSeparator());
        this.content.append("* " + new SimpleDateFormat("MMM dd, yyyy").format(new Date()) + System.lineSeparator());
        this.content.append("*/" + System.lineSeparator());
        this.content.append(System.lineSeparator());
        this.content.append("@Entity" + System.lineSeparator());
        this.content.append("@Table(name = \"" + tableName + "\", schema = \"" + schema + "\")" + System.lineSeparator());
        this.content.append("public class " + column.get(0).getClassName() + " extends BaseEntity implements Serializable {" + System.lineSeparator());
        this.content.append(System.lineSeparator());
//        this.content.append(tableName != null && !tableName.isEmpty() ? +"\tprivate static final SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy.MM.dd.HH.mm.ss\");" + System.lineSeparator());
        this.content.append(System.lineSeparator());
        this.content.append("\tprivate static final long serialVersionUID = 1L;" + System.lineSeparator());
        this.content.append(System.lineSeparator());
        // DEFINITION FIELS
        this.content.append(StringUtils.join(listAttribute, "\n") + System.lineSeparator());
        this.content.append("\tpublic " + column.get(0).getClassName() + "() {" + System.lineSeparator());
        this.content.append("\t}" + System.lineSeparator());
        this.content.append(System.lineSeparator());
        // DEFENITION GETTER AND SETTER
        this.content.append(StringUtils.join(listGetterSetter, "\n"));
        this.content.append(System.lineSeparator());
        this.content.append(org.apache.commons.lang3.StringUtils.join("}"));

        return this.content.toString();
    }

}
