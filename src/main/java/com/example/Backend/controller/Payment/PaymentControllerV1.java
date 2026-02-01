package com.example.Backend.controller.Payment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.Payment.PaymentRequestV1;
import com.example.Backend.entity.Payment.Payment;
import com.example.Backend.service.PaymentService.PaymentService;
import com.razorpay.RazorpayException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentControllerV1 {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@Valid @RequestBody PaymentRequestV1 req) throws RazorpayException {
        return ResponseEntity.ok(paymentService.createPayment(req));
    }
    
}
