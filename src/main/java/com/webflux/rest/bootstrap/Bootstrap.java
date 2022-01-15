package com.webflux.rest.bootstrap;

import com.webflux.rest.model.Category;
import com.webflux.rest.model.Vendor;
import com.webflux.rest.repository.CategoryRepository;
import com.webflux.rest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count().block() == 0 ) {
            System.out.println("#### LOADING Category DATA #####");
            loadCategory();

        }

        if (vendorRepository.count().block() == 0 ) {
            System.out.println("#### LOADING Vendor #####");
            loadVendor();
        }
    }

    private void loadCategory(){


        categoryRepository.save(Category.builder()
                .description("Fruits")
                .build()).block();


        categoryRepository.save(Category.builder()
                .description("Nuts")
                .build()).block();


        categoryRepository.save(Category.builder()
                .description("Breads")
                .build()).block();


        categoryRepository.save(Category.builder()
                .description("Meats")
                .build()).block();


        categoryRepository.save(Category.builder()
                .description("Eggs")
                .build()).block();

        System.out.println("Loaded Categories: " + categoryRepository.count().block());
    }


    private void loadVendor(){
        vendorRepository.save(Vendor.builder()
                .firstname("John")
                .lastname("Doe").build()).block();


        vendorRepository.save(Vendor.builder()
                .firstname("Internet")
                .lastname("Explorer").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstname("Billy")
                .lastname("bill").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstname("Douglas")
                .lastname("Adams").build()).block();

        System.out.println("Loaded Vendors: " + vendorRepository.count().block());
    }
}
