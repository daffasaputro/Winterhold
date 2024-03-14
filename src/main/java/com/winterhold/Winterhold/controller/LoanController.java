package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping(path = "/index")
    public String index(@RequestParam(defaultValue = "") String bookTitle,
                        @RequestParam(defaultValue = "") String customerName,
                        @RequestParam(defaultValue = "false") Boolean isPassedDueDate,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model) {
        var loanDataDTO = loanService.getDataByName(bookTitle, customerName, isPassedDueDate, page);
        var totalPage = loanDataDTO.getTotalPages();
        model.addAttribute("loanDataDTO", loanDataDTO);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("isPassedDueDate", isPassedDueDate);
        model.addAttribute("customerName", customerName);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/loan/loan-index";
    }

    @GetMapping(path = "/returnBook")
    public String returnBook(@RequestParam Integer loanId,
                             @RequestParam String bookCode) {
        loanService.returnBook(loanId, bookCode);
        return "redirect:/loan/index";
    }
}
