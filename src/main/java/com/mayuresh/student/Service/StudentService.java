package com.mayuresh.student.Service;
import com.mayuresh.student.Models.Student;
import java.util.List;


public interface StudentService {

    public Student addStudent(Student student);

    public Student getStudent(int id) throws Exception;

    public List<Student> getAllStudents();

    public Student updateStudent(int id, Student student) throws Exception ;
;
    public String deleteStudent(int id) ;

    List<Student> getAllByEmail(String email);
}
