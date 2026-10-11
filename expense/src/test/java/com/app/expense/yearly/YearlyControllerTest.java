package com.app.expense.yearly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.app.expense.controller.yearly.YearlyController;
import com.app.expense.dto.YearlyDTO;
import com.app.expense.service.yearly.YearlyService;

@ExtendWith(MockitoExtension.class)
public class YearlyControllerTest {
    //Mock service layer
    @Mock 
    private YearlyService svc;

    //Controller under test with mocks injected
    @InjectMocks
    private YearlyController controller;

    //Reusable test values for consistency
    private static final BigDecimal FOOD = 
    BigDecimal.valueOf(515.14);
    private static final BigDecimal GROCERIES = 
    BigDecimal.valueOf(99.44);
    private static final BigDecimal MEDICAL = 
    BigDecimal.valueOf(45.67);

    private YearlyDTO create (int year) {
        YearlyDTO yedto = new YearlyDTO();
        yedto.setYear(year);
        yedto.setFood(FOOD);
        yedto.setGroceries(GROCERIES);
        yedto.setMedical(MEDICAL);
        return yedto;
    }

    @Test 
    void testRetrieveByYear () {
        int year = 2024;
        YearlyDTO dto = 
        create(year);
        when(svc.retrieveByYear(year))
        .thenReturn(dto);
        ResponseEntity <YearlyDTO> response =
        controller.retrieveByYear(year);
        assertEquals(HttpStatus.OK,
        response.getStatusCode());
        YearlyDTO body = 
        response.getBody();
        assertNotNull(body);
        assertEquals(year, 
        body.getYear());
        assertEquals(FOOD, 
        body.getFood());
        assertEquals(GROCERIES, 
        body.getGroceries());
        assertEquals(MEDICAL, 
        body.getMedical());
        verify(svc)
        .retrieveByYear(year);
    }

    @Test 
    void testRetrieveAllYears () {
        YearlyDTO dto1 = 
        create(2024);
        YearlyDTO dto2 = 
        create(2025);
        when(svc.retrieveAllYears())
        .thenReturn(List.of(dto1, dto2));
        ResponseEntity <List <YearlyDTO>> response = 
        controller.retrieveAllYears();
        assertEquals(HttpStatus.OK,
        response.getStatusCode());
        List <YearlyDTO> body = 
        response.getBody();
        assertNotNull(body);
        assertEquals(2, 
        body.size());
        assertEquals(2024,
        body.get(0)
        .getYear());
        assertEquals(FOOD,
        body.get(0)
        .getFood());
        assertEquals(GROCERIES,
        body.get(0)
        .getGroceries());
        assertEquals(MEDICAL,
        body.get(0)
        .getMedical());
        assertEquals(2025,
        body.get(1)
        .getYear());
        assertEquals(FOOD,
        body.get(1)
        .getFood());
        assertEquals(GROCERIES,
        body.get(1)
        .getGroceries());
        assertEquals(MEDICAL,
        body.get(1)
        .getMedical());
        verify(svc)
        .retrieveAllYears();
    }
}