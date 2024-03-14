package com.winterhold.Winterhold.dto.loan;

import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseLoanUpdateDTO {
    private LoanUpsertDTO loanUpdateDTO;
    private LinkedList<DropdownDTO> customerDropdown;
    private LinkedList<DropdownDTO> bookDropdown;
}
