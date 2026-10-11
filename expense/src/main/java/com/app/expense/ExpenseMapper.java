package com.app.expense;
import org.springframework.stereotype.Component;
import com.app.expense.dto.ExpenseDTO;

@Component
public class ExpenseMapper {
    public Expense toEntity (ExpenseDTO dto) {
        return Expense.builder()
        .date(dto.getDate())
        .income(dto.getIncome())
        .emergency(dto.getEmergency())
        .srs(dto.getSrs())
        .ssb(dto.getSsb())
        .debt(dto.getDebt())
        .primeLife(dto.getPrimeLife())
        .criticare(dto.getCriticare())
        .termProtector(dto.getTermProtector())
        .mobile(dto.getMobile())
        .internet(dto.getInternet())
        .electricity(dto.getElectricity())
        .mortgage(dto.getMortgage())
        .incomeTax(dto.getIncomeTax())
        .propertyTax(dto.getPropertyTax())
        .parentsAllowances(dto.getParentsAllowances())
        .transport(dto.getTransport())
        .food(dto.getFood())
        .groceries(dto.getGroceries())
        .haircut(dto.getHaircut())
        .household(dto.getHousehold())
        .medical(dto.getMedical())
        .eatingOut(dto.getEatingOut())
        .holiday(dto.getHoliday())
        .recreational(dto.getRecreational())
        .shopping(dto.getShopping())
        .sports(dto.getSports())
        .tech(dto.getTech())
        .wake(dto.getWake())
        .wedding(dto.getWedding())
        .tithes(dto.getTithes())
        .build();
    }

    public ExpenseDTO toDTO (Expense e) {
        return new ExpenseDTO (
            e.getDate(),
            e.getIncome(),
            e.getEmergency(),
            e.getSrs(),
            e.getSsb(),
            e.getDebt(),
            e.getPrimeLife(),
            e.getCriticare(),
            e.getTermProtector(),
            e.getMobile(),
            e.getInternet(),
            e.getElectricity(),
            e.getMortgage(),
            e.getIncomeTax(),
            e.getPropertyTax(),
            e.getParentsAllowances(),
            e.getTransport(),
            e.getFood(),
            e.getGroceries(),
            e.getHaircut(),
            e.getHousehold(),
            e.getMedical(),
            e.getEatingOut(),
            e.getHoliday(),
            e.getRecreational(),
            e.getShopping(),
            e.getSports(),
            e.getTech(),
            e.getWake(),
            e.getWedding(),
            e.getTithes()
        );
    }

    public void updateExpense (ExpenseDTO dto, Expense e) {
        e.setIncome(dto.getIncome());
        e.setEmergency(dto.getEmergency());
        e.setSsb(dto.getSsb());
        e.setSrs(dto.getSrs());
        e.setDebt(dto.getDebt());
        e.setPrimeLife(dto.getPrimeLife());
        e.setCriticare(dto.getCriticare());
        e.setTermProtector(dto.getTermProtector());
        e.setMobile(dto.getMobile());
        e.setElectricity(dto.getElectricity());
        e.setInternet(dto.getInternet());
        e.setMortgage(dto.getMortgage());
        e.setIncomeTax(dto.getIncomeTax());
        e.setPropertyTax(dto.getPropertyTax());
        e.setParentsAllowances(dto.getParentsAllowances());
        e.setTransport(dto.getTransport());
        e.setFood(dto.getFood());
        e.setGroceries(dto.getGroceries());
        e.setHaircut(dto.getHaircut());
        e.setHousehold(dto.getHousehold());
        e.setMedical(dto.getMedical());
        e.setEatingOut(dto.getEatingOut());
        e.setHoliday(dto.getHoliday());
        e.setRecreational(dto.getRecreational());
        e.setShopping(dto.getShopping());
        e.setSports(dto.getSports());
        e.setTech(dto.getTech());
        e.setWake(dto.getWake());
        e.setWedding(dto.getWedding());
        e.setTithes(dto.getTithes());
    }
}