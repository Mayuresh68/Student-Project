package com.mayuresh.student.Service;

import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student addStudent(Student student) {
        Student student1 = studentRepository.save(student);
        return  student1;
    }

    public Student getStudent(int id) {
        Student st = studentRepository.findById(id).get();
        return st;
    }

    public Student getStudentById(int id) {
        Student st = studentRepository.findById(id).get();
        return st;
    }

    public List<Student> getAllStudents() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Student> students = studentRepository.findAll(sort);
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
}
