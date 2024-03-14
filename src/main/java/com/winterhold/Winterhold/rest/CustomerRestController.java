package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.dto.customer.CustomerUpsertDTO;
import com.winterhold.Winterhold.dto.utility.RestMessageResponseDTO;
import com.winterhold.Winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/customer")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "") String customerMembershipNumber,
                                      @RequestParam(defaultValue = "") String customerName,
                                      @RequestParam(defaultValue = "") Boolean isExpired,
                                      @RequestParam(defaultValue = "1") Integer page) {
        try {
            var customerDataDTO = customerService.getDataByName(customerMembershipNumber,
                    customerName,
                    isExpired,
                    page);
            return ResponseEntity.status(200).body(customerDataDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/{customerMembershipNumber}")
    public ResponseEntity<Object> getOne(@PathVariable String customerMembershipNumber) {
        try {
            var customerUpsertDTO = customerService.getDataById(customerMembershipNumber);
            return ResponseEntity.status(200).body(customerUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody CustomerUpsertDTO customerUpsertDTO,
                                      BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            customerService.upsert(customerUpsertDTO);
            return ResponseEntity.status(200).body(customerUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping(path = "/{customerMembershipNumber}")
    public ResponseEntity<Object> delete(@PathVariable String customerMembershipNumber) {
        try {
            customerService.delete(customerMembershipNumber);
            var response = new RestMessageResponseDTO(customerMembershipNumber,
                    "Success delete customer with id " + customerMembershipNumber);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/detail/{customerMembershipNumber}")
    public ResponseEntity<Object> detail(@PathVariable String customerMembershipNumber) {
        try {
            var customerDetailDTO = customerService.getDetailById(customerMembershipNumber);
            return ResponseEntity.status(200).body(customerDetailDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/extend/{customerMembershipNumber}")
    public ResponseEntity<Object> extend(@PathVariable String customerMembershipNumber) {
        try {
            customerService.extend(customerMembershipNumber);
            var response = new RestMessageResponseDTO(customerMembershipNumber,
                    "Success extend customer with id " + customerMembershipNumber);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }
}
