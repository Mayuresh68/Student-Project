package com.mayuresh.student.Controller;

import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    // ADD STUDENT
    @PostMapping("/add")
    public ResponseEntity addStudent(@Valid @RequestBody Student student){
        Student newStudent;
        try{
            newStudent = studentService.addStudent(student);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(newStudent,HttpStatus.ACCEPTED);
    }


    // GET STUDENT BY ID
    @GetMapping("/get/{id}")
    public Student getStudent(@PathVariable int id){
        return studentService.getStudent(id);
    }

    /*
    @GetMapping("/getById")
    public Student getStudentById(@RequestParam("id") int id){return studentService.getStudentById(id);}
    */

    //GET ALL STUDENTS
    @GetMapping("/getAll")
    public List<Student> getAllStudents(){
        return  studentService.getAllStudents();
    }

    //UPDATE STUDENT
    @PutMapping("/update/{id}")
    public ResponseEntity updateStudent(@Valid @PathVariable int id, @RequestBody Student student) {
        try {
            System.out.println("id"+" "+id);
            Student updatedStudent = studentService.updateStudent(id, student);
            System.out.println("updated student"+""+updatedStudent);
            if (updatedStudent != null) {
                return ResponseEntity.ok(updatedStudent);
            } else {
                String errorMessage = "Student with ID " + id + " not found";
                return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

}
