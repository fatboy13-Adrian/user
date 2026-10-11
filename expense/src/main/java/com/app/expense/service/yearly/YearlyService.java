package com.app.expense.service.yearly;
import java.util.List;
import com.app.expense.dto.YearlyDTO;

public interface YearlyService {
    YearlyDTO retrieveByYear (int year);
    List <YearlyDTO> retrieveAllYears ();
}