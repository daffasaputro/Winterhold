package com.winterhold.Winterhold.dto.category;

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
public class CategoryBookUpsertDTO {
    @NotBlank(message = "Cannot be empty")
    @Size(max = 20, message = "Cannot be more than 20 character")
    private String code;

    @NotBlank(message = "Cannot be empty")
    @Size(max = 100, message = "Cannot be more than 100 character")
    private String title;

    private String categoryName;

    @NotNull(message = "Cannot be empty")
    private Integer authorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private Integer totalPage;

    private String summary;
}
