package com.example.Backend.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequestV1 {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotBlank(message = "Category is required")
    private String category;
}
