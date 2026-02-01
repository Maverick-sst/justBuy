package com.example.Backend.repository.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.entity.Order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,UUID> {
    List<OrderItem> findByOrderId(UUID orderId);
}
