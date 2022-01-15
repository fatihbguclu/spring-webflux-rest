package com.webflux.rest.controller;

import com.webflux.rest.model.Category;
import com.webflux.rest.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories/";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Flux<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Category> getCategoryById(@PathVariable String id){
        return categoryRepository.findById(id);
    }

}
