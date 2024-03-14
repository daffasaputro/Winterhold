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
public class AuthorBookDTO {
    private String title;
    private String categoryName;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
}
