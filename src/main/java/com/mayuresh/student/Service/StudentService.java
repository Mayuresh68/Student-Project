package com.mayuresh.student.Service;
import com.mayuresh.student.Models.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface StudentService {

    public Student addStudent(Student student);

    public Student getStudent(int id) throws Exception;

    public List<Student> getAllStudents();

    public Student updateStudent(int id, Student student) throws Exception ;
;
    public String deleteStudent(int id) ;

    List<Student> getAllByEmail(String email);

    public Student uploadDocs(Student student, MultipartFile image) throws IOException;

    Student addData(Student student);

    List<Student> findByCityAndState(String city, String state);

    List<Student> dynamicSearch(String searchText);

    List<Student> getByEmail(String email);
}
