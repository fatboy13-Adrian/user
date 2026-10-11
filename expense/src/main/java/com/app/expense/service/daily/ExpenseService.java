package com.app.expense.service.daily;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import com.app.expense.dto.ExpenseDTO;

public interface ExpenseService {
    ExpenseDTO save (ExpenseDTO dto);
    ExpenseDTO retrieveByDate (LocalDate date);
    Page <ExpenseDTO> retrieveAllDates (int page, int size);
    void delete (LocalDate date);
}