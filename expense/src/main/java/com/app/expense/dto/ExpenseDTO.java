package com.app.expense.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private LocalDate date;
    private BigDecimal income = BigDecimal.ZERO;

    //Savings
    private BigDecimal emergency = BigDecimal.ZERO;
    private BigDecimal srs = BigDecimal.ZERO;
    private BigDecimal ssb = BigDecimal.ZERO;

    //Debt
    private BigDecimal debt = BigDecimal.ZERO;

    //Insurances
    private BigDecimal primeLife = BigDecimal.ZERO;
    private BigDecimal criticare = BigDecimal.ZERO;
    private BigDecimal termProtector = BigDecimal.ZERO;

    //Bills
    private BigDecimal internet = BigDecimal.ZERO;
    private BigDecimal electricity = BigDecimal.ZERO;
    private BigDecimal mobile = BigDecimal.ZERO;
    private BigDecimal mortgage = BigDecimal.ZERO;

    //Tax
    private BigDecimal incomeTax = BigDecimal.ZERO;
    private BigDecimal propertyTax = BigDecimal.ZERO;

    private BigDecimal parentsAllowances = BigDecimal.ZERO;
    private BigDecimal transport = BigDecimal.ZERO;
    private BigDecimal food = BigDecimal.ZERO;
    private BigDecimal groceries = BigDecimal.ZERO;
    private BigDecimal haircut = BigDecimal.ZERO;
    private BigDecimal household = BigDecimal.ZERO;
    private BigDecimal medical = BigDecimal.ZERO;

    //Wants
    private BigDecimal eatingOut = BigDecimal.ZERO;
    private BigDecimal holiday = BigDecimal.ZERO;
    private BigDecimal recreational = BigDecimal.ZERO;
    private BigDecimal shopping = BigDecimal.ZERO;
    private BigDecimal sports = BigDecimal.ZERO;
    private BigDecimal tech = BigDecimal.ZERO;

    //Others
    private BigDecimal wake = BigDecimal.ZERO;
    private BigDecimal wedding = BigDecimal.ZERO;

    //Tithes
    private BigDecimal tithes = BigDecimal.ZERO;
}