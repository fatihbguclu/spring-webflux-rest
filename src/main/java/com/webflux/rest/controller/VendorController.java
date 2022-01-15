package com.webflux.rest.controller;

import com.webflux.rest.model.Category;
import com.webflux.rest.model.Vendor;
import com.webflux.rest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors/";

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public Flux<Vendor> getAllCategories(){
        return vendorRepository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Vendor> getCategoryById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Vendor> updateVendor(@PathVariable String id,@RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }
}
