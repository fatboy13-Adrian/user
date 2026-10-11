package com.app.expense.monthly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.app.expense.dto.MonthlyDTO;
import com.app.expense.exception.ExportExcelFailedException;
import com.app.expense.service.monthly.MonthlyReportService;
import com.app.expense.service.monthly.MonthlyService;

@ExtendWith(MockitoExtension.class)
public class MonthlyReportServiceTest {
    @Mock
    private MonthlyService svc;

    @InjectMocks
    private MonthlyReportService reportSvc;

    private MonthlyDTO create () {
        MonthlyDTO dto = 
        new MonthlyDTO();
        dto.setMonth(YearMonth
        .of(2026, 1));
        dto.setIncome(new BigDecimal("5000.00"));
        dto.setSsb(new BigDecimal("1500.00"));
        return dto;
    }

    @Test
    void testGetMonthRange () {
        //Arrange
        Year targetYear = 
        Year.of(2026);
        MonthlyDTO mockDto = 
        create();
        when(svc
        .retrieveByMonth(any(YearMonth.class)))
        .thenReturn(mockDto);

        //Act
        List <MonthlyDTO> result = 
        reportSvc.getMonthRange(targetYear);

        //Assert
        assertNotNull(result);
        assertEquals(12, result.size(), 
        "Should generate data for exactly 12 months");
        verify(svc, 
        times(12))
        .retrieveByMonth(any(YearMonth.class));
    }

    @Test
    void testExportMonthlyRecords () throws Exception {
        //Arrange
        Year targetYear = 
        Year.of(2026);
        MonthlyDTO mockDto = 
        create();
        when(svc
        .retrieveByMonth(any(YearMonth.class)))
        .thenReturn(mockDto);

        //Act
        ByteArrayInputStream inputStream = 
        reportSvc.exportMonthlyExpenseRecords(targetYear);

        //Assert
        assertNotNull(inputStream, 
        "The exported stream should not be null");
        assertTrue(inputStream.available() > 0, 
        "The stream should contain binary data");

        //Validate that generated stream is a legitimate readable Apache POI Excel workbook
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
        assertNotNull(workbook
        .getSheet("Expense Records for Year 2026"));
        assertEquals(13, 
        workbook.getSheetAt(0)
        .getPhysicalNumberOfRows(), 
        "Should contain 1 header row + 12 data rows");
        }
    }

    @Test
    void testExportMonthlyRecords_withNullValues () throws Exception {
        //Edge Case: DTO fields are completely null
        MonthlyDTO completelyNullDto = 
        new MonthlyDTO();
        completelyNullDto
        .setMonth(YearMonth
        .of(2026, 1));
        when(svc
        .retrieveByMonth(any(YearMonth.class)))
        .thenReturn(completelyNullDto);

        //Act
        ByteArrayInputStream inputStream = 
        reportSvc
        .exportMonthlyExpenseRecords(Year.of(2026));

        //Assert
        assertNotNull(inputStream);
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = 
            workbook
            .getSheetAt(0);
            Row firstDataRow = 
            sheet.getRow(1); 
            assertEquals(0.0, 
            firstDataRow.getCell(1)
            .getNumericCellValue(), 
            "Null fields should map to 0.0 in the Excel sheet matrix.");
        }
    }

    @Test
    void testExportMonthlyRecordsNotFound () {
        //Edge Case: underlying service layer encounters an issue & returns a hard null
        when(svc
        .retrieveByMonth(any(YearMonth.class)))
        .thenReturn(null);

        //Act & Assert
        ExportExcelFailedException exception = 
        assertThrows(ExportExcelFailedException.class, () -> {
            reportSvc
            .exportMonthlyExpenseRecords(Year
            .of(2026));
        });
        
        assertNotNull(exception);
        
        //Match actual runtime behavior: Validate the top-level exception message
        assertTrue(exception.getMessage()
        .contains("Failed to export monthly expense records"));
    }
}