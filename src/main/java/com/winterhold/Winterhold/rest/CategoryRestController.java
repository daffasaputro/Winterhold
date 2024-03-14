package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.dto.category.CategoryBookUpsertDTO;
import com.winterhold.Winterhold.dto.category.CategoryUpsertDTO;
import com.winterhold.Winterhold.dto.category.ResponseCategoryBookDTO;
import com.winterhold.Winterhold.dto.utility.RestMessageResponseDTO;
import com.winterhold.Winterhold.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/category")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "") String categoryName,
                                      @RequestParam(defaultValue = "1") Integer page) {
        try {
            var categoryData = categoryService.getDataByName(categoryName, page);
            return ResponseEntity.status(200).body(categoryData);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/{categoryName}")
    public ResponseEntity<Object> getOne(@PathVariable String categoryName) {
        try {
            var categoryUpsertDTO = categoryService.getDataById(categoryName);
            return ResponseEntity.status(200).body(categoryUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody CategoryUpsertDTO categoryUpsertDTO,
                                      BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            categoryService.upsert(categoryUpsertDTO);
            return ResponseEntity.status(200).body(categoryUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping(path = "/{categoryName}")
    public ResponseEntity<Object> delete(@PathVariable String categoryName) {
        try {
            categoryService.delete(categoryName);
            var response = new RestMessageResponseDTO(categoryName,
                    "Success delete author with id " + categoryName);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/book")
    public ResponseEntity<Object> getBook(@RequestParam String categoryName,
                                          @RequestParam(defaultValue = "") String bookTitle,
                                          @RequestParam(defaultValue = "") String bookAuthor,
                                          @RequestParam(defaultValue = "") Boolean isAvailable,
                                          @RequestParam(defaultValue = "1") Integer page) {
        try {
            var categoryBookHeaderDTO = categoryService.getCategoryBookHeader(categoryName);
            var categoryBookDTO = categoryService.getCategoryBook(categoryName,
                    bookTitle,
                    bookAuthor,
                    isAvailable,
                    page);
            var responseCategoryBookDTO = new ResponseCategoryBookDTO(categoryBookHeaderDTO, categoryBookDTO);
            return ResponseEntity.status(200).body(responseCategoryBookDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/book/{bookCode}")
    public ResponseEntity<Object> getBook(@PathVariable String bookCode) {
        try {
            var categoryBookUpsertDTO = categoryService.getCategoryBookById(bookCode);
            return ResponseEntity.status(200).body(categoryBookUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/book/summary/{bookCode}")
    public ResponseEntity<Object> getOneBook(@PathVariable String bookCode) {
        try {
            var categoryBookSummaryDTO = categoryService.getCategoryBookSummary(bookCode);
            return ResponseEntity.status(200).body(categoryBookSummaryDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping(path = "/book")
    public ResponseEntity<Object> bookPut(@Valid @RequestBody CategoryBookUpsertDTO categoryBookUpsertDTO,
                                          BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            categoryService.bookUpsert(categoryBookUpsertDTO);
            return ResponseEntity.status(200).body(categoryBookUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping(path = "/book/{bookCode}")
    public ResponseEntity<Object> bookDelete(@PathVariable String bookCode) {
        try {
            categoryService.bookDelete(bookCode);
            var response = new RestMessageResponseDTO(bookCode, "Success delete book with code " + bookCode);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }
}
