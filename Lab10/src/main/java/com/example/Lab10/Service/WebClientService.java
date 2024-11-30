package com.example.Lab10.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Lab10.Model.Author;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    private final WebClient webClient;

    @Autowired
    public WebClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Author> getAllAuthors() {
        return webClient.get()
                .uri("/authors")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Author.class);
    }

 
    public Mono<Author> getAuthorById(Long id) {
        return webClient.get()
                .uri("/authors/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Author.class);
    }


    public Mono<Author> createAuthor(Author author) {
        return webClient.post()
                .uri("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(author)
                .retrieve()
                .onStatus(
                    status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> Mono.error(new RuntimeException("Error creating author"))
                )
                .bodyToMono(Author.class);
    }

    public Mono<Author> updateAuthor(Long id, Author author) {
        return webClient.put()
                .uri("/authors/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(author)
                .retrieve()
                .bodyToMono(Author.class);
    }

    public Mono<Void> deleteAuthor(Long id) {
        return webClient.delete()
                .uri("/authors/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}