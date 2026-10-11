package com.app.expense.yearly;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.app.expense.controller.yearly.YearlyReportController;
import com.app.expense.service.yearly.YearlyReportService;

@ExtendWith(MockitoExtension.class)
public class YearlyReportControllerTest {
    //Mock report service
    @Mock
    private YearlyReportService svc;
    
    //Controller under test
    @InjectMocks
    private YearlyReportController controller;

    @Test 
    void testExportYearlyExpenseReport () throws Exception {
        //Fake excel content
        byte [] fileContent = 
        "Test excel file".getBytes();
        ByteArrayInputStream file =
        new ByteArrayInputStream(fileContent);

        //Mock report service
        when(svc.exportYearlyRecords())
        .thenReturn(file);

        //Call controller
        ResponseEntity <InputStreamResource> response =
        controller.exportYearlyRecords();

        //Check HTTP status
        assertEquals(200, 
        response.getStatusCode()
        .value());

        //Check response body
        InputStreamResource resource =
        response.getBody();
        assertNotNull(resource);

        //Check content type
        assertEquals(MediaType.
        parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), 
        response.getHeaders().getContentType());

        //Check content disposition
        String contentDisposition =
        response.getHeaders()
        .getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(contentDisposition);
        assertEquals("attachment; filename=\"Yearly Expense Records.xlsx\"",
        contentDisposition);

        //Check file content
        byte [] returnedContent =
        resource.getInputStream()
        .readAllBytes();
        assertArrayEquals(fileContent,
        returnedContent);

        //Verify service call
        verify(svc)
        .exportYearlyRecords();
    }

    @Test
    void testExportYearlyExpenseRecordsEmptyFile () throws IOException {
        //Empty file
        ByteArrayInputStream file =
        new ByteArrayInputStream(new byte[0]);

        //Mock report service
        when(svc.exportYearlyRecords())
        .thenReturn(file);

        //Call controller
        ResponseEntity <InputStreamResource> response =
        controller.exportYearlyRecords();

        //Check HTTP status
        assertEquals(200,
        response.getStatusCode()
        .value());

        //Check response body
        InputStreamResource resource =
        response.getBody();
        assertNotNull(resource);

        //Check content type
        assertEquals(MediaType.parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        response.getHeaders()
        .getContentType());

        //Check filename
        assertEquals("attachment; filename=\"Yearly Expense Records.xlsx\"",
        response.getHeaders()
        .getFirst(HttpHeaders.CONTENT_DISPOSITION));

        //Check file is empty
        assertEquals(0, 
        resource.getInputStream()
        .readAllBytes().length);

        //Verify service call
        verify(svc)
        .exportYearlyRecords();
    }
}