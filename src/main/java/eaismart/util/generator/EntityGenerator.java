package eaismart.util.generator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import eaismart.util.constants.CRUDArtifactType;
import eaismart.util.constants.DataBaseAudit;

/**
 * @author Iekiny Marcel
 * Feb 21, 2021
 */
public class EntityGenerator extends JavaFileGenerator{
	
	private String tableName;
	private String schema;
	private List<EntityField> fields; 
	
	public EntityGenerator(String fileName, String appCode, String entityName, List<String> imports, List<EntityField> fields, String tableName, String schema) {
		this(fileName, appCode, entityName); 
		this.imports = imports != null ? imports : new ArrayList<String>(); 
		this.imports.add("java.io.Serializable;");
		this.imports.add("javax.persistence.Entity;");
		this.imports.add("javax.persistence.Table;");
		this.imports.add("eaismart.entity.BaseEntity;");
        this.imports = this.imports.stream().distinct().map(obj->obj.replaceAll(";", "").replace("import", ""))
        		.filter(obj -> !obj.contains("javax.validation")) // Line to be removed  
        		.collect(Collectors.toList());
        this.imports.sort(Comparator.naturalOrder());
		this.fields = fields != null ? fields : new ArrayList<EntityField>(); 
		this.tableName = tableName; 
		this.schema = schema; 
	}
	
	public EntityGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName); 
		type = CRUDArtifactType.ENTITY; 
	}
	
	@Override
	protected void addPackageDefinition() {
		this.content.append("package eaismart.webapps." + this.appCode + ".entity;" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
	}

	@Override
	protected void addClassDefinition() {
		this.content.append("@Entity" + System.lineSeparator()); 
		this.content.append( (tableName != null && !tableName.isEmpty() ? "@Table(name = \"" + tableName + "\"" + (schema != null && !schema.isEmpty() ? ", schema = \"" + schema + "\"" : "") + ")" 
				: (schema !=null && !schema.isEmpty() ? "@Table(schema=\"" + schema + "\")" : "@Table"))  + System.lineSeparator()); 
		this.content.append("public class " + entityName + " extends BaseEntity implements Serializable {" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		this.content.append("private static final long serialVersionUID = 1L;" + System.lineSeparator()); 
		this.content.append(System.lineSeparator()); 
		// defining fields ... 
		this.fields.forEach(field->{
			if(field != null && field.getAttribute() != null && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_UPDATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_BY_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_LAST_MODIFIED_BY_COLUMN_NAME)) { 
				// remove  
				String []lines = field.getAttribute().split("\\n"); 
				String attrDefinition = "";
				for(String line :lines) 
					if(!line.contains("@NotEmpty") && !line.contains("@Size")) 
					attrDefinition += line; 
				content.append(attrDefinition); 
				this.content.append(System.lineSeparator()); 
			}
		});
		// defining setters & getters ... 
		this.content.append(System.lineSeparator()); 
		this.fields.forEach(field->{
			if(field != null && field.getGetterSetter() != null && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_UPDATED_AT_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_CREATED_BY_COLUMN_NAME) && !field.getName().equals(DataBaseAudit.AUDIT_LAST_MODIFIED_BY_COLUMN_NAME)) {
				content.append(field.getGetterSetter()); 
				this.content.append(System.lineSeparator()); 
			}
		});
		this.content.append("}"); 
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<EntityField> getFields() {
		return fields;
	}

	public static class EntityField {

        private String appName;
        private String imported;
        private String attribute;
        private String tableRelationClass;
        private String tableName;
        private String getterSetter;
        private String className;
        private String schemaName;
        private String name;
        private Object type;
        private int typeSql;
        private boolean isAutoIncrement;
        private boolean isPrimaryKey;
        private boolean isNullable;
        private boolean isForeignKey;
        private Integer size;
        private Object defaultValue;
        private String tableRelation;
        private String columnMap;
        private String connectionName;
        private String format = "yyyy-mm-dd";
        private String ColumnTypeName​;
        private boolean afterWhere = false;

        public String getSchemaName() {
            return schemaName;
        }

        public void setSchemaName(String schemaName) {
            this.schemaName = schemaName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public int getTypeSql() {
            return typeSql;
        }

        public void setTypeSql(int typeSql) {
            this.typeSql = typeSql;
        }

        public boolean isAutoIncrement() {
            return isAutoIncrement;
        }

        public void setAutoIncrement(boolean isAutoIncrement) {
            this.isAutoIncrement = isAutoIncrement;
        }

        public boolean isPrimaryKey() {
            return isPrimaryKey;
        }

        public void setPrimaryKey(boolean isPrimaryKey) {
            this.isPrimaryKey = isPrimaryKey;
        }

        public boolean isNullable() {
            return isNullable;
        }

        public void setNullable(boolean isNullable) {
            this.isNullable = isNullable;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        public boolean isForeignKey() {
            return isForeignKey;
        }

        public void setForeignKey(boolean isForeignKey) {
            this.isForeignKey = isForeignKey;
        }

        public String getTableRelation() {
            return tableRelation;
        }

        public void setTableRelation(String tableRelation) {
            this.tableRelation = tableRelation;
        }

        public String getColumnMap() {
            return columnMap;
        }

        public void setColumnMap(String columnMap) {
            this.columnMap = columnMap;
        }

        public String getConnectionName() {
            return connectionName;
        }

        public void setConnectionName(String connectionName) {
            this.connectionName = connectionName;
        }

        public String getColumnTypeName​() {
            return ColumnTypeName​;
        }

        public void setColumnTypeName​(String columnTypeName​) {
            ColumnTypeName​ = columnTypeName​;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public boolean isAfterWhere() {
            return afterWhere;
        }

        public void setAfterWhere(boolean afterWhere) {
            this.afterWhere = afterWhere;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getImported() {
            return imported;
        }

        public void setImported(String imported) {
            this.imported = imported;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getTableRelationClass() {
            return tableRelationClass;
        }

        public void setTableRelationClass(String tableRelationClass) {
            this.tableRelationClass = tableRelationClass;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getGetterSetter() {
            return getterSetter;
        }

        public void setGetterSetter(String getterSetter) {
            this.getterSetter = getterSetter;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        @Override
        public String toString() {
            return "Column [schemaName=" + schemaName + ", name=" + name + ", type=" + type + ", typeSql=" + typeSql
                    + ", isAutoIncrement=" + isAutoIncrement + ", isPrimaryKey=" + isPrimaryKey + ", isNullable="
                    + isNullable + ", isForeignKey=" + isForeignKey + ", size=" + size + ", defaultValue="
                    + defaultValue + ", tableRelation=" + tableRelation + ", columnMap=" + columnMap + "]";
        }

    }
}
