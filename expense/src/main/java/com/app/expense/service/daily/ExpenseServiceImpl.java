package com.app.expense.service.daily;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.app.expense.Expense;
import com.app.expense.ExpenseMapper;
import com.app.expense.ExpenseRepository;
import com.app.expense.dto.ExpenseDTO;
import com.app.expense.exception.date.DateNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository repository;
    private final ExpenseMapper mapper;

    @Override
    @Transactional 
    public ExpenseDTO save (ExpenseDTO dto) {
        LocalDate date = dto.getDate();

        //Update existing expense
        Optional <Expense> existing = 
        repository.findById(date);
        if (existing.isPresent()) {
            Expense e = 
            existing
            .get();
            mapper
            .updateExpense(dto, e);
            Expense saved = 
            repository
            .save(e);
            return mapper
            .toDTO(saved);
        }

        //Create new expense
        Expense e = 
        mapper
        .toEntity(dto);
        Expense saved = 
        repository
        .save(e);
        return mapper
        .toDTO(saved);
    }

    @Override
    public ExpenseDTO retrieveByDate (LocalDate date) {
        Expense e = 
        repository
        .findById(date)
        .orElseThrow(() -> 
        new DateNotFoundException(date));
        return mapper
        .toDTO(e);
    }

    @Override
    public Page <ExpenseDTO> retrieveAllDates (int page, int size) {
        Pageable pageable = 
        PageRequest
        .of(page - 1, size);
        return repository
        .findAll(pageable)
        .map(mapper::toDTO);
    }

    @Override
    @Transactional
    public void delete (LocalDate date) {
        repository
        .findById(date)
        .orElseThrow(() -> 
        new DateNotFoundException(date));
        repository
        .deleteById(date);
    }
}