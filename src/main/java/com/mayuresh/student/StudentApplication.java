package com.mayuresh.student;

import com.mayuresh.student.Controller.StudentController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentApplication {

	public static void main(String[] args) {

		var context = SpringApplication.run(StudentApplication.class, args);
		StudentController studentController = context.getBean(StudentController.class);
		studentController.hello();
	}

}
