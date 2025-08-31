package com.springauth.domain.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springauth.domain.products.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}