package com.sputnik.ena.catalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sputnik.ena.catalogservice.entities.Product;
import com.sputnik.ena.catalogservice.repositories.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
   
    }

    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products; 
    }
}
