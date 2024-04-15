package com.mayuresh.student.ServiceImpl;

import com.mayuresh.student.Enums.CardType;
import com.mayuresh.student.Exception.StudentNotFoundException;
import com.mayuresh.student.Models.Card;
import com.mayuresh.student.Models.Student;
import com.mayuresh.student.Repository.CardRepository;
import com.mayuresh.student.Repository.StudentRepository;
import com.mayuresh.student.Service.StudentService;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CardRepository cardRepository ;


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

    public Student uploadDocs(Student student, MultipartFile image) throws IOException {
        Student st = studentRepository.save(student);
        uploadImage(image,student);
        return st;
    }

    @Override
    public Student addData(Student student) {
        Student student1 = studentRepository.save(student);

        Card card = new Card();
        card.setCardType( CardType.MASTERCARD);
        card.setCvv(2028);
        card.setCardNo("122");
        card.setStudent(student);
        cardRepository.save(card);




        return student1;

    }



    private void uploadImage(MultipartFile image,Student student) throws IOException {
        try{
            String path = "C:\\Spring Boot Project\\student\\src\\main\\java\\com\\mayuresh\\student\\Docs" + File.separator;
            System.out.println("PAth : "+ path);
            if (image != null) {
                System.out.println("in image function");
                // Create a File object representing the destination where the photo will be saved
                File destinationFile = new File(path + image.getOriginalFilename());
                System.out.println("destination file : ");
                // Transfer the uploaded photo to the destination file
                image.transferTo(destinationFile);
                // Set the photo path in the student object
                student.setPhotoPath(path +  image.getOriginalFilename());
                student.setPhotoName(image.getOriginalFilename());

                // Save the updated student object to the database
                studentRepository.save(student);
            }
        }
        catch(Exception e){
            System.out.println("error in exception is :" + e);
        }

    }


    @Override
    public List<Student> findByCityAndState(String city, String state) {
        return studentRepository.findByCityAndState(city,state);
    }

    @Override
    public List<Student> dynamicSearch(String searchText) {
       return studentRepository.dynamicSearch(searchText);
    }


    /*
    * Predicate & Criteria
    * Get Student BY Email;
    * it will find the student with query of predicate & criteria builder;
    */
    @Override
    public List<Student> getByEmail(String email) {
        return studentRepository.findAll((root,query,criteriaBuilder)->{
            List<Predicate> predicates = new ArrayList<>();
            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }


}
