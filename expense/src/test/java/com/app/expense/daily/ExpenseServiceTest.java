package com.app.expense.daily;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.app.expense.Expense;
import com.app.expense.ExpenseMapper;
import com.app.expense.ExpenseRepository;
import com.app.expense.dto.ExpenseDTO;
import com.app.expense.exception.date.DateNotFoundException;
import com.app.expense.service.daily.ExpenseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @InjectMocks
    private ExpenseServiceImpl svc;

    @Mock
    private ExpenseRepository repository;

    @Mock
    private ExpenseMapper mapper;

    private LocalDate date;
    private Expense e;
    private ExpenseDTO dto;

    @BeforeEach
    public void setUp () {
        date = LocalDate
        .of(2026, 1, 1);

        //DTO used as input for service methods
        dto = 
        new ExpenseDTO();
        dto.setDate(date);
        dto.setFood(BigDecimal
        .valueOf(119.99));
        dto.setGroceries(BigDecimal
        .valueOf(9.99));
        dto.setMedical(BigDecimal
        .valueOf(22.34));

        //Entity representing DB record
        e = new Expense();
        e.setDate(date);
        e.setFood(BigDecimal
        .valueOf(119.99));
        e.setGroceries(BigDecimal
        .valueOf(9.99));
        e.setMedical(BigDecimal
        .valueOf(22.34));
    }

    @Test 
    void testSave () {
        Expense mappedExpense = 
        new Expense(); 
        mappedExpense
        .setDate(date);
        Expense savedExpense = 
        new Expense(); 
        savedExpense.
        setDate(date);
        when(repository.
        findById(date))
        .thenReturn(Optional
        .empty());
        when(mapper
        .toEntity(dto))
        .thenReturn(mappedExpense);
        when(repository
        .save(mappedExpense))
        .thenReturn(savedExpense);
        when(mapper
        .toDTO(savedExpense))
        .thenReturn(dto);
        ExpenseDTO result = 
        svc
        .save(dto);
        assertNotNull(result); 
        assertEquals(date, 
        result
        .getDate()); 
        assertEquals(dto
        .getFood(), 
        result
        .getFood()); 
        assertEquals(dto
        .getGroceries(), 
        result
        .getGroceries()); 
        assertEquals(dto
        .getMedical(), 
        result
        .getMedical());
        verify(repository, 
        times(1))
        .findById(date);
        verify(mapper, 
        times(1))
        .toEntity(dto);
        verify(repository, 
        times(1))
        .save(mappedExpense);
        verify(mapper, 
        times(1))
        .toDTO(savedExpense);
        verify(mapper, never())
        .updateExpense(any(ExpenseDTO.class), 
        any(Expense.class));
    }

    @Test 
    void testUpdateExistingRecord () {
        Expense savedExpense = 
        new Expense(); 
        savedExpense
        .setDate(date);
        when(repository
        .findById(date))
        .thenReturn(Optional
        .of(e));
        when(repository
        .save(e))
        .thenReturn(savedExpense);
        when(mapper
        .toDTO(savedExpense))
        .thenReturn(dto);
        ExpenseDTO result = 
        svc
        .save(dto);
        assertNotNull(result); 
        assertEquals(date, 
        result
        .getDate());
        verify(repository, 
        times(1))
        .findById(date); 
        verify(mapper, 
        times(1))
        .updateExpense(dto, e);
        verify(repository, 
        times(1))
        .save(e); 
        verify(mapper, 
        times(1))
        .toDTO(savedExpense);
        verify(mapper, never())
        .toEntity(dto);
    }

    @Test
    void testRetrieveByDate () {
        //Mock: expense exists for given date
        when(repository
        .findById(date))
        .thenReturn(Optional
        .of(e));
        when(mapper
        .toDTO(e))
        .thenReturn(dto);
        ExpenseDTO result = 
        svc
        .retrieveByDate(date);
        assertNotNull(result);
        assertEquals(dto
        .getDate(), 
        result.getDate());
        verify(repository, 
        times(1))
        .findById(date);
    }

    @Test
    void testRetrieveByDateNotFound () {
        //Mock: no expense found
        when(repository
        .findById(date))
        .thenReturn(Optional
        .empty());

        //Expect exception when not found
        DateNotFoundException exception =
        assertThrows(
        DateNotFoundException.class,
        () -> svc
        .retrieveByDate(date));
        assertNotNull(exception);
        verify(repository, 
        times(1))
        .findById(date);
    }

    @Test
    void testRetrieveAllDates () {
        //Define page number & page size
        int page = 1;
        int size = 10;

        //Create a Pageable that repository is expected to receive
        Pageable pageable;
        pageable = PageRequest
        .of(page - 1, size);

        //Create a list of expense entities
        List <Expense> expenseList = 
        List.of(e);

        //Create a Page containing expense entities
        Page <Expense> expensePage =
        new PageImpl <> (expenseList, 
        pageable, expenseList.size());

        //Mock repository response
        when(repository
        .findAll(pageable))
        .thenReturn(expensePage);

        //Mock entity-to-DTO mapping
        when(mapper.toDTO(e))
        .thenReturn(dto);

        //Call service method
        Page <ExpenseDTO> result =
        svc.retrieveAllDates(page, size);

        //Verify that a result was returned
        assertNotNull(result);

        //Verify page contains one DTO
        assertEquals(1, 
        result.getContent().size());

        //Verify that correct DTO was returned
        assertEquals(dto, 
        result.getContent().get(0));

        //Verify page number
        assertEquals(0, 
        result.getNumber());

        //Verify page size
        assertEquals(size, 
        result.getSize());

        //Verify that repository was called once
        verify(repository, 
        times(1))
        .findAll(pageable);

        //Verify that mapper was called once
        verify(mapper, 
        times(1))
        .toDTO(e);
    }

    @Test
    void testRetrieveAllDatesNotFound () {
        //Define page number & page size
        int page = 1;
        int size = 10;

        //Create expected Pageable
        Pageable pageable = 
        PageRequest.of(page - 1, size);

        //Mock repository to return an empty page
        Page <Expense> empty = new PageImpl<>(List
        .of(), pageable, 0);
        when(repository.findAll(pageable))
        .thenReturn(empty);

        //Call service method
        Page <ExpenseDTO> result = 
        svc.retrieveAllDates(page, size);

        //Verify that a result was returned
        assertNotNull(result);

        //Verify that page contains no records
        assertEquals(0, 
        result.getContent().size());

        //Verify that page is empty
        assertTrue(result.isEmpty());

        //Verify that total number of records is zero
        assertEquals(0, 
        result.getTotalElements());

        //Verify that repository was called once
        verify(repository, 
        times(1))
        .findAll(pageable);

        //Verify that mapper was never called
        verify(mapper, 
        times(0))
        .toDTO(any(Expense.class));
    }


    @Test
    void testDelete () {
        //Arrange
        date = LocalDate
        .of(2026, 1, 15);
        Expense expense = new Expense();
        when(repository.findById(date))
        .thenReturn(Optional.of(expense));

        //Act
        svc.delete(date);

        //Assert
        verify(repository, 
        times(1))
        .findById(date);
        verify(repository, 
        times(1))
        .deleteById(date);
    }

    @Test
    void testDeleteNotFound () {
        //Arrange
        date = LocalDate
        .of(2026, 01, 15);
        when(repository.findById(date))
        .thenReturn(Optional.empty());

        //Act & Assert
        DateNotFoundException exception = 
        assertThrows(DateNotFoundException.class,
        () -> svc.delete(date));

        //Verify exception was thrown
        assertNotNull(exception);

        //Verify that findById was called
        verify(repository, 
        times(1))
        .findById(date);

        //deleteById must NOT be called because date doesn't exist
        verify(repository, 
        never())
        .deleteById(date);
    }
}