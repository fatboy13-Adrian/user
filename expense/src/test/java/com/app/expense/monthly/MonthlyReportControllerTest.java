package com.app.expense.monthly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Year;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.app.expense.controller.monthly.MonthlyReportController;
import com.app.expense.service.monthly.MonthlyReportService;

@ExtendWith(MockitoExtension.class)
public class MonthlyReportControllerTest {
    //Mock report service
    @Mock
    private MonthlyReportService svc;

    //Controller under test
    @InjectMocks
    private MonthlyReportController controller;

    private Year year; 
    private ByteArrayInputStream file;

    @BeforeEach void setUp() { 
        year = Year.of(2026); 
        file = new ByteArrayInputStream("Test Excel content".getBytes()); 
    }

    @Test
    void testExportMonthlyReport () throws IOException {
        //Arrange 
        when(svc.exportMonthlyExpenseRecords(year))
        .thenReturn(file);

        //Act 
        ResponseEntity<InputStreamResource> response = 
        controller.exportMonthlyReport(year);

        //Assert 
        assertEquals(HttpStatus.OK, 
        response.getStatusCode()); 
        assertNotNull(response
        .getBody());

        assertEquals(MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), 
        response.getHeaders()
        .getContentType());

        assertEquals("attachment; filename=\"Expense Records For Year 2026.xlsx\"", 
        response.getHeaders()
        .getFirst( HttpHeaders
        .CONTENT_DISPOSITION)); 
        verify(svc)
        .exportMonthlyExpenseRecords(year);
    }

    @Test
    void testExportMonthlyExpenseReportDifferentYear () throws IOException {
        //Arrange
        Year anotherYear = 
        Year.of(2025); 
        ByteArrayInputStream anotherExcelFile = 
        new ByteArrayInputStream("Another Excel file".getBytes());
        when(svc.exportMonthlyExpenseRecords(anotherYear))
        .thenReturn(anotherExcelFile);

        //Act 
        ResponseEntity<InputStreamResource> response = 
        controller.exportMonthlyReport(anotherYear);

        //Assert 
        assertEquals(HttpStatus.OK, 
        response.getStatusCode()); 
        assertNotNull(response.getBody());
        assertEquals("attachment; filename=\"Expense Records For Year 2025.xlsx\"", 
        response.getHeaders()
        .getFirst(HttpHeaders
        .CONTENT_DISPOSITION));
        verify(svc)
        .exportMonthlyExpenseRecords(anotherYear);
    }
}