package com.mayuresh.student.ServiceImpl;

import com.mayuresh.student.Exception.StudentNotFoundException;
import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Repository.StudentRepository;
import com.mayuresh.student.Service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    StudentRepository studentRepository;

    public Student addStudent(Student student) {
        Student student1 = studentRepository.save(student);
        return  student1;
    }

    public Student getStudent(int id) throws StudentNotFoundException {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        } else {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
    }

    public Student getStudentById(int id) {
        Student st = studentRepository.findById(id).get();
        return st;
    }

    public List<Student> getAllStudents() {
        System.out.println("GET ALL Students");
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Student> students = studentRepository.findAll(sort);
        System.out.println("Students"+" "+students);
        return  students;
    }

    public Student updateStudent(int id, Student student) throws Exception {
        Student student1 = studentRepository.findById(id).get();
        return  student1;
    }

    public String deleteStudent(int id) {
        try{
            Student student = studentRepository.findById(id).get();
            if(student != null){
                studentRepository.deleteById(id);
                return "Student deleted successfully";
            }
        }
        catch (Exception e){
            return "Error while deleting student with id "+ id;
        }
        return null;
    }

    @Override
    public List<Student> getAllByEmail(String email) {
        List<Student>students =  studentRepository.findAllByEmail(email);
        return  students;
    }
}
