package com.coffzin.service;

import org.springframework.stereotype.Service;

import com.coffzin.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    

}
