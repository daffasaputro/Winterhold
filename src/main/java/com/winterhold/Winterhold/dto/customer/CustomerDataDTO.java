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
public class CustomerDataDTO {
    private String membershipNumber;
    private String fullName;
    private LocalDate membershipExpireDate;
}
