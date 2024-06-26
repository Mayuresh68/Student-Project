package com.mayuresh.student.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "name is mandatory")
    private String name;

    @NonNull
    @Min(value = 18, message = "Age must be greater than or equal to 0")
    @Max(value = 25, message = "Age must be less than or equal to 120")
    private int age;

    @NonNull
    @Email(message = "Invalid Email")
    private String email;

    @Size(max = 10, message = "Mobile number must be up to 10 digits long")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be numeric and exactly 10 digits long")
    @Column(name = "mob_no")
    private String mobNo;

    private String city;

    private String state;

    private String photoPath;

    private String photoName;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Card> cards = new ArrayList<>();


    //Many To Many Example (owning side)
    @ManyToMany
    @JoinTable(name = "student_teacher",
            joinColumns = @JoinColumn(name = "student_id"),
     inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    List<Teacher> teachers = new ArrayList<>();
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }




    public Student() {
    }

    public Student(int id, String name, int age, String email, String mobNo, String city, String state) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.mobNo = mobNo;
        this.city = city;
        this.state = state;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
