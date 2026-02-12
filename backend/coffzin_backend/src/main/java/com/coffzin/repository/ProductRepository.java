package com.coffzin.repository;

import com.coffzin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface  ProductRepository extends JpaRepository<Product, Long> {

}
