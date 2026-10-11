package com.app.expense.yearly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.app.expense.dto.YearlyDTO;
import com.app.expense.exception.ExportExcelFailedException;
import com.app.expense.service.yearly.YearlyReportService;
import com.app.expense.service.yearly.YearlyService;

@ExtendWith(MockitoExtension.class)
public class YearlyReportServiceTest {
    @InjectMocks
    private YearlyReportService reportSvc;

    @Mock
    private YearlyService svc;

    private YearlyDTO create () {
        YearlyDTO dto =
        new YearlyDTO();
        dto.setYear(2026);
        dto.setIncome(BigDecimal
        .valueOf(5000.00));
        dto.setSavings(BigDecimal
        .valueOf(200.00));
        dto.setBills(BigDecimal
        .valueOf(200.00));
        dto.setDebt(BigDecimal
        .valueOf(300.00));
        dto.setFood(BigDecimal
        .valueOf(400.00));
        dto.setGroceries(BigDecimal
        .valueOf(100.00));
        dto.setHaircut(BigDecimal
        .valueOf(50.00));
        dto.setInsurances(BigDecimal
        .valueOf(150.00));
        dto.setMedical(BigDecimal
        .valueOf(100.00));
        dto.setTax(BigDecimal
        .valueOf(100.00));
        dto.setTithes(BigDecimal
        .valueOf(50.00));
        dto.setTransport(BigDecimal
        .valueOf(100.00));
        dto.setWants(BigDecimal
        .valueOf(200.00));
        dto.setOverspent(BigDecimal
        .valueOf(50.00));
        dto.setSavings(BigDecimal
        .valueOf(1900.00));
        return dto;
    }

    @Test
    void testExportYearlyExpenseRecords () throws Exception {
        //Create yearly expense DTO
        YearlyDTO dto = create();

        //Mock service
        when(svc.retrieveAllYears())
        .thenReturn(List.of(dto));

        //Call method
        ByteArrayInputStream result =
        reportSvc.exportYearlyRecords();

        //Verify result exists
        assertNotNull(result);

        //Verify service was called once
        verify(svc, 
        times(1))
        .retrieveAllYears();

        //Open generated Excel workbook
        try (Workbook workbook = new XSSFWorkbook(result)) {
            //Verify sheet exists
            assertEquals("Yearly Expense Records",
            workbook.getSheetAt(0).getSheetName());
            Sheet sheet = workbook.getSheetAt(0);

            //Header + 1 data row
            assertEquals(2, 
            sheet.getPhysicalNumberOfRows());

            //Verify headers & data
            Row row = sheet.getRow(1);
            assertEquals(2026,
            row.getCell(0)
            .getNumericCellValue());
            assertEquals(5000.00,
            row.getCell(1)
            .getNumericCellValue());
            assertEquals(1900.00,
            row.getCell(2)
            .getNumericCellValue());
            assertEquals(300.00,
            row.getCell(3)
            .getNumericCellValue());
            assertEquals(150.00,
            row.getCell(4)
            .getNumericCellValue());
            assertEquals(200.00,
            row.getCell(5)
            .getNumericCellValue());
            assertEquals(100.00,
            row.getCell(6)
            .getNumericCellValue());
        }
    }

    @Test
    void testExportYearlyRecordsEmpty () throws Exception {
        //Mock service to return no records
        when(svc.retrieveAllYears())
        .thenReturn(List.of());

        //Call method
        ByteArrayInputStream result =
        reportSvc.exportYearlyRecords();

        //Verify result exists
        assertNotNull(result);

        //Verify service called once
        verify(svc, 
        times(1))
        .retrieveAllYears();

        //Open workbook
        try (Workbook workbook = new XSSFWorkbook(result)) {
            Sheet sheet = 
            workbook.getSheetAt(0);

            //Header row should still exist
            assertEquals(1, 
            sheet.getPhysicalNumberOfRows());

            //Verify sheet name & heders
            assertEquals("Yearly Expense Records",
            sheet.getSheetName());
        }
    }

    @Test
    void testExportYearlyExpenseRecordsMultipleYears () throws Exception {
        //Create 1st year
        YearlyDTO dto2025 =
        create();
        dto2025.setYear(2025);

        //Create 2nd year
        YearlyDTO dto2026 =
        create();
        dto2026.setYear(2026);

        //Mock service
        when(svc.retrieveAllYears())
        .thenReturn(List.of(dto2025,dto2026));

        //Call method
        ByteArrayInputStream result =
        reportSvc.exportYearlyRecords();

        //Open workbook
        try (Workbook workbook = new XSSFWorkbook(result)) {
            Sheet sheet = 
            workbook
            .getSheetAt(0);

            //Header + 2 years
            assertEquals(3,
            sheet.getPhysicalNumberOfRows());

            //1st year
            assertEquals(2025, 
            sheet.getRow(1)
            .getCell(0)
            .getNumericCellValue());

            //2nd year
            assertEquals(2026, 
            sheet.getRow(2)
            .getCell(0)
            .getNumericCellValue());
        }

        //Verify service called once
        verify(svc, 
        times(1))
        .retrieveAllYears();
    }

    @Test void testExportYearlyRecordsNullValues() throws Exception { 
        //Create DTO with null financial values 
        YearlyDTO dto = 
        new YearlyDTO(); 
        dto.setYear(2026); 
        when(svc.retrieveAllYears())
        .thenReturn(List.of(dto));

        //Call method 
        ByteArrayInputStream result = 
        reportSvc.exportYearlyRecords(); 
        assertNotNull(result); 

        //Open workbook 
        try (Workbook workbook = new XSSFWorkbook(result)) { 
            Sheet sheet = 
            workbook
            .getSheetAt(0); 
            Row row = 
            sheet
            .getRow(1); 
            assertNotNull(row, 
            "Expected the yearly data row to exist"); 
            
            //Verify year 
            assertNotNull(row.getCell(0), 
            "Year cell should exist"); 
            assertEquals(2026.0, 
            row.getCell(0)
            .getNumericCellValue(),
            0.001); 

            //Verify all 17 financial fields & exported as zero 
            for (int i = 1; i <= 17; i++) { 
                Cell cell = row.getCell(i); 
                assertNotNull( cell, 
                "Expected financial cell at column index " + i); 
                assertEquals( 0.0, 
                cell.getNumericCellValue(), 
                0.001, "Expected zero at column index " + i); 
            } 
            
            //Verify total number of columns 
            assertEquals(18, 
            row.getLastCellNum(), 
            "Expected 1 year column & 17 financial columns"); 
        } 
    }

    @Test
    void testExportYearlyExpenseRecordsFailure () {
        when(svc.retrieveAllYears())
        .thenReturn(null);
        ExportExcelFailedException exception = 
        assertThrows(
        ExportExcelFailedException.class,
        () -> reportSvc.exportYearlyRecords());
        assertNotNull(exception);
    }
}