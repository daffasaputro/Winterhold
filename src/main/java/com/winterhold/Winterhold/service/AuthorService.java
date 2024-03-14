package com.winterhold.Winterhold.service;

import com.winterhold.Winterhold.dto.author.AuthorBookDTO;
import com.winterhold.Winterhold.dto.author.AuthorBookHeaderDTO;
import com.winterhold.Winterhold.dto.author.AuthorDataDTO;
import com.winterhold.Winterhold.dto.author.AuthorUpsertDTO;
import com.winterhold.Winterhold.entity.Author;
import com.winterhold.Winterhold.repository.AuthorRepository;
import com.winterhold.Winterhold.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Long calculateAge(LocalDate birthDate, LocalDate deceasedDate) {
        Long age;

        if (deceasedDate == null) {
            age = ChronoUnit.DAYS.between(birthDate, LocalDate.now()) / 365;
            return age;
        } else {
            age = ChronoUnit.DAYS.between(birthDate, deceasedDate) / 365;
            return age;
        }
    }

    public String setStatus(LocalDate deceasedDate) {
        if (deceasedDate == null) {
            return "Alive";
        } else {
            return "Deceased";
        }
    }

    public Page<AuthorDataDTO> getDataByName(String authorName, Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var authorDataDTO = authorRepository.findByName(authorName, pageable);

        for (AuthorDataDTO author : authorDataDTO) {
            author.setAge(calculateAge(author.getBirthDate(), author.getDeceasedDate()));
            author.setStatus(setStatus(author.getDeceasedDate()));
        }

        return authorDataDTO;
    }

    public AuthorUpsertDTO getDataById(Integer authorId) {
        var author = authorRepository.findById(authorId).get();
        var authorUpsertDTO = new AuthorUpsertDTO();
        authorUpsertDTO.setId(author.getId());
        authorUpsertDTO.setTitle(author.getTitle());
        authorUpsertDTO.setFirstName(author.getFirstName());
        authorUpsertDTO.setLastName(author.getLastName());
        authorUpsertDTO.setBirthDate(author.getBirthDate());
        authorUpsertDTO.setDeceasedDate(author.getDeceasedDate());
        authorUpsertDTO.setEducation(author.getEducation());
        authorUpsertDTO.setSummary(author.getSummary());
        return authorUpsertDTO;
    }

    public void upsert(AuthorUpsertDTO authorUpsertDTO) {
        var author = new Author();
        author.setId(authorUpsertDTO.getId());
        author.setTitle(authorUpsertDTO.getTitle());
        author.setFirstName(authorUpsertDTO.getFirstName());
        author.setLastName(authorUpsertDTO.getLastName());
        author.setBirthDate(authorUpsertDTO.getBirthDate());
        author.setDeceasedDate(authorUpsertDTO.getDeceasedDate());
        author.setEducation(authorUpsertDTO.getEducation());
        author.setSummary(authorUpsertDTO.getSummary());
        authorRepository.save(author);
    }

    public void delete(Integer authorId) {
        authorRepository.deleteById(authorId);
    }

    public AuthorBookHeaderDTO getAuthorBookHeader(Integer authorId) {
        var author = authorRepository.findById(authorId).get();
        var authorBookHeaderDTO = new AuthorBookHeaderDTO();
        var authorFullName = author.getTitle() + " " + author.getFirstName() + " " + author.getLastName();
        authorBookHeaderDTO.setAuthorId(author.getId());
        authorBookHeaderDTO.setFullName(authorFullName);
        authorBookHeaderDTO.setBirthDate(author.getBirthDate());
        authorBookHeaderDTO.setDeceasedDate(author.getDeceasedDate());
        authorBookHeaderDTO.setEducation(author.getEducation());
        authorBookHeaderDTO.setSummary(author.getSummary());
        return authorBookHeaderDTO;
    }

    public Page<AuthorBookDTO> getAuthorBook(Integer authorId, Integer page) {
        var pageable = PageRequest.of(page - 1, 10);
        var authorBookDTO = bookRepository.findByAuthor(authorId, pageable);
        return authorBookDTO;
    }
}
