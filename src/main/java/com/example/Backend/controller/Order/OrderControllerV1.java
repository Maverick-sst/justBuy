package com.example.Backend.controller.Order;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.entity.UserModel;
import com.example.Backend.entity.Order.Order;
import com.example.Backend.service.OrderService.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderControllerV1 {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.ok(orderService.createOrder(user.getId()));
    }
}