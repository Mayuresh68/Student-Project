package com.mayuresh.student.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "salaries")
public class Salaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal amount;

    @OneToOne
    @JoinColumn
    Employee employee;
}
