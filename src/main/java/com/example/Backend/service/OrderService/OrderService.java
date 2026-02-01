package com.example.Backend.service.OrderService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Backend.entity.CartItem.CartItem;
import com.example.Backend.entity.Order.Order;
import com.example.Backend.entity.Order.OrderItem;
import com.example.Backend.repository.jpa.CartRepository;
import com.example.Backend.repository.jpa.OrderItemRepository;
import com.example.Backend.repository.jpa.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional    // for acid compliance
    public Order createOrder(UUID userId){
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if(cartItems.isEmpty()) throw new RuntimeException("Cart is empty!");
        
        // calculate Total
        Double total=0.0;
        for(CartItem items : cartItems){
            total += items.getPriceAtAddition()*items.getQuantity();
        }
        // create a parent order
        Order order = Order.builder()
                      .userId(userId)
                      .totalAmount(total)
                      .status("CREATED")
                      .build();
        Order savedOrder= orderRepository.save(order);

        // shift items from cartItems to orderItem table (snapshot of our purchase)
        List<OrderItem> orderItems = cartItems.stream()
                                              .map(cart -> OrderItem.builder()
                                                    .orderId(savedOrder.getId())
                                                    .productId(cart.getProductId())
                                                    .quantity(cart.getQuantity())
                                                    .priceAtPurchase(cart.getPriceAtAddition())
                                                    .build()
                                                ).collect(Collectors.toList());
        orderItemRepository.saveAll(orderItems);
        
        // clear cart
        cartRepository.deleteAll(cartItems);
        
        return savedOrder;
    }

}
