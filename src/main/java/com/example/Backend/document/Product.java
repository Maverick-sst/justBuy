package com.example.Backend.document;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    
    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer stock;
    
    private UUID sellerId;
    private Instant createdAt;
}
