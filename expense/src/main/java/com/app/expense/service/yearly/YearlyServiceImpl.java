package com.app.expense.service.yearly;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app.expense.Expense;
import com.app.expense.ExpenseRepository;
import com.app.expense.calculator.YearlyCalculator;
import com.app.expense.dto.MonthlyDTO;
import com.app.expense.dto.YearlyDTO;
import com.app.expense.service.monthly.MonthlyService;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class YearlyServiceImpl implements YearlyService {
    private final ExpenseRepository repository;
    private final MonthlyService svc;;
    private final YearlyCalculator calculator;

    @Override
    public YearlyDTO retrieveByYear (int year) {
        YearlyDTO dto = 
        new YearlyDTO();
        dto.setYear(year);

        //Define start & end of entire year
        LocalDate startOfYear = 
        LocalDate
        .of(year, 1, 1);
        LocalDate endOfYear = 
        LocalDate
        .of(year, 12, 31);

        //Query DB ONCE for whole year instead of 12 times
        List <Expense> yearlyData = 
        repository
        .findByDateBetween(startOfYear, endOfYear);

        //Build MonthlyExpenseDTOs only for months that actually have data
        List <MonthlyDTO> mdtoList = 
        yearlyData
        .stream()
        .map(e -> 
        YearMonth
        .from(e.getDate()))
        .distinct()
        .map(svc::retrieveByMonth)
        .toList();

        //Run clean calculator
        calculator
        .calculateYearlyExpenses(dto, mdtoList);
        return dto;
    }

    @Override
    public List <YearlyDTO> retrieveAllYears () {
        //Retrieve existing record entries
        List <Expense> eList = 
        repository
        .findAll();

        //Extract unique, sorted years directly from list
        return eList
        .stream()
        .map(e -> 
        e.getDate()
        .getYear())
        .distinct()
        .sorted()
        .map(this::retrieveByYear)
        .toList();
    }
}   