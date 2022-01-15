package com.webflux.rest.controller;

import com.webflux.rest.model.Category;
import com.webflux.rest.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() throws Exception{
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAllCategories() {

        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(
                        Category.builder().description("cat1").build(),
                        Category.builder().description("cat2").build())
                );

        webTestClient.get()
                .uri(CategoryController.BASE_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);

    }

    @Test
    void getCategoryById() {
        BDDMockito.given(categoryRepository.findById("id"))
                .willReturn(Mono.just(
                   Category.builder().description("Cat").build()
                ));

        webTestClient.get()
                .uri(CategoryController.BASE_URL + "id")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void createCategory() {

        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> savedMono = Mono.just(Category.builder().description("cat").build());

        webTestClient.post()
                .uri(CategoryController.BASE_URL)
                .body(savedMono, Category.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void updateCategory() {

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> savedCategoryMono = Mono.just(Category.builder().build());

        webTestClient.put()
                .uri(CategoryController.BASE_URL + "1")
                .body(savedCategoryMono, Category.class)
                .exchange()
                .expectStatus().isOk();
    }
}