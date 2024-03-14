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
public class LoanDetailDTO {
    private String title;
    private String category;
    private String author;
    private Integer floor;
    private String isle;
    private String bay;
    private String membershipNumber;
    private String fullName;
    private String phone;
    private LocalDate membershipExpireDate;
}
