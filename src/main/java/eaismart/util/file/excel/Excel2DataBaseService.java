/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eaismart.util.file.excel;

import eaismart.webapps.sigpc.entity.Classifier;
import eaismart.webapps.sigpc.entity.Movement;
import eaismart.webapps.sigpc.service.MovementService;
import eaismart.webapps.sigpc.util.constants.TransitionType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author elton
 */
public class Excel2DataBaseService {

    @Autowired
    MovementService movementService;
    
    private static Object getCellValue(Cell cell) {
        if (cell.getCellTypeEnum().equals(CellType.STRING)) {
            return cell.getStringCellValue();
        } else if (cell.getCellTypeEnum().equals(CellType.BOOLEAN)) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
            return cell.getNumericCellValue();
        }
        return null;
    }

    private static Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    public static List<Movement> importFromExcelFile(String excelFilePath) throws IOException {

        List<Movement> listMovment = new ArrayList<>();
        
        try (InputStream inputStream = Excel2DataBaseService.class.getResourceAsStream(new File(excelFilePath).getAbsolutePath())) {
            
            Workbook workbook = getWorkbook((FileInputStream) inputStream, excelFilePath);
            Sheet FirstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = FirstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Movement movement = new Movement();

                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 1:
                            movement.setDate((Date) getCellValue(nextCell));
                            break;
                        case 2:
                            movement.setRecipient((String) getCellValue(nextCell));
                            break;
                        case 3:
                            movement.setDescription((String) getCellValue(nextCell));
                            break;
                        case 4:
                            movement.setNumDoc((String) getCellValue(nextCell));
                            break;
                        case 5:
                            movement.setClassifier((Classifier) getCellValue(nextCell));
                            break;
                        case 6:
                            movement.setType((TransitionType) getCellValue(nextCell));
                            break;
                        case 7:
                            movement.setBalance((BigDecimal) getCellValue(nextCell));
                            break;
                    }
                }
                listMovment.add(movement);
            }
            
            workbook.close();
            inputStream.close();
            
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
      return listMovment;          
    }
}
