package com.sputnik.ena.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sputnik.ena.catalogservice.entities.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
}
