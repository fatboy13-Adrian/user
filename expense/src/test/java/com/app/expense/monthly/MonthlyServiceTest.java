package com.app.expense.monthly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import com.app.expense.Expense;
import com.app.expense.ExpenseRepository;
import com.app.expense.calculator.MonthlyCalculator;
import com.app.expense.dto.ExpenseDTO;
import com.app.expense.dto.MonthlyDTO;
import com.app.expense.service.monthly.MonthlyServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MonthlyServiceTest {
    @InjectMocks
    private MonthlyServiceImpl svc;
    
    @Mock
    private ExpenseRepository repository;

    @Mock
    private MonthlyCalculator meCalculator;

    private Expense e;

    @BeforeEach
    public void setUp () {
        //Declared missing date context variable
        LocalDate date = LocalDate
        .of(2024, 12, 31);
        
        ExpenseDTO dto = new ExpenseDTO(); 
        dto.setDate(date);
        dto.setFood(BigDecimal
        .valueOf(123.45));
        dto.setGroceries(BigDecimal
        .valueOf(9.99));
        dto.setMedical(BigDecimal
        .valueOf(22.34));

        //Entity representing db record
        e = new Expense();
        e.setFood(BigDecimal
        .valueOf(123.45));
        e.setGroceries(BigDecimal
        .valueOf(9.99));
        e.setMedical(BigDecimal
        .valueOf(22.34));
    }

    @Test
    void testRetrieveByMonth () {
        //Define month to retrieve
        YearMonth targetMonth = 
        YearMonth
        .of(2026, 1);

        //Call service method
        MonthlyDTO result = 
        svc
        .retrieveByMonth(targetMonth);

        //Assertions
        assertNotNull(result);
        assertEquals(targetMonth, 
        result.getMonth());

        //Verify monthly calculator execution
        verify(meCalculator, 
        times(1))
        .calculateMonthlyExpenses(result);
    }

    @Test 
    void testRetrieveByMonthNotFound () {
        //Define a target month
        YearMonth targetMonth = 
        YearMonth.of(2026, 1);

        //Call service method
        MonthlyDTO result = 
        svc.retrieveByMonth(targetMonth);
        
        /**Assert that a valid container
        DTO is still returned with month set**/
        assertNotNull(result);
        assertEquals(targetMonth, 
        result.getMonth());

        /**Verify that monthly calculator was 
        still invoked once to process empty DTO safely**/
        verify(meCalculator, 
        times(1))
        .calculateMonthlyExpenses(result);
    }

    @Test
    void testRetrieveAllMonths () {
        //Define page
        int page = 1;
        int size = 2;

        /**Create unsorted records with duplicate 
        months to test streaming logic**/
        Expense marchExpense = 
        new Expense();
        marchExpense
        .setDate(LocalDate
        .of(2026, 3, 15));

        Expense januaryExpense = 
        new Expense();
        januaryExpense
        .setDate(LocalDate
        .of(2026, 1, 1));

        Expense februaryExpense = 
        new Expense();
        februaryExpense
        .setDate(LocalDate
        .of(2026, 2, 10));
        
        Expense duplicateJanExpense = 
        new Expense();
        duplicateJanExpense
        .setDate(LocalDate
        .of(2026, 1, 20));

        when(repository.findAll()).thenReturn(List.of(
        marchExpense, 
        januaryExpense, 
        februaryExpense, 
        duplicateJanExpense));

        //Call the service with a page size of 2
        Page <MonthlyDTO> result = 
        svc.retrieveAllMonths(page, size);

        //Assertions
        assertNotNull(result);
        assertEquals(3, 
        result
        .getTotalElements()); 
        assertEquals(2, 
        result
        .getContent().size()); 

        //Verifies distinct sorting logic order
        assertEquals(YearMonth
        .of(2026, 1), 
        result
        .getContent()
        .get(0)
        .getMonth());
        assertEquals(YearMonth
        .of(2026, 2), 
        result
        .getContent()
        .get(1)
        .getMonth());

        verify(repository, 
        times(1))
        .findAll();
        
        /**Calculator triggers only twice
        for sliced content context window**/
        verify(meCalculator, 
        times(2))
        .calculateMonthlyExpenses(any(MonthlyDTO.class));
    }

    @Test
    void testRetrieveAllMonthsNotFound () {
        int page = 1;
        int size = 10;
        when(repository
        .findAll())
        .thenReturn(List.of());
        Page <MonthlyDTO> result = 
        svc.retrieveAllMonths(page, size);
        assertNotNull(result);
        assertEquals(0, 
        result.getContent()
        .size());
        assertTrue(result
        .isEmpty());
        assertEquals(0, 
        result
        .getTotalElements());
        verify(repository, 
        times(1))
        .findAll();
        verify(meCalculator, 
        times(0))
        .calculateMonthlyExpenses(any(MonthlyDTO.class));
    }
}