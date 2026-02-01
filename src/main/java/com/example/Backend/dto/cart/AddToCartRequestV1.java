package com.example.Backend.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequestV1 {
    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotNull
    @Min(value=1 , message= "Quantity must be at least 1")
    private Integer quantity;
}
