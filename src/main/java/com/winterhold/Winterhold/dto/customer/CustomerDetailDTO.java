package com.winterhold.Winterhold.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerDetailDTO {
    private String membershipNumber;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;
}
