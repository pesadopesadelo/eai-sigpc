package eaismart.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Iekiny Marcel
 * Feb 4, 2021
 */
public class GeneratorDto {
	
	private String applicationCode; 
	private String schema; 
	private String dataSource; 
	private List<String> schemas;
	private List<String> tables;
	private List<String> selectedTables;
	private List<String> types;
	private String type; 
	private List<TableFile> tableFiles; 
	private List<String> fileCode;
	private String table; 
	private String tablePrefix;
	
	public GeneratorDto() {
		schemas = new ArrayList<String>(Arrays.asList("NOT_SUPPORTED")); 
		types = new ArrayList<String>(Arrays.asList("table", "view")); 
		dataSource = "Default DS";
		tables = new ArrayList<String>(); 
		selectedTables = new ArrayList<String>(); 
		tableFiles = new ArrayList<TableFile>(); 
		fileCode = new ArrayList<String>();
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public List<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}
	
	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public List<String> getSelectedTables() {
		return selectedTables;
	}

	public void setSelectedTables(List<String> selectedTables) {
		this.selectedTables = selectedTables;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public static class TableFile{
		
		private String code; 
		private String value;
		private boolean overwrite;
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}

		public boolean isOverwrite() {
			return overwrite;
		}

		public void setOverwrite(boolean overwrite) {
			this.overwrite = overwrite;
		}
	}

	public List<TableFile> getTableFiles() {
		return tableFiles;
	}

	public void setTableFiles(List<TableFile> tableFiles) {
		this.tableFiles = tableFiles;
	}

	public List<String> getFileCode() {
		return fileCode;
	}

	public void setFileCode(List<String> fileCode) {
		this.fileCode = fileCode;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	
}
