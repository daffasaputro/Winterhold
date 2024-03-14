package com.winterhold.Winterhold.dto.author;

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
public class AuthorUpsertDTO {
    private Integer id;

    @NotBlank(message = "Cannot be empty")
    @Size(max = 10, message = "Cannot be more than 10 character")
    private String title;

    @NotBlank(message = "Cannot be empty")
    @Size(max = 50, message = "Cannot be more than 50 character")
    private String firstName;

    @Size(max = 50, message = "Cannot be more than 50 character")
    private String lastName;

    @NotNull(message = "Cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deceasedDate;

    @Size(max = 50, message = "Cannot be more than 50 character")
    private String education;

    @Size(max = 500, message = "Cannot be more than 500 character")
    private String summary;
}
