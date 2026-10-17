package com.app.expense.service.yearly;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import com.app.expense.dto.YearlyDTO;
import com.app.expense.exception.ExportExcelFailedException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YearlyReportService {
    private final YearlyService svc;

    public ByteArrayInputStream exportYearlyRecords () {
        //Retrieve all yearly expense records
        List <YearlyDTO> ydto = 
        svc.retrieveAllYears();

        //Build & return Excel file
        return buildYearlyExcel(ydto);
    }

    private ByteArrayInputStream buildYearlyExcel
    (List <YearlyDTO> ydto) {
        try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream output = 
        new ByteArrayOutputStream()) {
            //Create worksheet
            Sheet sheet = 
            workbook
            .createSheet("Yearly Expense Records");

            //Create header style
            CellStyle headerStyle = 
            workbook
            .createCellStyle();
            Font font = 
            workbook
            .createFont();
            font
            .setBold(true);
            headerStyle
            .setFont(font);
            headerStyle
            .setAlignment(HorizontalAlignment.CENTER);

            //Create currency style
            CellStyle currencyStyle = 
            workbook
            .createCellStyle();
            currencyStyle
            .setDataFormat(workbook
            .createDataFormat()
            .getFormat("[$$-en-SG]#,##0.00"));
            currencyStyle
            .setAlignment(HorizontalAlignment.CENTER);

            //Define Excel column headers
            String[] columns = {
                "Year",
                "Income",
                "Emergency Fund",
                "Srs",
                "Ssb",
                "Debt",
                "Insurances",
                "Bills",
                "Tax",
                "Parent's Allowances",
                "Transport",
                "Food",
                "Groceries",
                "Haircut",
                "Household",
                "Medical",
                "Wants",
                "Others",
                "Tithes",
                "Overspent",
                "Available"
            };

            //Create header row
            Row header = 
            sheet
            .createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = 
                header
                .createCell(i);
                cell
                .setCellValue(columns[i]);
                cell
                .setCellStyle(headerStyle);
            }

            //Create data rows
            for (int i = 0; i < ydto.size(); i++) {
                //Retrieve current yearly expense DTO
                YearlyDTO dto = 
                ydto.get(i);

                //Create row
                Row row = 
                sheet
                .createRow(i + 1);

                //Set year
                row
                .createCell(0)
                .setCellValue(dto
                .getYear());

                //Store financial values
                BigDecimal[] values = {
                    dto.getIncome(),
                    dto.getEmergency(),
                    dto.getSrs(),
                    dto.getSsb(),
                    dto.getDebt(),
                    dto.getInsurances(),
                    dto.getBills(),
                    dto.getTax(),
                    dto.getParentsAllowances(),
                    dto.getTransport(),
                    dto.getFood(),
                    dto.getGroceries(),
                    dto.getHaircut(),
                    dto.getHousehold(),
                    dto.getMedical(),
                    dto.getWants(),
                    dto.getOthers(),
                    dto.getTithes(),
                    dto.getAvailable()
                };

                //Create financial cells
                for (int j = 0; j < values.length; j++) {
                    Cell cell = 
                    row
                    .createCell(j + 1);

                    //Prevent null values
                    BigDecimal value = 
                    values[j] != null
                    ? values[j] : BigDecimal.ZERO;

                    cell
                    .setCellValue(value
                    .doubleValue());
                    cell
                    .setCellStyle(currencyStyle);
                }
            }

            //Automatically resize columns
            for (int i = 0; i < columns.length; i++) {
                sheet
                .autoSizeColumn(i);
            }

            //Write workbook into output stream
            workbook
            .write(output);

            //Return Excel file
            return new ByteArrayInputStream(output.toByteArray());        
        } catch (Exception e) {
            //Handle Excel export failure
            throw new ExportExcelFailedException
            ("Failed to export yearly expense records", e);
        }
    }
}