package com.mayuresh.student.Service;


import com.mayuresh.student.Models.Employee;
import org.springframework.web.bind.annotation.PathVariable;

public interface EmployeeService {

    public Employee addEmployee(Employee employee);

    Employee getEmp(int id);

    public String getdecrypt(@PathVariable int id);
}
