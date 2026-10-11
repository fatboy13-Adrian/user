package com.app.expense.yearly;
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
import com.app.expense.Expense;
import com.app.expense.ExpenseRepository;
import com.app.expense.calculator.YearlyCalculator;
import com.app.expense.dto.MonthlyDTO;
import com.app.expense.dto.YearlyDTO;
import com.app.expense.service.monthly.MonthlyService;
import com.app.expense.service.yearly.YearlyServiceImpl;

@ExtendWith(MockitoExtension.class)
public class YearlyServiceTest {
    @InjectMocks
    private YearlyServiceImpl svc;
    
    @Mock
    private ExpenseRepository repository;

    @Mock
    private MonthlyService meExpSvc;

    @Mock
    private YearlyCalculator yeCalculator;

    private Expense e;
    private int year;
    private MonthlyDTO dto;

    @BeforeEach
    public void setUp () {
        year = 2024;
        e = new Expense();
        e.setDate(LocalDate
        .of(2024, 12, 31));
        dto = new MonthlyDTO();
        dto.setFood(BigDecimal
        .valueOf(150.00));
        dto.setGroceries(BigDecimal
        .valueOf(200.50));
        dto.setBills(BigDecimal
        .valueOf(85.25));
    }

    @Test
    void testRetrieveByYear () {
        //Arrange
        YearMonth targetMonth = 
        YearMonth.from(e.getDate());
        when(repository
        .findByDateBetween(
        LocalDate
        .of(2024, 1, 1),
        LocalDate
        .of(2024, 12, 31)))
        .thenReturn(List.of(e));
        when(meExpSvc
        .retrieveByMonth(targetMonth))
        .thenReturn(dto);

        //Act
        YearlyDTO result = 
        svc.retrieveByYear(year);

        //Assert
        assertNotNull(result);
        assertEquals(year, 
        result.getYear());
        
        //Verify downstream calculator logic execution
        verify(yeCalculator, 
        times(1))
        .calculateYearlyExpenses(result, 
        List.of(dto));
    }

    @Test
    void testRetrieveByYearNotFound () {
        //Define target year
        int targetYear = 2024; 

        //Configure mock matching target year
        when(repository.findByDateBetween(
        LocalDate
        .of(2024, 1, 1),
        LocalDate
        .of(2024, 12, 31)
        ))
        .thenReturn(List.of());

        //Pass matching targetYear variable
        YearlyDTO result = 
        svc.retrieveByYear(targetYear); 

        //Assertions
        assertNotNull(result);
        assertEquals(targetYear, 
        result.getYear());
        verify(yeCalculator, 
        times(1))
        .calculateYearlyExpenses(result, 
        List.of());
    }

    @Test
    void testRetrieveAllYears () {
        //Arrange
        Expense de2023 = 
        new Expense();
        de2023.setDate(LocalDate
        .of(2023, 5, 10));

        //Mock findAll to return an unsorted list with duplicate years to test stream logic
        when(repository.findAll())
        .thenReturn(List
        .of(e, de2023, e)); 

        //Mock inside calls triggered by this::retrieveByYear structure
        when(repository
        .findByDateBetween(LocalDate
        .of(2023, 1, 1), 
        LocalDate
        .of(2023, 12, 31)))
        .thenReturn(List
        .of(de2023));
        when(repository.findByDateBetween(LocalDate
        .of(2024, 1, 1), 
        LocalDate
        .of(2024, 12, 31)))
        .thenReturn(List.of(e));
        when(meExpSvc
        .retrieveByMonth(YearMonth
        .of(2023, 5)))
        .thenReturn(dto);
        when(meExpSvc
        .retrieveByMonth(YearMonth.of(2024, 12)))
        .thenReturn(dto);

        //Act
        List <YearlyDTO> resultList = 
        svc.retrieveAllYears();

        //Assert
        assertNotNull(resultList);
        assertEquals(2, 
        resultList.size());
        assertEquals(2023, 
        resultList.get(0)
        .getYear());
        assertEquals(2024, 
        resultList.get(1)
        .getYear());
    }

    @Test
    void testRetrieveAllYearsNotFound () {
        /**Arrange: Mock repository 
        to return an empty db list**/
        when(repository.findAll())
        .thenReturn(List.of());

        //Act: Invoke service method
        List <YearlyDTO> resultList = 
        svc.retrieveAllYears();

        /**Assert: Verify that a valid, 
        empty collection container is returned**/
        assertNotNull(resultList);
        assertTrue(resultList
        .isEmpty());
        assertEquals(0, 
        resultList.size());

        /**Verify: Ensure repository was queried 
        once & downstream methods were skipped**/
        verify(repository, 
        times(1))
        .findAll(); 
        verify(yeCalculator, 
        times(0))
        .calculateYearlyExpenses(any(), 
        any());
    }
}