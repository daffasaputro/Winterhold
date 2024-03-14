package com.winterhold.Winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseCategoryBookDTO {
    private CategoryBookHeaderDTO categoryBookHeaderDTO;
    private Page<CategoryBookDTO> categoryBookDTO;
}
