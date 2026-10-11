package com.app.expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal income = BigDecimal.ZERO;

    //Savings
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal emergency = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal srs = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal ssb = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal debt = BigDecimal.ZERO;

    //Insurances
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal primeLife = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal criticare = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal termProtector = BigDecimal.ZERO;

    //Bills
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal mobile = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal internet = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal electricity = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal mortgage = BigDecimal.ZERO;

    //Tax
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal incomeTax = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal propertyTax = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal parentsAllowances = BigDecimal.ZERO;

    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal transport = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal food = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal groceries = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal haircut = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal household = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal medical = BigDecimal.ZERO;

    //Wants
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal eatingOut = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal holiday = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal recreational = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal shopping = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal sports = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal tech = BigDecimal.ZERO;

    //Others
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal wake = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal wedding = BigDecimal.ZERO;
    @Builder.Default
    @Column(precision = 10, scale = 2)
    private BigDecimal tithes = BigDecimal.ZERO;
}