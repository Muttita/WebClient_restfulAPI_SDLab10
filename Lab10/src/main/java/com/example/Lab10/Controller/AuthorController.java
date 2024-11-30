package com.example.Lab10.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Lab10.Model.Author;
import com.example.Lab10.Service.WebClientService;

@Controller
public class AuthorController {

    private final WebClientService webClientService;

    @Autowired
    public AuthorController(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/web/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", webClientService.getAllAuthors().collectList().block());
        return "authorListCRUD";
    }

    @GetMapping("/web/authors/{id}")
    public String getViewAuthorById(@PathVariable Long id, Model model) {
        model.addAttribute("author", webClientService.getAuthorById(id).block());
        return "authorDetail";
    }

    @GetMapping("/web/createauthor")
    public String createAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "addAuthorForm";
    }

    @PostMapping("/web/addAuthor")
    public String addAuthor(@ModelAttribute Author authorRequest, Model model) {
        webClientService.createAuthor(authorRequest).doOnError(error -> model.addAttribute("error", error.getMessage())).block();
        return "redirect:/web/authors";
    }

    @GetMapping("/web/editauthor/{id}")
    public String editAuthor(@PathVariable Long id, Model model) {
        model.addAttribute("author", webClientService.getAuthorById(id).block());
        return "editAuthorForm";
    }

    @PostMapping("/web/updateauthor/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute Author authorRequest, Model model) {
        webClientService.updateAuthor(id, authorRequest).doOnError(error -> model.addAttribute("error", error.getMessage())).block();
        return "redirect:/web/authors";
    }

    @GetMapping("/web/deleteauthor/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        webClientService.deleteAuthor(id)
            .doOnError(error -> System.err.println("Error: " + error.getMessage()))
            .doOnSuccess(v -> System.out.println("Author deleted successfully"))
            .block();
        return "redirect:/web/authors";
    }
}