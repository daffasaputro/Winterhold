package com.winterhold.Winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthorDataDTO {
    private Integer id;
    private String fullName;
    private LocalDate birthDate;

    private Long age;
    private LocalDate deceasedDate;

    private String status;
    private String education;

    public AuthorDataDTO(Integer id, String fullName, LocalDate birthDate, LocalDate deceasedDate, String education) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.deceasedDate = deceasedDate;
        this.education = education;
    }
}
