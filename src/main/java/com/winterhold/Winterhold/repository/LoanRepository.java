package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.Winterhold.dto.loan.LoanDataDTO;
import com.winterhold.Winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query("""
            SELECT new com.winterhold.Winterhold.dto.loan.LoanDataDTO(loa.id,
                boo.code,
                boo.title,
                CONCAT(cus.firstName, ' ', cus.lastName),
                loa.loanDate,
                loa.dueDate,
                loa.returnDate)
            FROM Loan AS loa
            JOIN loa.book AS boo
            JOIN loa.customer AS cus
            WHERE (:bookTitle IS NULL OR boo.title LIKE %:bookTitle%) AND 
                (:customerName IS NULL OR CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:customerName%)
            """)
    public Page<LoanDataDTO> findByName(@Param("bookTitle") String bookTitle,
                                        @Param("customerName") String customerName,
                                        Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.loan.LoanDataDTO(loa.id,
                boo.code,
                boo.title,
                CONCAT(cus.firstName, ' ', cus.lastName),
                loa.loanDate,
                loa.dueDate,
                loa.returnDate)
            FROM Loan AS loa
            JOIN loa.book AS boo
            JOIN loa.customer AS cus
            WHERE loa.dueDate < GETDATE() AND
                (:bookTitle IS NULL OR boo.title LIKE %:bookTitle%) AND 
                (:customerName IS NULL OR CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:customerName%)
            """)
    public Page<LoanDataDTO> findPassedDueDateLoan(@Param("bookTitle") String bookTitle,
                                        @Param("customerName") String customerName,
                                        Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.loan.LoanDetailDTO(boo.title,
                cat.name,
                CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
                cat.floor,
                cat.isle,
                cat.bay,
                cus.membershipNumber,
                CONCAT(cus.firstName, ' ', cus.lastName),
                cus.phone,
                cus.membershipExpireDate)
            FROM Loan AS loa
            JOIN loa.book AS boo
            JOIN loa.customer AS cus
            JOIN boo.category AS cat
            JOIN boo.author AS aut
            WHERE loa.id = :id
            """)
    public LoanDetailDTO loanDetail(@Param("id") Integer loanId);
}
