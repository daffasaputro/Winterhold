package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.Winterhold.dto.loan.LoanDataDTO;
import com.winterhold.Winterhold.dto.loan.LoanUpsertDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Loan;
import com.winterhold.Winterhold.repository.BookRepository;
import com.winterhold.Winterhold.repository.CustomerRepository;
import com.winterhold.Winterhold.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    public Page<LoanDataDTO> getDataByName(String bookTitle,
                                           String customerName,
                                           Boolean isPassedDueDate,
                                           Integer page) {
        var pageable = PageRequest.of(page - 1, 10);

        if (!isPassedDueDate) {
            return loanRepository.findByName(bookTitle, customerName, pageable);
        } else {
            return loanRepository.findPassedDueDateLoan(bookTitle, customerName, pageable);
        }
    }

    public LoanUpsertDTO getDataById(Integer loanId) {
        var loan = loanRepository.findById(loanId).get();
        var loanUpsertDTO = new LoanUpsertDTO();
        loanUpsertDTO.setId(loan.getId());
        loanUpsertDTO.setCustomerNumber(loan.getCustomerNumber());
        loanUpsertDTO.setBookCode(loan.getBookCode());
        loanUpsertDTO.setLoanDate(loan.getLoanDate());
        loanUpsertDTO.setNote(loan.getNote());
        return loanUpsertDTO;
    }

    public LinkedList<DropdownDTO> getCustomerDropdown() {
        var customerDropdownDTO = customerRepository.dropdown();
        return customerDropdownDTO;
    }

    public LinkedList<DropdownDTO> getBookDropdown() {
        var bookDropdownDTO = bookRepository.dropdown();
        return bookDropdownDTO;
    }

    public LocalDate setDueDate(LocalDate loanDate) {
        var dueDate = loanDate.plusDays(5);
        return dueDate;
    }

    public void upsert(LoanUpsertDTO loanUpsertDTO) {
        var loan = new Loan();
        var book = bookRepository.findById(loanUpsertDTO.getBookCode()).get();
        loan.setId(loanUpsertDTO.getId());
        loan.setCustomerNumber(loanUpsertDTO.getCustomerNumber());
        loan.setBookCode(loanUpsertDTO.getBookCode());
        loan.setLoanDate(loanUpsertDTO.getLoanDate());
        loan.setDueDate(setDueDate(loanUpsertDTO.getLoanDate()));
        loan.setNote(loanUpsertDTO.getNote());
        book.setIsBorrowed(true);
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    public void returnBook(Integer loanId, String bookCode) {
        var loan = loanRepository.findById(loanId).get();
        var book = bookRepository.findById(bookCode).get();
        loan.setReturnDate(LocalDate.now());
        book.setIsBorrowed(false);
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    public LoanDetailDTO getDetail(Integer loanId) {
        var loanBookDetailDTO = loanRepository.loanDetail(loanId);
        return loanBookDetailDTO;
    }
}
