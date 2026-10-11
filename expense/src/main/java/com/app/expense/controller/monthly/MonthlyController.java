package com.app.expense.controller.monthly;
import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.dto.MonthlyDTO;
import com.app.expense.service.monthly.MonthlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/monthly")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Monthly Expenses", description = "Endpoints for viewing monthly expenses")
public class MonthlyController {
    @Autowired	
    private MonthlyService svc;

    @GetMapping("/month/{month}")
    @Operation(summary = "Retrieve expense record by month")
    public ResponseEntity <MonthlyDTO> retrieveByMonth 
    (@PathVariable @DateTimeFormat
    (pattern = "yyyy-MM") YearMonth month) {
        return ResponseEntity
        .ok(svc
        .retrieveByMonth(month));
    }

    @GetMapping("/months")
    @Operation(summary = "Retrieve a paginated list of monthly expense summaries")
    public ResponseEntity <Page <MonthlyDTO>> retrieveAllMonths
    (@RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
        .ok(svc
        .retrieveAllMonths(page, size));
    }
}