package com.example.Backend.dto.product;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseV1 {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private UUID sellerId;
    private Instant createdAt;
}
