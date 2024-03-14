package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.dto.loan.LoanUpsertDTO;
import com.winterhold.Winterhold.dto.loan.ResponseLoanInsertDTO;
import com.winterhold.Winterhold.dto.loan.ResponseLoanUpdateDTO;
import com.winterhold.Winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/loan")
public class LoanRestController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "") String bookTitle,
                                      @RequestParam(defaultValue = "") String customerName,
                                      @RequestParam(defaultValue = "false") Boolean isPassedDueDate,
                                      @RequestParam(defaultValue = "1") Integer page) {
        try {
            var loanDataDTO = loanService.getDataByName(bookTitle,
                    customerName,
                    isPassedDueDate,
                    page);
            return ResponseEntity.status(200).body(loanDataDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/dropdown")
    public ResponseEntity<Object> getInsertData() {
        try {
            var customerDropdownDTO = loanService.getCustomerDropdown();
            var bookDropdownDTO = loanService.getBookDropdown();
            var response = new ResponseLoanInsertDTO(customerDropdownDTO, bookDropdownDTO);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/dropdown/{loanId}")
    public ResponseEntity<Object> getUpdateData(@PathVariable Integer loanId) {
        try {
            var loanDataDTO = loanService.getDataById(loanId);
            var customerDropdownDTO = loanService.getCustomerDropdown();
            var bookDropdownDTO = loanService.getBookDropdown();
            var response = new ResponseLoanUpdateDTO(loanDataDTO, customerDropdownDTO, bookDropdownDTO);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody LoanUpsertDTO loanUpsertDTO,
                                       BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            loanService.upsert(loanUpsertDTO);
            return ResponseEntity.status(200).body(loanUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody LoanUpsertDTO loanUpsertDTO,
                                      BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            loanService.upsert(loanUpsertDTO);
            return ResponseEntity.status(200).body(loanUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/detail/{loanId}")
    public ResponseEntity<Object> getDetail(@PathVariable Integer loanId) {
        try {
            var loanDetailDTO = loanService.getDetail(loanId);
            return ResponseEntity.status(200).body(loanDetailDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }
}
