package com.mayuresh.student.Repository;

import com.mayuresh.student.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    //Defined Customer Query
    List<Student> findAllByEmail(String email);

    //By Query
//    @Query("SELECT u FROM User u WHERE u.email = mayuresh@gmail.com")
//    Student findByEmai(String email);
}
