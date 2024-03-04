package com.mayuresh.student.Controller;

import com.mayuresh.student.Exception.StudentNotFoundException;
import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
            logger.info("exception in addStudent");
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(newStudent,HttpStatus.ACCEPTED);
    }


    // GET STUDENT BY ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudent(@PathVariable int id) throws Exception {
        try {
            Student student = studentService.getStudent(id);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with idd: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request");
        }
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

    @GetMapping("/getAllByEmail/{email}")
    public List<Student> getAllByEmail1(@PathVariable String email){
        return studentService.getAllByEmail(email);
    }

    @GetMapping("/getAllByEmail")
    public List<Student> getAllByEmail(@RequestHeader String email){
        return studentService.getAllByEmail(email);
    }


//    @PostMapping("/add")
//    public ResponseEntity<MyInvoice> addInvoice(@RequestParam(value = "file", required = false) MultipartFile image,
//                                                @RequestParam("data") String myinvoice) {
//        ResponseEntity<MyInvoice> responseEntity ;
//        try {
//            MyInvoice genericInput = new ObjectMapper().readValue(myinvoice, new TypeReference<MyInvoice>() {
//            });
//            System.out.println("controller called");
//            MyInvoice invoiceDo = myInvoiceService.addInvoice(genericInput, image);
//            responseEntity = new ResponseEntity<MyInvoice>(invoiceDo, HttpStatus.ACCEPTED);
//        } catch (Exception se) {
//            System.out.println(se);
//            responseEntity = new ResponseEntity<MyInvoice>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return responseEntity;
//    }


}
