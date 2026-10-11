package com.app.expense;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository <Expense, LocalDate> {
    List <Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Page <Expense> findByDateBetween(LocalDate startdate, LocalDate endDate, Pageable pageable);
}