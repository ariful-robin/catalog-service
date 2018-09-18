package com.sputnik.ena.catalogservice.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sputnik.ena.catalogservice.entities.Product;
import com.sputnik.ena.catalogservice.services.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> allProducts(HttpServletRequest request) {
        String auth_header = request.getHeader("AUTH_HEADER");
        return productService.findAllProducts();
    }

}
