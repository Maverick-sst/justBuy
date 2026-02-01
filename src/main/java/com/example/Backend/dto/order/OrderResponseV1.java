package com.example.Backend.dto.order;

import com.example.Backend.entity.Order.OrderItem;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponseV1 {
    private UUID orderId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItem> items;
}