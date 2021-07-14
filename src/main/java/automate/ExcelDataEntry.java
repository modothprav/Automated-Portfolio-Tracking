package automate;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataEntry {
    private final List<Transaction> transactions;
    private XSSFWorkbook workbook;
    private String filepath;

    /**
     * Constructor, creates an excel workook object in wich the transactions
     * data will be entered. If no file path is specified then the 
     * cnostructor will create a new workbook instead of creating a 
     * workbook instance of that file.
     * 
     * @param transactions A List of Transaction objects
     * @param filePath A String specifing the file path of the excel workbook
     */
    public ExcelDataEntry(List<Transaction> transactions, String filePath) {
        this.transactions = new ArrayList<Transaction>(transactions);
        this.filepath = filePath;
        // If file not specified then create a spreadsheet
        if (filePath == null) {
            this.workbook = new XSSFWorkbook();
        } else {
            try {
                FileInputStream file = new FileInputStream(new File(filePath));
                this.workbook = new XSSFWorkbook(file);
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateExcelFile() {
        XSSFSheet sheet = workbook.getSheet("Transactions");

        int rowCount = sheet.getLastRowNum() + 1;

        // Outer loop to fill rows
        for (Transaction t : this.transactions) {
            Row row = sheet.createRow(rowCount);

            // Inner loop to fill columns
            int columnCount = 0;
            while (true) {
                Cell cell = row.createCell(columnCount);
                
                switch(columnCount) {
                    case 0:
                        cell.setCellValue(t.getOrderID());
                        break;
                    case 1:
                        cell.setCellValue(t.getTraDate().toString());
                        break;
                    case 2: 
                        cell.setCellValue(t.getStock());
                        break;
                    case 3:
                        cell.setCellValue(t.getMarket());
                        break;
                    case 4:
                        cell.setCellValue(t.getQuantity());
                        break;
                    case 5:
                        cell.setCellValue(t.getPrice());
                        break;
                    case 6:
                        cell.setCellValue(t.getTransactionType());
                        break;
                    case 7: 
                        cell.setCellValue(t.getExchangeRate());
                        break;
                    case 8: 
                        cell.setCellValue(t.getFees());
                        break;
                    case 9:
                        cell.setCellValue(t.getCurrency());
                        break;
                    case 10:
                        cell.setCellValue(t.getAmount());
                        break;
                    case 11:
                        cell.setCellValue(t.getTransactionMethod());
                        break;
                }

                columnCount++;
                if (columnCount == 12) { break; }
            }

            rowCount++;
        }

        // Write the updated workbook to excel file
        try {
            FileOutputStream out = new FileOutputStream(this.filepath);
            workbook.write(out);
            out.close();
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();   
        } 
        
    }




}
