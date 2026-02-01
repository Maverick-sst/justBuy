package com.example.Backend.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.entity.Payment.Payment;




public interface PaymentRepository extends JpaRepository<Payment,UUID> {
    public Payment findByRazorpayOrderId(String razorpayOrderId);
}
