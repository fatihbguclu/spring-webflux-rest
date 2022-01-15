package com.webflux.rest.controller;

import com.webflux.rest.model.Category;
import com.webflux.rest.model.Vendor;
import com.webflux.rest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorController vendorController;
    VendorRepository vendorRepository;


    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllCategories() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder().firstname("name").lastname("lastname").build(),
                        Vendor.builder().firstname("name1").lastname("lastname1").build())
                );

        webTestClient.get()
                .uri(VendorController.BASE_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getCategoryById() {
        BDDMockito.given(vendorRepository.findById("id"))
                .willReturn(Mono.just(
                   Vendor.builder().firstname("name").lastname("lastname").build()
                ));

        webTestClient.get()
                .uri(VendorController.BASE_URL + "id")
                .exchange()
                .expectBody(Vendor.class);

    }

    @Test
    void createVendor() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Vendor> savedMono = Mono.just(Vendor.builder().build());

        webTestClient.post()
                .uri(VendorController.BASE_URL)
                .body(savedMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void updateVendor() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Category> savedCategoryMono = Mono.just(Category.builder().build());

        webTestClient.put()
                .uri(VendorController.BASE_URL + "1")
                .body(savedCategoryMono, Vendor.class)
                .exchange()
                .expectStatus().isOk();
    }
}