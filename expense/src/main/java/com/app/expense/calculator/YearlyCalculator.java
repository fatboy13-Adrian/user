package com.app.expense.calculator;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

import com.app.expense.dto.MonthlyDTO;
import com.app.expense.dto.YearlyDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class YearlyCalculator {
    public BigDecimal calculateSavings (YearlyDTO ydto, List <MonthlyDTO> mdto) {
        BigDecimal savings = BigDecimal.ZERO;

        for (MonthlyDTO monthly : mdto) {
            savings = 
            savings
            .add(monthly
            .getEmergency())
            .add(monthly
            .getSrs())
            .add(monthly
            .getSsb())
            .add(monthly
            .getAvailable());
        }

        return savings;
    }

    public void calculateYearlyExpenses (YearlyDTO ydto, List <MonthlyDTO> mdto) {
        BigDecimal income = BigDecimal.ZERO;
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
        BigDecimal overspent = BigDecimal.ZERO;

        //Sum up daily expenses for current year
        for (MonthlyDTO dto : mdto) {
            income = income
            .add(dto.getIncome());
            debt = debt
            .add(dto.getDebt());
            insurances = insurances
            .add(dto.getInsurances());
            bills = bills
            .add(dto.getBills());
            tax = tax
            .add(dto.getTax());
            transport = transport
            .add(dto.getTransport());
            parentsAllowances = parentsAllowances
            .add(dto.getParentsAllowances());
            food = food
            .add(dto.getFood());
            groceries = groceries
            .add(dto.getGroceries());
            haircut = haircut
            .add(dto.getHaircut());
            household = household
            .add(dto.getHousehold());
            medical = medical
            .add(dto.getMedical());
            wants = wants
            .add(dto.getWants());
            others = others
            .add(dto.getOthers());
            tithes = tithes
            .add(dto.getTithes());
            overspent = overspent
            .add(dto.getOverspent());
        }

        //Store calculated values into yearly expenses
        ydto.setIncome(income);
        ydto.setDebt(debt);
        ydto.setInsurances(insurances);
        ydto.setBills(bills);
        ydto.setTax(tax);
        ydto.setTransport(transport);
        ydto.setParentsAllowances(parentsAllowances);
        ydto.setFood(food);
        ydto.setGroceries(groceries);
        ydto.setHaircut(haircut);
        ydto.setHousehold(household);
        ydto.setMedical(medical);
        ydto.setWants(wants);
        ydto.setOthers(others);
        ydto.setTithes(tithes);
        ydto.setOverspent(overspent);
        ydto.setSavings(calculateSavings(ydto, mdto));
    }
}