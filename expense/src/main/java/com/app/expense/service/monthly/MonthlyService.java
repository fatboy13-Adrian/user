package com.app.expense.service.monthly;
import java.time.YearMonth;
import org.springframework.data.domain.Page;
import com.app.expense.dto.MonthlyDTO;

public interface MonthlyService {
    MonthlyDTO retrieveByMonth (YearMonth month);
    Page <MonthlyDTO> retrieveAllMonths (int page, int size);
}