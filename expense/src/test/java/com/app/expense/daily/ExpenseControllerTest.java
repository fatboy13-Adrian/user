package com.app.expense.daily;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.app.expense.controller.ExpenseController;
import com.app.expense.dto.ExpenseDTO;
import com.app.expense.service.daily.ExpenseService;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {
    //Mock service layer
    @Mock 
    private ExpenseService svc;

    //Controller under test with mocks injected
    @InjectMocks 
    private ExpenseController controller;

    private ExpenseDTO dto;
    private LocalDate date;

    @BeforeEach
    void setup () {
        date = LocalDate
        .of(2026, 12, 31);
        dto = 
        new ExpenseDTO();
        dto.setDate(date);
        dto.setIncome(BigDecimal
        .valueOf(5000));
        dto.setFood(BigDecimal
        .valueOf(1234.56));
        dto.setGroceries(BigDecimal
        .valueOf(234.56));
    }

    @Test 
    void testSave () {
        when(svc
        .save(dto))
        .thenReturn(dto);
        ResponseEntity <ExpenseDTO> response = 
        controller
        .save(dto);
        assertEquals(HttpStatus.OK, 
        response
        .getStatusCode());
        ExpenseDTO responseBody = 
        response
        .getBody();
        assertNotNull(responseBody);
        assertEquals(date, 
        responseBody
        .getDate());
        assertEquals(BigDecimal
        .valueOf(5000), 
        responseBody
        .getIncome());
        assertEquals(BigDecimal
        .valueOf(1234.56), 
        responseBody
        .getFood());
        assertEquals(BigDecimal
        .valueOf(234.56), 
        responseBody
        .getGroceries());
        verify(svc)
        .save(dto);
    }

    @Test
    void testRetrieveByDate () {
        when(svc
        .retrieveByDate(date))
        .thenReturn(dto);
        ResponseEntity <ExpenseDTO> response = 
        controller
        .retrieveByDate(date);
        assertEquals(HttpStatus.OK, 
        response
        .getStatusCode());
        ExpenseDTO responseBody = 
        response
        .getBody();
        assertNotNull(responseBody); 
        assertEquals(date, 
        responseBody
        .getDate());
        assertEquals(BigDecimal
        .valueOf(5000), 
        responseBody
        .getIncome());
        verify(svc)
        .retrieveByDate(date);
    }

    @Test 
    void testRetrieveAllDates () {
        int page = 1; 
        int size = 10;
        Page <ExpenseDTO> serviceResult = 
        new PageImpl <>(List
        .of(dto), 
        PageRequest
        .of(page - 1, size), 
        1);
        when(svc
        .retrieveAllDates(page, size))
        .thenReturn(serviceResult);
        ResponseEntity <Page <ExpenseDTO>> response = 
        controller
        .retrieveAllDates(page, size);
        assertEquals(HttpStatus.OK, 
        response
        .getStatusCode());
        Page <ExpenseDTO> responseBody = 
        response
        .getBody();
        assertNotNull(responseBody); 
        assertEquals(1, 
        responseBody
        .getContent()
        .size());
        assertEquals(1, 
        responseBody
        .getTotalElements());
        assertEquals(date, 
        responseBody
        .getContent()
        .get(0)
        .getDate() );
        verify(svc)
        .retrieveAllDates(page, size);
    }

    @Test
    void testDelete () {
        doNothing()
        .when(svc)
        .delete(date);
        ResponseEntity <Void> response = 
        controller
        .delete(date);
        assertEquals(HttpStatus
        .NO_CONTENT, 
        response
        .getStatusCode());
        assertNull(response
        .getBody());
        verify(svc)
        .delete(date);
    }
}