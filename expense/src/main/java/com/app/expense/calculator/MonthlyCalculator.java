package com.app.expense.calculator;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import org.springframework.stereotype.Component;
import com.app.expense.Expense;
import com.app.expense.ExpenseRepository;
import com.app.expense.dto.MonthlyDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyCalculator {
    private final ExpenseRepository repository;

    public void calculateMonthlyExpenses (MonthlyDTO dto) {
        YearMonth month = 
        dto.getMonth();

        List <Expense> eList = repository.
        findByDateBetween(month
        .atDay(1), 
        month
        .atEndOfMonth());

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal emergency = BigDecimal.ZERO;
        BigDecimal srs = BigDecimal.ZERO;
        BigDecimal ssb = BigDecimal.ZERO;
        BigDecimal debt = BigDecimal.ZERO;
        BigDecimal insurances = BigDecimal.ZERO;
        BigDecimal bills = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal transport = BigDecimal.ZERO;
        BigDecimal parentsAllowances = BigDecimal.ZERO;
        BigDecimal food = BigDecimal.ZERO;
        BigDecimal groceries = BigDecimal.ZERO;
        BigDecimal haircut = BigDecimal.ZERO;
        BigDecimal household = BigDecimal.ZERO;
        BigDecimal medical = BigDecimal.ZERO;
        BigDecimal wants = BigDecimal.ZERO;
        BigDecimal others = BigDecimal.ZERO;
        BigDecimal tithes = BigDecimal.ZERO;

        /*Loop exclusively through standard 
        daily cash outflows*/
        for (Expense e : eList) {
            income = income
            .add(e.getIncome());
            emergency = emergency
            .add(e.getEmergency());
            srs = srs
            .add(e.getSrs());
            ssb = ssb
            .add(e.getSsb());
            debt = debt
            .add(e.getDebt());
            insurances = insurances
            .add(e.getPrimeLife())
            .add(e.getCriticare())
            .add(e.getTermProtector());
            bills = bills
            .add(e.getMobile())
            .add(e.getElectricity())
            .add(e.getInternet())
            .add(e.getMortgage());
            tax = tax
            .add(e.getIncomeTax())
            .add(e.getPropertyTax());
            transport = transport
            .add(e.getTransport());
            parentsAllowances = parentsAllowances
            .add(e.getParentsAllowances());
            food = food
            .add(e.getFood());
            groceries = groceries
            .add(e.getGroceries());
            haircut = haircut
            .add(e.getHaircut());
            household = household
            .add(e.getHousehold());
            medical = medical
            .add(e.getMedical());
            wants = wants
            .add(e.getEatingOut())
            .add(e.getHoliday())
            .add(e.getRecreational())
            .add(e.getShopping())
            .add(e.getSports())
            .add(e.getTech());
            others = others
            .add(e.getWake())
            .add(e.getWedding());
            tithes = tithes
            .add(e.getTithes());
        }

        /*Populate metrics cleanly &
        run updated calculation*/
        dto.setIncome(income);
        dto.setEmergency(emergency);
        dto.setSrs(srs);
        dto.setSsb(ssb);
        dto.setDebt(debt);
        dto.setInsurances(insurances);
        dto.setBills(bills);
        dto.setTax(tax);
        dto.setTransport(transport);
        dto.setParentsAllowances(parentsAllowances);
        dto.setFood(food);
        dto.setGroceries(groceries);
        dto.setHaircut(haircut);
        dto.setHousehold(household);
        dto.setMedical(medical);
        dto.setWants(wants);
        dto.setOthers(others);
        dto.setTithes(tithes);
        dto.setAvailable(calculateAvailable(dto));
    }

    public BigDecimal calculateAvailable (MonthlyDTO dto) {
        BigDecimal total = dto
        .getEmergency()
        .add(dto.getSrs())
        .add(dto.getSsb())
        .add(dto.getDebt())
        .add(dto.getInsurances())
        .add(dto.getBills())
        .add(dto.getTax())
        .add(dto.getTransport())
        .add(dto.getParentsAllowances())
        .add(dto.getFood())
        .add(dto.getGroceries())
        .add(dto.getHaircut())
        .add(dto.getHousehold())
        .add(dto.getMedical())
        .add(dto.getWants())
        .add(dto.getOthers())
        .add(dto.getTithes());
        
        return dto.getIncome()
        .subtract(total);
    }
}