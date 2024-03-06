package eaismart.util.file.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author elton
 */
public class ExcelFile {
	
	private List<ArrayList<Object>> data;
	private String fileName; 
	private String currentSheetName; 
	
	public ExcelFile() {
		data = new ArrayList<ArrayList<Object>>(); 
	}
	
	public void loadFrom(InputStream inputStream) {
		Workbook workbook = null;
		
		try {
			workbook = new XSSFWorkbook(inputStream);
			
			//xlsx, xls 
			
	        Sheet FirstSheet = currentSheetName != null && !currentSheetName.isEmpty() ? workbook.getSheet(currentSheetName) : workbook.getSheetAt(0);
	        Iterator<Row> iterator = FirstSheet.iterator();

	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            ArrayList<Object> objectRow = new ArrayList<Object>(); 
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell nextCell = cellIterator.next();
	                switch (nextCell.getCellTypeEnum()) {
                    case STRING:
                    	objectRow.add(nextCell.getStringCellValue());
                        break;
                    case NUMERIC:
                    	objectRow.add(nextCell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                    	objectRow.add(nextCell.getBooleanCellValue());
                        break;
                    case FORMULA:
                    	
                    	switch(nextCell.getCachedFormulaResultTypeEnum()) {
                    		case NUMERIC: 
                    			objectRow.add(nextCell.getNumericCellValue());
                    		break;
                    		case STRING: 
                    			objectRow.add(nextCell.getRichStringCellValue());
                    		break;
                    	}
                    	
                    	break;
                    case BLANK:
                    case _NONE:
                    	objectRow.add(null);
                    break;
					default: // Error 
						objectRow.add(nextCell.getErrorCellValue());
						break;
	                }
	            }
	            data.add(objectRow);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				 workbook.close();
			     inputStream.close();
			} catch (Exception e2) {
				
			}
		}
        
		
	}

	public List<ArrayList<Object>> getData() {
		return data;
	}

	public String getCurrentSheet() {
		return currentSheetName;
	}

	public void setCurrentSheet(String currentSheetName) {
		this.currentSheetName = currentSheetName;
	}
	
}
