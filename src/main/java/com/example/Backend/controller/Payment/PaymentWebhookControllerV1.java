package com.example.Backend.controller.Payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.Payment.PaymentWebhookRequestV1;
import com.example.Backend.service.PaymentService.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class PaymentWebhookControllerV1 {
    private final PaymentService paymentService;

    @PostMapping("/razorpay")
    public ResponseEntity<String> handle(@RequestBody PaymentWebhookRequestV1 req) {
        paymentService.processPaymentWebhook(req);
        return ResponseEntity.ok("Handled");
    }
}
