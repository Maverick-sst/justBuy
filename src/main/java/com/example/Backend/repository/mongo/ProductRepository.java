package com.example.Backend.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend.document.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product,String>{
    // basic crud api's inclusive
} 