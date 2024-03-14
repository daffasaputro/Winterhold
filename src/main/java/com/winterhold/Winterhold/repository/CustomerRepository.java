package com.winterhold.Winterhold.repository;

import com.winterhold.Winterhold.dto.customer.CustomerDataDTO;
import com.winterhold.Winterhold.dto.customer.CustomerDetailDTO;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("""
            SELECT new com.winterhold.Winterhold.dto.customer.CustomerDataDTO(cus.membershipNumber,
                CONCAT(cus.firstName, ' ', cus.lastName),
                cus.membershipExpireDate)
            FROM Customer AS cus
            WHERE (:customerMembershipNumber IS NULL OR cus.membershipNumber LIKE %:customerMembershipNumber%) AND 
                (:customerName IS NULL OR CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:customerName%)
            """)
    public Page<CustomerDataDTO> findByName(@Param("customerMembershipNumber") String customerMembershipNumber,
                                            @Param("customerName") String customerName,
                                            Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.customer.CustomerDataDTO(cus.membershipNumber,
                CONCAT(cus.firstName, ' ', cus.lastName),
                cus.membershipExpireDate)
            FROM Customer AS cus
            WHERE cus.membershipExpireDate < GETDATE() AND
                (:customerMembershipNumber IS NULL OR cus.membershipNumber LIKE %:customerMembershipNumber%) AND 
                (:customerName IS NULL OR CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:customerName%)
            """)
    public Page<CustomerDataDTO> findExpiredCustomer(@Param("customerMembershipNumber") String customerMembershipNumber,
                                            @Param("customerName") String customerName,
                                            Pageable pageable);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.customer.CustomerDetailDTO(cus.membershipNumber,
                CONCAT(cus.firstName, ' ', cus.lastName),
                cus.birthDate,
                cus.gender,
                cus.phone,
                cus.address)
            FROM Customer AS cus
            WHERE cus.membershipNumber = :customerMembershipNumber
            """)
    public CustomerDetailDTO detail(@Param("customerMembershipNumber") String customerMembershipNumber);

    @Query("""
            SELECT new com.winterhold.Winterhold.dto.utility.DropdownDTO(cus.membershipNumber,
                CONCAT(cus.firstName, ' ', cus.lastName))
            FROM Customer AS cus
            WHERE cus.membershipExpireDate > GETDATE()
            """)
    public LinkedList<DropdownDTO> dropdown();
}
