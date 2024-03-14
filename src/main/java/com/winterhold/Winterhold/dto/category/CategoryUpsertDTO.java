package com.winterhold.Winterhold.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryUpsertDTO {
    @NotBlank(message = "Cannot be empty")
    @Size(max = 100, message = "Cannot be more than 100 character")
    private String name;

    @NotNull(message = "Cannot be empty")
    private Integer floor;

    @NotBlank(message = "Cannot be empty")
    @Size(max = 10, message = "Cannot be more than 10 character")
    private String isle;

    @NotBlank(message = "Cannot be empty")
    @Size(max = 10, message = "Cannot be more than 10 character")
    private String bay;
}
