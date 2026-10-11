package com.app.expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.app.expense.dto.ExpenseDTO;
import com.app.expense.service.daily.ExpenseService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExpenseLoader implements CommandLineRunner {
    private final ExpenseService svc;

    public ExpenseDTO build (
        LocalDate date,
        BigDecimal income,
        BigDecimal emergency,
        BigDecimal srs,
        BigDecimal ssb,
        BigDecimal debt,
        BigDecimal primeLife,
        BigDecimal criticare,
        BigDecimal termProtector,
        BigDecimal electricity,
        BigDecimal internet,
        BigDecimal mobile,
        BigDecimal mortgage,
        BigDecimal incomeTax,
        BigDecimal propertyTax,
        BigDecimal parentsAllowances,
        BigDecimal transport,
        BigDecimal food,
        BigDecimal groceries,
        BigDecimal haircut,
        BigDecimal household,
        BigDecimal medical,
        BigDecimal eatingOut,
        BigDecimal recreational,
        BigDecimal holiday,
        BigDecimal shopping,
        BigDecimal sports,
        BigDecimal tech,
        BigDecimal wake,
        BigDecimal wedding,
        BigDecimal tithes
    ) {
        //Create daily expense DTO instance
        ExpenseDTO dto = new ExpenseDTO();

        //Set date
        dto.setDate(date);

        dto.setIncome(income);
        dto.setEmergency(emergency);
        dto.setSsb(ssb);
        dto.setSrs(srs);
        dto.setDebt(debt);
        dto.setPrimeLife(primeLife);
        dto.setCriticare(criticare);
        dto.setTermProtector(termProtector);
        dto.setElectricity(electricity);
        dto.setInternet(internet);
        dto.setMobile(mobile);
        dto.setMortgage(mortgage);
        dto.setIncomeTax(incomeTax);
        dto.setPropertyTax(propertyTax);
        dto.setParentsAllowances(parentsAllowances);
        dto.setTransport(transport);
        dto.setFood(food);
        dto.setGroceries(groceries);
        dto.setHaircut(haircut);
        dto.setHousehold(household);
        dto.setMedical(medical);
        dto.setEatingOut(eatingOut);
        dto.setHoliday(holiday);
        dto.setRecreational(recreational);
        dto.setShopping(shopping);
        dto.setSports(sports);
        dto.setTech(tech);
        dto.setWake(wake);
        dto.setWedding(wedding);
        dto.setTithes(tithes);

        //Return fully constructed DTO
        return dto;
    }

    //App startup logic
    @Override
    public void run(String... args) {
        //Pre - load expense data for multiple years into DB at startup
        svc.save(build(LocalDate.of(2024, 12, 31),
            BigDecimal.valueOf(66667.67),   //income
            BigDecimal.valueOf(4900.0),     //emergency fund
            BigDecimal.valueOf(10000.0),    //ssb
            BigDecimal.valueOf(4500.00),    //srs
            BigDecimal.valueOf(14800.07),   //debt
            BigDecimal.valueOf(476.52),     //aia prime life
            BigDecimal.valueOf(581.40),     //hsbc criticare
            BigDecimal.valueOf(1250.16),    //hsbc term protector
            BigDecimal.ZERO,                    //electricity
            BigDecimal.valueOf(818.71),     //internet
            BigDecimal.valueOf(600.00),     //mobile phone
            BigDecimal.ZERO,                    //mortgage
            BigDecimal.ZERO,                    //iras
            BigDecimal.ZERO,                    //property tax
            BigDecimal.ZERO,                    //parent's allowance
            BigDecimal.valueOf(1584.50),    //transport
            BigDecimal.valueOf(4470.78),    //food
            BigDecimal.valueOf(491.13),     //groceries
            BigDecimal.valueOf(72.00),      //haircut
            BigDecimal.ZERO,                    //household  
            BigDecimal.valueOf(178.35),     //medical
            BigDecimal.valueOf(1000.0),    //eating out
            BigDecimal.valueOf(1500.0),     //holiday
            BigDecimal.valueOf(1500.0),     //recreational
            BigDecimal.valueOf(1250.51),    //shopping
            BigDecimal.ZERO,                    //sports
            BigDecimal.valueOf(4500.0),     //tech
            BigDecimal.ZERO,                    //wake
            BigDecimal.ZERO,                    //wedding
            BigDecimal.ZERO                     //tithes
        ));

        svc.save(build(LocalDate.of(2025, 12, 31),
            BigDecimal.valueOf(30174.32),   //income
            BigDecimal.valueOf(5100.0),     //emergency fund
            BigDecimal.valueOf(3000.00),    //ssb
            BigDecimal.ZERO,                    //srs
            BigDecimal.valueOf(8450.00),   //debt
            BigDecimal.valueOf(436.94),     //aia prime life
            BigDecimal.valueOf(533.11),     //hsbc criticare
            BigDecimal.valueOf(1146.32),    //hsbc term protector
            BigDecimal.valueOf(11.82),      //electricity
            BigDecimal.valueOf(576.74),     //internet
            BigDecimal.valueOf(576.74),     //mobile phone
            BigDecimal.ZERO,                    //mortgage
            BigDecimal.valueOf(93.00),      //iras
            BigDecimal.ZERO,                    //property tax
            BigDecimal.ZERO,                    //parent's allowance
            BigDecimal.valueOf(1212.00),    //transport
            BigDecimal.valueOf(4333.2),    //food
            BigDecimal.valueOf(491.44),     //groceries
            BigDecimal.valueOf(78.00),      //haircut
            BigDecimal.ZERO,                    //household  
            BigDecimal.valueOf(178.35),     //medical
            BigDecimal.valueOf(1351.87),    //eating out
            BigDecimal.valueOf(1337.47),    //holiday
            BigDecimal.valueOf(1500.0),     //recreational
            BigDecimal.valueOf(1250.51),    //shopping
            BigDecimal.ZERO,                    //sports
            BigDecimal.valueOf(4500.0),     //tech
            BigDecimal.ZERO,                    //wake
            BigDecimal.ZERO,                    //wedding
            BigDecimal.ZERO                     //tithes
        ));

        svc.save(build(LocalDate.of(2026, 12, 31),
            BigDecimal.valueOf(30174.32),   //income
            BigDecimal.valueOf(5100.0),     //emergency fund
            BigDecimal.ZERO,                    //ssb
            BigDecimal.valueOf(5100.0),     //srs
            BigDecimal.valueOf(8450.00),   //debt
            BigDecimal.valueOf(436.94),     //aia prime life
            BigDecimal.valueOf(533.11),     //hsbc criticare
            BigDecimal.valueOf(1146.32),    //hsbc term protector
            BigDecimal.valueOf(11.82),      //electricity
            BigDecimal.valueOf(576.74),     //internet
            BigDecimal.valueOf(576.74),     //mobile phone
            BigDecimal.ZERO,                    //mortgage
            BigDecimal.valueOf(93.00),      //iras
            BigDecimal.ZERO,                    //property tax
            BigDecimal.valueOf(5100.0),     //parent's allowance
            BigDecimal.valueOf(1212.00),    //transport
            BigDecimal.valueOf(4333.2),    //food
            BigDecimal.valueOf(491.44),     //groceries
            BigDecimal.valueOf(78.00),      //haircut
            BigDecimal.ZERO,                    //household  
            BigDecimal.valueOf(178.35),     //medical
            BigDecimal.valueOf(1351.87),    //eating out
            BigDecimal.valueOf(1337.47),    //holiday
            BigDecimal.valueOf(1500.0),     //recreational
            BigDecimal.valueOf(1250.51),    //shopping
            BigDecimal.ZERO,                    //sports
            BigDecimal.valueOf(4500.0),     //tech
            BigDecimal.valueOf(5100.0),     //wake
            BigDecimal.ZERO,                    //wedding
            BigDecimal.ZERO                     //tithes
        ));
    }
}