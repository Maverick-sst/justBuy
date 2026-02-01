package com.example.Backend.dto.Payment;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseV1 {
    private UUID id;
    private String razorpayOrderId;
    private Double amount;
    private String status;
}
