package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.customer.CustomerDataDTO;
import com.winterhold.Winterhold.dto.customer.CustomerDetailDTO;
import com.winterhold.Winterhold.dto.customer.CustomerUpsertDTO;
import com.winterhold.Winterhold.entity.Customer;
import com.winterhold.Winterhold.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Page<CustomerDataDTO> getDataByName(String customerMembershipNumber,
                                               String customerName,
                                               Boolean isExpired,
                                               Integer page) {
        var pageable = PageRequest.of(page - 1, 10);

        if (!isExpired) {
            return customerRepository.findByName(customerMembershipNumber,
                    customerName,
                    pageable);
        } else {
            return customerRepository.findExpiredCustomer(customerMembershipNumber,
                    customerName,
                    pageable);
        }
    }

    public CustomerUpsertDTO getDataById(String customerMembershipNumber) {
        var customer = customerRepository.findById(customerMembershipNumber).get();
        var customerUpsertDTO = new CustomerUpsertDTO();
        customerUpsertDTO.setMembershipNumber(customer.getMembershipNumber());
        customerUpsertDTO.setFirstName(customer.getFirstName());
        customerUpsertDTO.setLastName(customer.getLastName());
        customerUpsertDTO.setBirthDate(customer.getBirthDate());
        customerUpsertDTO.setGender(customer.getGender());
        customerUpsertDTO.setPhone(customer.getPhone());
        customerUpsertDTO.setAddress(customer.getAddress());
        customerUpsertDTO.setMembershipExpireDate(customer.getMembershipExpireDate());
        return customerUpsertDTO;
    }

    public LocalDate setExpireDate() {
        var expireDate = LocalDate.now().plusYears(2);
        return expireDate;
    }

    public void upsert(CustomerUpsertDTO customerUpsertDTO) {
        var customer = new Customer();
        customer.setMembershipNumber(customerUpsertDTO.getMembershipNumber());
        customer.setFirstName(customerUpsertDTO.getFirstName());
        customer.setLastName(customerUpsertDTO.getLastName());
        customer.setBirthDate(customerUpsertDTO.getBirthDate());
        customer.setGender(customerUpsertDTO.getGender());
        customer.setPhone(customerUpsertDTO.getPhone());
        customer.setAddress(customerUpsertDTO.getAddress());

        if (customerUpsertDTO.getMembershipExpireDate() == null) {
            customer.setMembershipExpireDate(setExpireDate());
        } else {
            customer.setMembershipExpireDate(customerUpsertDTO.getMembershipExpireDate());
        }

        customerRepository.save(customer);
    }

    public void delete(String customerMembershipNumber) {
        customerRepository.deleteById(customerMembershipNumber);
    }

    public LocalDate extendExpireDate(LocalDate expireDate) {
        LocalDate newExpireDate;

        if (expireDate.isBefore(LocalDate.now())) {
            newExpireDate = LocalDate.now().plusYears(2);
        } else {
            newExpireDate = expireDate.plusYears(2);
        }

        return newExpireDate;
    }

    public void extend(String customerMembershipNumber) {
        var customer = customerRepository.findById(customerMembershipNumber).get();
        customer.setMembershipExpireDate(extendExpireDate(customer.getMembershipExpireDate()));
        customerRepository.save(customer);
    }

    public CustomerDetailDTO getDetailById(String customerMembershipNumber) {
        var customerDetailDTO = customerRepository.detail(customerMembershipNumber);
        return customerDetailDTO;
    }
}

// INSERT INTO Customer(MembershipNumber, FirstName, LastName, BirthDate, Gender, Phone, Address, MembershipExpireDate)
// VALUES('C-001', 'Kim', 'Jong Un', '1958-12-15', 'Male', '12345', 'North Korea', '2020-12-15'),
//        ('C-002', 'Joe', 'Biden', '1961-02-24', 'Male', '13579', 'USA', '2024-02-24'),
//        ('C-003', 'Boris', 'Johnson', '1963-07-11', 'Male', '54321', 'UK', '2020-07-11'),
//        ('C-004', 'Greta', 'Thunberg', '2000-08-17', 'Female', '97531', 'Sweden', '2023-12-12')
//
// DELETE FROM Customer
