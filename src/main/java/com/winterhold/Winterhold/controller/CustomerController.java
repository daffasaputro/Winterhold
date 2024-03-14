package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.dto.customer.CustomerUpsertDTO;
import com.winterhold.Winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping(path = "/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/index")
    public String index(@RequestParam(defaultValue = "") String customerMembershipNumber,
                        @RequestParam(defaultValue = "") String customerName,
                        @RequestParam(defaultValue = "false") Boolean isExpired,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model) {
        var customerDataDTO = customerService.getDataByName(customerMembershipNumber,
                customerName,
                isExpired,
                page);
        var totalPage = customerDataDTO.getTotalPages();
        var todayDate = LocalDate.now();
        model.addAttribute("customerDataDTO", customerDataDTO);
        model.addAttribute("customerMembershipNumber", customerMembershipNumber);
        model.addAttribute("customerName", customerName);
        model.addAttribute("isExpired", isExpired);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/customer/customer-index";
    }

    @GetMapping(path = "/insertForm")
    public String insertForm(Model model) {
        var customerUpsertDTO = new CustomerUpsertDTO();
        model.addAttribute("customerUpsertDTO", customerUpsertDTO);
        return "/customer/customer-form";
    }

    @GetMapping(path = "/updateForm")
    public String updateForm(@RequestParam String customerMembershipNumber,
                             Model model) {
        var customerUpsertDTO = customerService.getDataById(customerMembershipNumber);
        model.addAttribute("customerUpsertDTO", customerUpsertDTO);
        return "/customer/customer-form";
    }

    @PostMapping(path = "/upsert")
    public String upsert(@Valid @ModelAttribute("customerUpsertDTO") CustomerUpsertDTO customerUpsertDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/customer/customer-form";
        }

        customerService.upsert(customerUpsertDTO);
        return "redirect:/customer/index";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam String customerMembershipNumber) {
        customerService.delete(customerMembershipNumber);
        return "redirect:/customer/index";
    }

    @GetMapping(path = "/extend")
    public String extend(@RequestParam String customerMembershipNumber) {
        customerService.extend(customerMembershipNumber);
        return "redirect:/customer/index";
    }
}
