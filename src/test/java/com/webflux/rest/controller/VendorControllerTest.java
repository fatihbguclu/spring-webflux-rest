package com.webflux.rest.controller;

import com.webflux.rest.model.Vendor;
import com.webflux.rest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

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
}