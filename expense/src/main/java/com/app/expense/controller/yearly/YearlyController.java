package com.app.expense.controller.yearly;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.dto.YearlyDTO;
import com.app.expense.service.yearly.YearlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Yearly Expenses", description = "Endpoints for viewing yearly expenses")
public class YearlyController {
    @Autowired
    private YearlyService svc;

    @GetMapping("/year/{year}")
    @Operation(summary = "Retrieve expense record by year")
    public ResponseEntity <YearlyDTO> retrieveByYear (@PathVariable int year) {
        return ResponseEntity
        .ok(svc
        .retrieveByYear(year));
    }

    @GetMapping("/years")
    @Operation(summary = "Retrieve all historical yearly expense summaries")
    public ResponseEntity <List <YearlyDTO>> retrieveAllYears () {
        return ResponseEntity
        .ok(svc
        .retrieveAllYears());
    }
}