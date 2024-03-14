package com.winterhold.Winterhold.rest;

import com.winterhold.Winterhold.dto.author.AuthorUpsertDTO;
import com.winterhold.Winterhold.dto.author.ResponseAuthorBookDTO;
import com.winterhold.Winterhold.dto.utility.RestMessageResponseDTO;
import com.winterhold.Winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorRestController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "") String authorName,
                                      @RequestParam(defaultValue = "1") Integer page) {
        try {
            var authorDataDTO = authorService.getDataByName(authorName, page);
            return ResponseEntity.status(200).body(authorDataDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/{authorId}")
    public ResponseEntity<Object> getOne(@PathVariable Integer authorId) {
        try {
            var authorUpsertDTO = authorService.getDataById(authorId);
            return ResponseEntity.status(200).body(authorUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody AuthorUpsertDTO authorUpsertDTO,
                                       BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            authorService.upsert(authorUpsertDTO);
            return ResponseEntity.status(200).body(authorUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody AuthorUpsertDTO authorUpsertDTO,
                                      BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(400).body(bindingResult.getAllErrors());
            }

            authorService.upsert(authorUpsertDTO);
            return ResponseEntity.status(200).body(authorUpsertDTO);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @DeleteMapping(path = "/{authorId}")
    public ResponseEntity<Object> delete(@PathVariable Integer authorId) {
        try {
            authorService.delete(authorId);
            var response = new RestMessageResponseDTO(authorId, "Success delete author with id " + authorId);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }

    @GetMapping(path = "/book")
    public ResponseEntity<Object> getBook(@RequestParam Integer authorId,
                                          @RequestParam(defaultValue = "1") Integer page) {
        try {
            var authorBookHeaderDTO = authorService.getAuthorBookHeader(authorId);
            var authorBookDTO = authorService.getAuthorBook(authorId, page);
            var response = new ResponseAuthorBookDTO(authorBookHeaderDTO, authorBookDTO);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body(exception.getMessage());
        }
    }
}
