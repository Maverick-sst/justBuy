package com.example.Backend.dto.product;

import lombok.Data;

@Data
public class ProductSearchCriteriaV1 {
    private String name;
    private String category;
    private Double minPrice;
    private Double maxPrice;
    private Boolean inStock;
}
