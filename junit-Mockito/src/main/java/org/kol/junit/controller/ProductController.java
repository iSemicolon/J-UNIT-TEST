package org.kol.junit.controller;

import jakarta.validation.Valid;
import org.kol.junit.model.Product;
import org.kol.junit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;



    @PostMapping("/create")
    public Product createProductRecord(@Valid @RequestBody  Product productRecord){
        return  productRepository.save(productRecord);
    }

    @GetMapping("/fetch-all")
    public List<Product> getAllBookRecords(){
        return  productRepository.findAll();
    }


}































