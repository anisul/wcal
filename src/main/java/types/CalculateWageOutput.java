package types;

import domain.Employee;

import java.util.ArrayList;
import java.util.List;

public class CalculateWageOutput extends BaseOutput {
    List<Employee> employeeList = new ArrayList<>();

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
