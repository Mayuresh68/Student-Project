package com.mayuresh.student.Controller;

import com.mayuresh.student.Models.Employee;
import com.mayuresh.student.Service.EmployeeService;
import com.mayuresh.student.Util.EncryptionDecryption;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EncryptionDecryption encryptionDecryption;

    @GetMapping("get_emp")
    public String get_emp(){
        return "Hello Emp...!!";
    }
    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employee employee){
        Employee newEmp;
        try{
            newEmp = employeeService.addEmployee(employee);
        }
        catch (Exception e){
            logger.info("exception in addStudent");
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(newEmp,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/{id}")
    public String getEmp(@PathVariable int id) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Employee emp = employeeService.getEmp(id);
        System.out.println("EMP : "+ emp.toString());
//        return encryptionDecryption.encrypt(emp.toString());
        return encryptionDecryption.encrypt(encryptionDecryption.convertToJson(emp));
    }

    @GetMapping("/decrypt/{enc}")
    public String getdecrypt(@PathVariable String enc){
        return encryptionDecryption.decrypt(enc);
    }

    @GetMapping("/getall")
    public List<Employee> getall(){
        return employeeService.getall();
    }

}
