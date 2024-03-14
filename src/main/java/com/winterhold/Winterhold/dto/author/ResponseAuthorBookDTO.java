package com.winterhold.Winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseAuthorBookDTO {
    private AuthorBookHeaderDTO authorBookHeaderDTO;
    private Page<AuthorBookDTO> authorBookDTO;
}
