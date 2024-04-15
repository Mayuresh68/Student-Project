package com.mayuresh.student.Repository;

import com.mayuresh.student.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {
    //Defined Customer Query
    List<Student> findAllByEmail(String email);

//    @Query(value = "Select * from student where city = :city and state = :state ",nativeQuery = true)
    @Query("SELECT s FROM Student s WHERE s.city = :city AND s.state = :state")
    List<Student> findByCityAndState(String city, String state);



    @Query(value = "Select s from Student s where" +
            " s.name LIKE CONCAT('%', :searchText,'%') OR" +
            " s.city LIKE CONCAT('%', :searchText,'%') OR" +
            " s.state LIKE CONCAT('%', :searchText,'%') OR" +
            " s.email LIKE CONCAT('%', :searchText,'%')")
    List<Student> dynamicSearch(String searchText);

    //By Query
//    @Query("SELECT u FROM User u WHERE u.email = mayuresh@gmail.com")
//    Student findByEmai(String email);
}
