package com.mayuresh.student.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayuresh.student.Exception.StudentNotFoundException;
import com.mayuresh.student.Models.Student;
import com.mayuresh.student.RequestDTO.Sony;
import com.mayuresh.student.Response.GenericListResponse;
import com.mayuresh.student.Service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    StudentService studentService;

    public void hello(){
        System.out.println("hello i am calling");
    }

    @GetMapping("/sayhey")
    public String sayheyy(){
        System.out.println("hello i am calling");
        return "HEyyyyyyy";
    }

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

    @PostMapping("/uploadDocs")
    public Student uploadDocs(@RequestParam("data") String student, @RequestParam(value = "file", required = false) MultipartFile image) throws IOException {
        System.out.println("in dtaaa");
        Student genericInput = new ObjectMapper().readValue(student, new TypeReference<Student>() {
        });
        return studentService.uploadDocs(genericInput, image);
//        responseEntity = new ResponseEntity<MyInvoice>(invoiceDo, HttpStatus.ACCEPTED);
//        return studentService.uploadDocs(student,image);
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


    @PostMapping("/addData")
    public Student addData(@RequestBody Student student){
        return  studentService.addData(student);

    }

    @GetMapping("/getByCityState")
    public GenericListResponse<Student> findByCityAndState(@RequestParam String city , @RequestParam String state){
        GenericListResponse<Student> response =  new GenericListResponse<>();
        try{
            response.setDataList(studentService.findByCityAndState(city,state));
            response.setStatus("True");
        }catch (Exception e){
            logger.info("getting error while fetching Students with city & state");
            response.setError("not able to find data :"+ e.getMessage());
            response.setStatus("False");
        }
        return response;
    }

    @GetMapping("/search")
    public GenericListResponse<Student> dynamicSearch(@RequestParam("search") String searchText){
        GenericListResponse<Student> response =  new GenericListResponse<>();
        try{
            response.setDataList(studentService.dynamicSearch(searchText));
            response.setStatus("True");
        }catch (Exception e){
            logger.info("something went wrong, while searching student data in dynamic search");
            response.setError("not able to find data :"+ e.getMessage());
            response.setStatus("False");
        }
        return response;
    }

    @GetMapping("/getBy")
    public GenericListResponse<Student> getByemail(@RequestParam("email") String email){
        GenericListResponse<Student> response =  new GenericListResponse<>();
        try{
            response.setDataList(studentService.getByEmail(email));
            response.setStatus("True");
        }catch (Exception e){
            logger.info("something went wrong, while searching student data in getByEmail!!!!");
            response.setError("not able to find data :"+ e.getMessage());
            response.setStatus("False");
        }
        return response;
    }



    @GetMapping("/call_sony_bravia")
    public void sony(){
        /* Var ->
        - We can declare any datatype with the var keyword.
        - var can be used in a local variable declaration
        - var cannot be used in an instance and global variable declaration.
        -  var cannot be used as a Generic type.
        (type inference is used in var keyword in which it
         automatically detects the datatype of a variable)*/
        var sony = new Sony(12,"mmm","nnn");
        //multi-text line
        String x = """
                Hello...
                my name is Mayuresh,
                from Mumbai.
                """;

        if(x.startsWith("h")){

        }
        else{
            ProblemDetail problemDetail =  ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
