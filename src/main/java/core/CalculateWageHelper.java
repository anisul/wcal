package core;

import dao.Dao;
import domain.Employee;
import domain.Entry;
import types.CalculateWageInput;
import types.CalculateWageOutput;
import utils.AppUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculateWageHelper extends AbstractHelper {
    private CalculateWageInput input;
    private Dao dao;

    public CalculateWageHelper(Dao dao, CalculateWageInput input) {
        init(input, new CalculateWageOutput(), dao);
        this.dao = dao;
        this.input = input;
    }

    @Override
    protected void executeHelper() {
        CalculateWageOutput output = (CalculateWageOutput) this.output;
        List<Employee> employeeList = new ArrayList<>();

        for (Entry entry: input.getEntryList()) {
            long entryId = entry.getId();
            boolean isInEntryList = false;

            for (Employee employee: employeeList) {
                if (employee.getId() == entryId) {
                    BigDecimal previousWage = employee.getWage();
                    BigDecimal calculatedWage = AppUtil.calculateWage(entry.getStartTime(), entry.getEndTime());

                    BigDecimal newWage = previousWage.add(calculatedWage).setScale(2, RoundingMode.CEILING);
                    employee.setWage(newWage);
                    isInEntryList = true;
                }
            }

            if (!isInEntryList) {
                Employee employee = new Employee();

                employee.setId(entry.getId());
                employee.setName(entry.getPersonName());
                employee.setWage(AppUtil.calculateWage(entry.getStartTime(), entry.getEndTime()));
                employeeList.add(employee);
            }
        }

        System.out.println("Id, Name, Wage");
        for (Employee employee: employeeList) {
            System.out.println(employee.getId() + ", " + employee.getName() + ", " + NumberFormat.getCurrencyInstance().format(employee.getWage()));
        }
    }
}
