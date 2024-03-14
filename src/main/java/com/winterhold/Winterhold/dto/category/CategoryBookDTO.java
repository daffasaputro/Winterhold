package com.winterhold.Winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryBookDTO {
    private String code;
    private String title;
    private String author;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
    private String summary;
}
