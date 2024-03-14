package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.dto.category.CategoryBookUpsertDTO;
import com.winterhold.Winterhold.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/index")
    public String index(@RequestParam(defaultValue = "") String categoryName,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model) {
        var categoryDataDTO = categoryService.getDataByName(categoryName, page);
        var totalPage = categoryDataDTO.getTotalPages();
        model.addAttribute("categoryDataDTO", categoryDataDTO);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/category/category-index";
    }

    @GetMapping(path = "/delete")
    public String delete(String categoryName) {
        categoryService.delete(categoryName);
        return "redirect:/category/index";
    }

    @GetMapping(path = "/book")
    public String book(@RequestParam String categoryName,
                       @RequestParam(defaultValue = "") String bookTitle,
                       @RequestParam(defaultValue = "") String bookAuthor,
                       @RequestParam(defaultValue = "") Boolean isAvailable,
                       @RequestParam(defaultValue = "1") Integer page,
                       Model model) {
        var categoryBookHeaderDTO = categoryService.getCategoryBookHeader(categoryName);
        var categoryBookDTO = categoryService.getCategoryBook(categoryName,
                bookTitle,
                bookAuthor,
                isAvailable,
                page);
        var totalPage = categoryBookDTO.getTotalPages();
        model.addAttribute("categoryBookHeaderDTO", categoryBookHeaderDTO);
        model.addAttribute("categoryBookDTO", categoryBookDTO);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("bookAuthor", bookAuthor);
        model.addAttribute("isAvailable", isAvailable);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/category/category-book";
    }

    @GetMapping(path = "/book/insertForm")
    public String bookInsertForm(@RequestParam String categoryName,
                                 Model model) {
        var categoryBookUpsertDTO = new CategoryBookUpsertDTO();
        var authorDropdownDTO = categoryService.getAuthorDropdown();
        categoryBookUpsertDTO.setCategoryName(categoryName);
        model.addAttribute("categoryBookUpsertDTO", categoryBookUpsertDTO);
        model.addAttribute("authorDropdownDTO", authorDropdownDTO);
        return "/category/category-book-form";
    }

    @GetMapping(path = "/book/updateForm")
    public String bookUpdateForm(@RequestParam String categoryName,
                                 @RequestParam String bookCode,
                                 Model model) {
        var categoryBookUpsertDTO = categoryService.getCategoryBookById(bookCode);
        var authorDropdownDTO = categoryService.getAuthorDropdown();
        categoryBookUpsertDTO.setCategoryName(categoryName);
        model.addAttribute("categoryBookUpsertDTO", categoryBookUpsertDTO);
        model.addAttribute("authorDropdownDTO", authorDropdownDTO);
        return "/category/category-book-form";
    }

    @PostMapping(path = "/book/upsert")
    public String bookUpsert(@Valid @ModelAttribute("categoryBookUpsertDTO")
                                 CategoryBookUpsertDTO categoryBookUpsertDTO,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            var authorDropdownDTO = categoryService.getAuthorDropdown();
            model.addAttribute("authorDropdownDTO", authorDropdownDTO);
            return "/category/category-book-form";
        }

        categoryService.bookUpsert(categoryBookUpsertDTO);
        var categoryName = categoryBookUpsertDTO.getCategoryName();
        redirectAttributes.addAttribute("categoryName", categoryName);
        return "redirect:/category/book";
    }

    @GetMapping(path = "/book/delete")
    public String bookDelete(@RequestParam String bookCode,
                             @RequestParam String categoryName,
                             RedirectAttributes redirectAttributes) {
        categoryService.bookDelete(bookCode);
        redirectAttributes.addAttribute("categoryName", categoryName);
        return "redirect:/category/book";
    }
}
