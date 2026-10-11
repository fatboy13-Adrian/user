package com.app.expense.controller;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.dto.ExpenseDTO;
import com.app.expense.service.daily.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Daily Expenses", description = "Endpoints for managing daily expenses")
public class ExpenseController {
    @Autowired	
    private ExpenseService svc;

    @PostMapping("/save")
    @Operation(summary = "Add a new expense record")
    public ResponseEntity <ExpenseDTO> save (@RequestBody ExpenseDTO dto) {
        return ResponseEntity
        .ok(svc
        .save(dto));
    }

    @GetMapping("/date/{date}")
    @Operation(summary = "Retrieve expense record by date")
    public ResponseEntity <ExpenseDTO> retrieveByDate (@PathVariable @DateTimeFormat
    (iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity
        .ok(svc
        .retrieveByDate(date));
    }

    @GetMapping("/dates")
    @Operation(summary = "Retrieve a paginated list of expense records")
    public ResponseEntity <Page <ExpenseDTO>> retrieveAllDates 
    (@RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
        .ok(svc
        .retrieveAllDates(page, size));
    }

    @DeleteMapping("/date/{date}")
    @Operation(summary = "Delete expense record by date")
    public ResponseEntity <Void> delete (@PathVariable 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        svc
        .delete(date);
        return ResponseEntity
        .noContent()
        .build();
    }
}