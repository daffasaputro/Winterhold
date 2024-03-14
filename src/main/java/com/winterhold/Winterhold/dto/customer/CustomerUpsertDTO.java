package com.winterhold.Winterhold.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerUpsertDTO {
    @NotBlank(message = "cannot be empty")
    @Size(max = 20, message = "Cannot be more than 20 character")
    private String membershipNumber;

    @NotBlank(message = "cannot be empty")
    @Size(max = 50, message = "Cannot be more than 50 character")
    private String firstName;

    @Size(max = 50, message = "Cannot be more than 50 character")
    private String lastName;

    @NotNull(message = "cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "cannot be empty")
    private String gender;

    @Size(max = 20, message = "Cannot be more than 20 character")
    private String phone;

    @Size(max = 500, message = "Cannot be more than 500 character")
    private String address;

    private LocalDate membershipExpireDate;
}
