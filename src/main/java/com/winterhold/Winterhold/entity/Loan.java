package com.winterhold.Winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Loan")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CustomerNumber")
    private String customerNumber;

    @ManyToOne
    @JoinColumn(name = "CustomerNumber", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "BookCode")
    private String bookCode;

    @ManyToOne
    @JoinColumn(name = "BookCode", insertable = false, updatable = false)
    private Book book;

    @Column(name = "LoanDate")
    private LocalDate loanDate;

    @Column(name = "DueDate")
    private LocalDate dueDate;

    @Column(name = "ReturnDate")
    private LocalDate returnDate;

    @Column(name = "Note")
    private String note;
}
