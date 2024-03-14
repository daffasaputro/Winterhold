package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.category.*;
import com.winterhold.Winterhold.dto.utility.DropdownDTO;
import com.winterhold.Winterhold.entity.Book;
import com.winterhold.Winterhold.entity.Category;
import com.winterhold.Winterhold.repository.AuthorRepository;
import com.winterhold.Winterhold.repository.BookRepository;
import com.winterhold.Winterhold.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public Integer setTotalBook(String categoryName) {
        var totalBook = categoryRepository.countBookByCategory(categoryName);
        return totalBook;
    }

    public Page<CategoryDataDTO> getDataByName(String categoryName, Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var categoryDataDTO = categoryRepository.findByName(categoryName, pageable);

        for (CategoryDataDTO category : categoryDataDTO) {
            category.setTotalBook(setTotalBook(category.getName()));
        }

        return categoryDataDTO;
    }

    public CategoryUpsertDTO getDataById(String categoryName) {
        var category = categoryRepository.findById(categoryName).get();
        var categoryUpsertDTO = new CategoryUpsertDTO();
        categoryUpsertDTO.setName(category.getName());
        categoryUpsertDTO.setFloor(category.getFloor());
        categoryUpsertDTO.setIsle(category.getIsle());
        categoryUpsertDTO.setBay(category.getBay());
        return categoryUpsertDTO;
    }

    public void upsert(CategoryUpsertDTO categoryUpsertDTO) {
        var category = new Category();
        category.setName(categoryUpsertDTO.getName());
        category.setFloor(categoryUpsertDTO.getFloor());
        category.setIsle(categoryUpsertDTO.getIsle());
        category.setBay(categoryUpsertDTO.getBay());
        categoryRepository.save(category);
    }

    public void delete(String categoryName) {
        categoryRepository.deleteById(categoryName);
    }

    public CategoryBookHeaderDTO getCategoryBookHeader(String categoryName) {
        var category = categoryRepository.findById(categoryName).get();
        var categoryBookHeaderDTO = new CategoryBookHeaderDTO();
        categoryBookHeaderDTO.setName(category.getName());
        return categoryBookHeaderDTO;
    }

    public Page<CategoryBookDTO> getCategoryBook(String categoryName,
                                                 String bookTitle,
                                                 String bookAuthor,
                                                 Boolean isAvailable,
                                                 Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var categoryBookDTO = bookRepository.findByCategory(categoryName,
                bookTitle,
                bookAuthor,
                isAvailable,
                pageable);
        return categoryBookDTO;
    }

    public CategoryBookUpsertDTO getCategoryBookById(String bookCode) {
        var book = bookRepository.findById(bookCode).get();
        var categoryBookUpsertDTO = new CategoryBookUpsertDTO();
        categoryBookUpsertDTO.setCode(book.getCode());
        categoryBookUpsertDTO.setTitle(book.getTitle());
        categoryBookUpsertDTO.setCategoryName(book.getCategoryName());
        categoryBookUpsertDTO.setAuthorId(book.getAuthorId());
        categoryBookUpsertDTO.setReleaseDate(book.getReleaseDate());
        categoryBookUpsertDTO.setTotalPage(book.getTotalPage());
        categoryBookUpsertDTO.setSummary(book.getSummary());
        return categoryBookUpsertDTO;
    }

    public LinkedList<DropdownDTO> getAuthorDropdown() {
        var authorDropdownDTO = authorRepository.dropdown();
        return authorDropdownDTO;
    }

    public void bookUpsert(CategoryBookUpsertDTO categoryBookUpsertDTO) {
        var book = new Book();
        book.setCode(categoryBookUpsertDTO.getCode());
        book.setTitle(categoryBookUpsertDTO.getTitle());
        book.setCategoryName(categoryBookUpsertDTO.getCategoryName());
        book.setAuthorId(categoryBookUpsertDTO.getAuthorId());
        book.setIsBorrowed(false);
        book.setSummary(categoryBookUpsertDTO.getSummary());
        book.setReleaseDate(categoryBookUpsertDTO.getReleaseDate());
        book.setTotalPage(categoryBookUpsertDTO.getTotalPage());
        bookRepository.save(book);
    }

    public void bookDelete(String bookCode) {
        bookRepository.deleteById(bookCode);
    }

    public CategoryBookSummaryDTO getCategoryBookSummary(String bookCode) {
        var book = bookRepository.findById(bookCode).get();
        var categoryBookSummary = new CategoryBookSummaryDTO();
        categoryBookSummary.setCode(book.getCode());
        categoryBookSummary.setSummary(book.getSummary());
        return categoryBookSummary;
    }
}
