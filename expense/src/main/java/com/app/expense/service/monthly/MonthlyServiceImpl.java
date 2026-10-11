package com.app.expense.service.monthly;
import java.time.YearMonth;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.app.expense.Expense;
import com.app.expense.ExpenseRepository;
import com.app.expense.calculator.MonthlyCalculator;
import com.app.expense.dto.MonthlyDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthlyServiceImpl implements MonthlyService {
    private final ExpenseRepository repository;
    private final MonthlyCalculator calculator;

    @Override
    public MonthlyDTO retrieveByMonth (YearMonth month) {
        MonthlyDTO dto = 
        new MonthlyDTO();
        dto.setMonth(month);
        calculator
        .calculateMonthlyExpenses(dto);
        return dto;
    }

    @Override
    public Page <MonthlyDTO> retrieveAllMonths (int page, int size) {
        //Maintain 0-based conversion for internal processing bounds
        Pageable pageable = 
        PageRequest
        .of(page - 1, size);

        //Fetch records and extract unique months in memory
        List <Expense> eList = 
        repository
        .findAll();
        List <YearMonth> uniqueMonths = 
        eList
        .stream()
        .map(e -> YearMonth
        .from(e
        .getDate()))
        .distinct()
        .sorted()
        .toList();

        //Apply safe pagination window slicing filters
        int start = 
        Math
        .min((int) 
        pageable
        .getOffset(),
        uniqueMonths
        .size());
        int end =
        Math
        .min(start + size, 
        uniqueMonths
        .size());
        
        //Map ONLY sliced segment into calculation models
        List<MonthlyDTO> content = 
        uniqueMonths
        .subList(start, end)
        .stream()
        .map(this::retrieveByMonth)
        .toList();

        //Return standard PageImpl container mapping framework targets
        return new PageImpl <> (content, 
        pageable, 
        uniqueMonths
        .size());
    }
}