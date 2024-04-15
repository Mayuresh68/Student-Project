package com.mayuresh.student.ServiceImpl;

import com.mayuresh.student.Exception.StudentNotFoundException;
import com.mayuresh.student.Models.Employee;
import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Repository.EmployeeRepository;
import com.mayuresh.student.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee getEmp(int id) {
        Optional<Employee> studentOptional = employeeRepository.findById(id);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        }
        return null;
    }

    @Override
    public String getdecrypt(int id) {
        return null;
    }

    @Override
    public List<Employee> getall() {
      return employeeRepository.findAll();
    }
}
