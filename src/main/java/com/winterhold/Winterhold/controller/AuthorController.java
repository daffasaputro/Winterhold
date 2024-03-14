package com.winterhold.Winterhold.controller;

import com.winterhold.Winterhold.dto.author.AuthorUpsertDTO;
import com.winterhold.Winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping(path = "/index")
    public String index(@RequestParam(defaultValue = "") String authorName,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model) {
        var authorDataDTO = authorService.getDataByName(authorName, page);
        var totalPage = authorDataDTO.getTotalPages();
        model.addAttribute("authorDataDTO", authorDataDTO);
        model.addAttribute("authorName", authorName);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/author/author-index";
    }

    @GetMapping(path = "/insertForm")
    public String insertForm(Model model) {
        var authorUpsertDTO = new AuthorUpsertDTO();
        model.addAttribute("authorUpsertDTO", authorUpsertDTO);
        return "/author/author-form";
    }

    @GetMapping(path = "/updateForm")
    public String updateForm(@RequestParam Integer authorId,
                             Model model) {
        var authorUpsertDTO = authorService.getDataById(authorId);
        model.addAttribute("authorUpsertDTO", authorUpsertDTO);
        return "/author/author-form";
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("authorUpsertDTO") AuthorUpsertDTO authorUpsertDTO,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "/author/author-form";
        }

        authorService.upsert(authorUpsertDTO);
        return "redirect:/author/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer authorId) {
        authorService.delete(authorId);
        return "redirect:/author/index";
    }

    @GetMapping(path = "book")
    public String book(@RequestParam Integer authorId,
                       @RequestParam(defaultValue = "1") Integer page,
                       Model model) {
        var authorBookHeaderDTO = authorService.getAuthorBookHeader(authorId);
        var authorBookDTO = authorService.getAuthorBook(authorId, page);
        var totalPage = authorBookDTO.getTotalPages();
        model.addAttribute("authorBookHeaderDTO", authorBookHeaderDTO);
        model.addAttribute("authorBookDTO", authorBookDTO);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "/author/author-book";
    }
}
