package eaismart.util.db;

import eaismart.util.generator.entity.EntityGenerator;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Iekiny Marcel Dec 1, 2020
 */
public final class DatabaseMetadataHelper { 
	
	private DatabaseMetadataHelper() {}

    public static final String TABLE_TYPE_TABLE = "TABLE";
    public static final String TABLE_TYPE_VIEW = "VIEW";

    public static List<String> getTables(Connection connection, String schema, String type_table) {

        List<String> list = new ArrayList<>();

        try (ResultSet tables = connection.getMetaData().getTables(null, schema, null, new String[]{TABLE_TYPE_TABLE, TABLE_TYPE_VIEW})) {
            // Get All Tables on the schema database 
            while (tables.next()) {
                if ((TABLE_TYPE_TABLE.equalsIgnoreCase(type_table) && tables.getString("TABLE_TYPE").equalsIgnoreCase(TABLE_TYPE_TABLE))
                        || (TABLE_TYPE_VIEW.equalsIgnoreCase(type_table) && tables.getString("TABLE_TYPE").equalsIgnoreCase(TABLE_TYPE_VIEW))) {
                    list.add(tables.getString("TABLE_NAME")); // Get Table Name 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get primary key of table
    public static List<String> getPrimaryKeys(Connection connection, String schema, String tableName) {

        List<String> keys = new ArrayList<>();

        if (connection != null) {
            try (ResultSet keysR = connection.getMetaData().getPrimaryKeys(null, schema, tableName)) {
                while (keysR.next()) {
                    keys.add(keysR.getString("COLUMN_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return keys;
    }

    public static Map<String, String> getForeignKeysTableName(java.sql.Connection con, String schema, String tableName) {

        Map<String, String> keys = new HashMap<>();

        if (con != null) {
            try (ResultSet keysR = con.getMetaData().getImportedKeys(null, schema, tableName)) {
                while (keysR.next()) {
                    keys.put(keysR.getString("FKCOLUMN_NAME"),
                            keysR.getString("PKTABLE_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return keys;
    }

    public static Map<String, String> getForeignKeysConstrainName(java.sql.Connection con, String schema, String tableName) {
        Map<String, String> keys = new HashMap<>();
        if (con != null) {
            try (ResultSet keysR = con.getMetaData().getImportedKeys(null, schema, tableName)) {
                while (keysR.next()) {
                    keys.put(keysR.getString("FKCOLUMN_NAME"),
                            keysR.getString("FK_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return keys;
    }

    public static List<String> getExportedKeys(Connection connection, String schema, String tableName) {
        List<String> keys = new ArrayList<>();
        if (connection != null) {
            try (ResultSet keysR = connection.getMetaData().getExportedKeys(null, schema, tableName)) {
                while (keysR.next()) {
                    keys.add(keysR.getString("PKCOLUMN_NAME"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return keys;
    }

    // Get collumns name of query
    public static List<Column> getCollumns(Connection connection, String sql) {

        List<Column> list = new ArrayList<>();

        if (sql != null && !sql.isEmpty()) {
            try (PreparedStatement st = connection.prepareStatement(sql);
                    ResultSet rs = st.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnsCount = metaData.getColumnCount();
                for (int i = 1; i <= columnsCount; i++) {
                    Column col = new Column();
                    col.setName(metaData.getColumnName(i));
                    list.add(col);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static void setParameters(NamedParameterStatement q, List<Column> parametersMap) throws SQLException {
        for (Column col : parametersMap) {
            setParameter(q, col.getDefaultValue(), col);
        }
    }

    public static List<Column> getCollumns(Connection connection, List<Column> parametersMap, String sql) {

        List<Column> list = new ArrayList<>();

        if (sql != null && !sql.isEmpty()) {
            try (PreparedStatement st = connection.prepareStatement(sql)) {
                NamedParameterStatement q = new NamedParameterStatement(connection, sql);
                setParameters(q, parametersMap);
                try (ResultSet rs = q.executeQuery()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnsCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnsCount; i++) {
                        Column col = new Column();
                        col.setName(metaData.getColumnName(i));
                        list.add(col);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // Get collumns name of table 
    public static List<Column> getCollumns(Connection connection, String schema, String tableName) throws Exception {

        List<Column> list = new ArrayList<>();
        String justTablename = tableName;

        try {
            List<String> pkeys = getPrimaryKeys(connection, schema, tableName);
            Map<String, String> fkeys = getForeignKeysTableName(connection, schema, tableName);
            justTablename = (schema != null && !schema.equals("")) ? schema + "." + tableName : tableName;
            String sql = "SELECT * FROM " + justTablename;
            try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnsCount = metaData.getColumnCount();
                for (int i = 1; i <= columnsCount; i++) {
                    Column col = new Column();
                    col.setSchemaName(schema);
                    col.setTableName(tableName);
                    col.setTypeSql(metaData.getColumnType(i));
                    col.setSize(metaData.getColumnDisplaySize(i));
                    col.setNullable(metaData.isNullable(i) > 0);
                    col.setAutoIncrement(metaData.isAutoIncrement(i));
                    col.setColumnTypeName​(metaData.getColumnTypeName(i));
                    col.setName(metaData.getColumnName(i));
                    col.setPrimaryKey(pkeys != null && pkeys.contains(col.getName()));
                    if (fkeys != null && fkeys.containsKey(col.getName())) {
                        col.setForeignKey(true);
                        col.setTableRelation(fkeys.get(col.getName()));
                        List<String> colRelaction = getExportedKeys(connection, schema, col.getTableRelation());
                        if (colRelaction != null && colRelaction.size() > 0) {
                            col.setColumnMap(colRelaction.get(0));
                        }
                        col.setTableRelationClass(new EntityGenerator().classNameNormalize(col.getTableRelation()));
                    }
                    col.setType(sqlToJava(metaData.getColumnType(i)));
                    col.setClassName(new EntityGenerator().classNameNormalize(col.getTableName()));
                    col.setAttribute(new EntityGenerator().addVariable(col));
                    col.setGetterSetter(new EntityGenerator().addGetterAndSetter(col));
                    list.add(col);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get schemas names from connection 
    public static List<String> getSchemas(Connection connection) {
        List<String> schemes = new ArrayList<String>();
        try (ResultSet schemas = connection.getMetaData().getSchemas()) {
            while (schemas.next()) {
                String scheme = schemas.getString(1);
                if (!scheme.contains("pg_catalog") && !scheme.contains("information_schema")) {
                    schemes.add(scheme);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schemes;
    }

    public static void setParameter(NamedParameterStatement query, Object value, Column col) throws SQLException {
        if (value != null) {
            if (col.getType().equals(java.lang.Integer.class)) {
                query.setInt(col.getName(), Integer.parseInt(value.toString()));
            } else if (col.getType().equals(java.lang.Double.class)) {
                query.setDouble(col.getName(), Double.parseDouble(value.toString()));
            } else if (col.getType().equals(java.lang.Float.class)) {
                query.setFloat(col.getName(), Float.parseFloat(value.toString()));
            } else if (col.getType().equals(java.lang.Long.class)) {
                query.setLong(col.getName(), Long.parseLong(value.toString()));
            } else if (col.getType().equals(java.lang.Short.class)) {
                query.setShort(col.getName(), Short.parseShort(value.toString()));
            } else if (col.getType().equals(java.lang.Boolean.class)) {
                query.setBoolean(col.getName(), (Boolean) value);
            } else if (col.getType().equals(java.sql.Array.class)) {
                query.setArray(col.getName(), (Array) value);
            } else if (col.getType().equals(java.lang.Byte.class)) {
                query.setByte(col.getName(), (Byte) value);
            } else if (col.getType().equals(java.sql.Date.class) && value != null) {
                query.setDate(col.getName(), (Date) value);
            } else if (col.getType().equals(java.lang.String.class) || col.getType().equals(java.lang.Character.class) && value != null) {
                query.setString(col.getName(), value.toString());
            } else if (col.getType().equals(java.math.BigDecimal.class)) {
                query.setBigDecimal(col.getName(), (BigDecimal) value);
            } else {
                query.setObject(col.getName(), value);
            }
        } else {
            query.setObject(col.getName(), null);
        }
    }

    //Receive Sql Type and Return Java Type
    public static Object sqlToJava(int type) {

        Object result = Object.class;
        switch (type) {
            case Types.VARCHAR:
            case Types.NVARCHAR:
            case Types.LONGNVARCHAR:
            case Types.LONGVARCHAR:
                result = String.class;
                break;
            case Types.CHAR:
            case Types.NCHAR:
                result = java.lang.Character.class;
                break;
            case Types.BIT:
                result = Boolean.class;
                break;
            case Types.NUMERIC:
            case Types.DECIMAL:
                result = java.math.BigDecimal.class;
                break;
            case Types.TINYINT:
                result = Byte.class;
                break;
            case Types.SMALLINT:
                result = Short.class;
                break;
            case Types.INTEGER:
                result = Integer.class;
                break;
            case Types.BIGINT:
                result = Long.class;
                break;
            case Types.REAL:
            case Types.FLOAT:
                result = Float.class;
                break;
            case Types.DOUBLE:
                result = Double.class;
                break;
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                result = Byte[].class;
                break;
            case Types.DATE:
                result = java.sql.Date.class;
                break;
            case Types.TIME:
                result = java.sql.Time.class;
                break;
            case Types.TIMESTAMP:
                result = java.sql.Timestamp.class;
                break;
            case Types.ARRAY:
                result = java.sql.Array.class;
                break;
            case Types.CLOB:
                result = java.sql.Clob.class;
                break;
            case Types.BLOB:
                result = java.sql.Blob.class;
                break;
            case Types.NCLOB:
                result = java.sql.NClob.class;
                break;
            case Types.STRUCT:
                result = java.sql.Struct.class;
                break;
            case Types.REF:
                result = java.sql.Ref.class;
                break;
        }
        return result;
    }

    public static class Column {

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

        public Column() {
        }

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
