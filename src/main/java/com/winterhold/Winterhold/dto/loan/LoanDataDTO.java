package com.winterhold.Winterhold.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoanDataDTO {
    private Integer id;
    private String bookCode;
    private String bookTitle;
    private String customerName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
