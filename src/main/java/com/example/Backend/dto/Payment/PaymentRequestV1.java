package com.example.Backend.dto.Payment;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequestV1 {
    @NotNull(message = "Order ID is required")
    private UUID orderId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}
