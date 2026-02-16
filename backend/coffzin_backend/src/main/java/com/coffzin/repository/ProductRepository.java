package com.coffzin.repository;

import com.coffzin.model.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface  ProductRepository extends JpaRepository<Product, Long> {

    public Optional <Product> findByName (String name);

}
