package com.app.expense.monthly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
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
import com.app.expense.controller.monthly.MonthlyController;
import com.app.expense.dto.MonthlyDTO;
import com.app.expense.service.monthly.MonthlyService;

@ExtendWith(MockitoExtension.class)
public class MonthlyControllerTest {
    //Mock service layer
    @Mock 
    private MonthlyService svc;

    //Controller under test with mocks injected
    @InjectMocks 
    private MonthlyController controller;

    //Reusable test values for consistency
    private static final BigDecimal FOOD = 
    BigDecimal.valueOf(515.14);
    private static final BigDecimal GROCERIES = 
    BigDecimal.valueOf(99.44);
    private static final BigDecimal MEDICAL = 
    BigDecimal.valueOf(45.67);

    private MonthlyDTO createMonthlyExpenseDTO () {
        MonthlyDTO dto = 
        new MonthlyDTO();
        dto.setMonth(YearMonth
        .of(2024, 12));
        dto.setFood(FOOD);
        dto.setGroceries(GROCERIES);
        dto.setMedical(MEDICAL);
        return dto;
    }

    @Test 
    void testRetrieveByMonth () {
        YearMonth month = 
        YearMonth
        .of(2024, 12);
        MonthlyDTO dto = 
        createMonthlyExpenseDTO();
        when(svc
        .retrieveByMonth(month))
        .thenReturn(dto);
        ResponseEntity <MonthlyDTO> response = 
        controller.retrieveByMonth(month);
        assertEquals(HttpStatus.OK,
        response.getStatusCode());
        MonthlyDTO body =
        response.getBody();
        assertNotNull(body);
        assertEquals(month, 
        body.getMonth());
        assertEquals(FOOD, 
        body.getFood());
        assertEquals(GROCERIES,
        body.getGroceries());
        assertEquals(MEDICAL, 
        body.getMedical());
        verify(svc).
        retrieveByMonth(month);
    }

    @Test 
    void testRetrieveAllMonths () {
        //Test pagination values
        int page = 1;
        int size = 10;

        //Create test DTO
        MonthlyDTO dto =
        createMonthlyExpenseDTO();

        //Create a Page containing the DTO
        Page <MonthlyDTO> serviceResult =
        new PageImpl <>(List.of(dto), 
        PageRequest.of(page - 1, size), 1);

        //Mock service response
        when(svc.retrieveAllMonths(page, size))
        .thenReturn(serviceResult);

        //Call controller
        ResponseEntity <Page <MonthlyDTO>> response =
        controller.retrieveAllMonths(page, size);

        //Check HTTP status
        assertEquals(HttpStatus.OK,
        response.getStatusCode());

        //Check response body
        Page<MonthlyDTO> body =
        response.getBody();
        assertNotNull(body);

        //Check number of records
        assertEquals(1,
        body.getContent().size());

        //Get 1st result
        MonthlyDTO result =
        body.getContent()
        .get(0);

        //Check monthly data
        assertEquals(
        YearMonth
        .of(2024, 12),
        result
        .getMonth());

        assertEquals(FOOD,
        result
        .getFood());
        assertEquals(GROCERIES, 
        result
        .getGroceries());
        assertEquals(MEDICAL, 
        result
        .getMedical());

        //Check pagination
        assertEquals(1,
        body.getTotalElements());

        //Verify service was called with correct parameters
        verify(svc)
        .retrieveAllMonths(page, size);
    }
}