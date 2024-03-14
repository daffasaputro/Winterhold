package com.winterhold.Winterhold.dto.loan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoanUpsertDTO {
    private Integer id;

    @NotNull(message = "Cannot be empty")
    @Size(max = 20, message = "Cannot be more than 20 character")
    private String customerNumber;

    @NotNull(message = "Cannot be empty")
    @Size(max = 20, message = "Cannot be more than 20 character")
    private String bookCode;

    @NotNull(message = "Cannot be empty")
    private LocalDate loanDate;

    private LocalDate dueDate;

    @Size(max = 500, message = "Cannot be more than 500 character")
    private String note;
}
