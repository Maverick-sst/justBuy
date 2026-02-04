package com.example.Backend.service.CartService;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Backend.document.Product;
import com.example.Backend.entity.CartItem.CartItem;
import com.example.Backend.repository.jpa.CartRepository;
import com.example.Backend.repository.mongo.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository; // postgres
    private final ProductRepository productRepository; // mongodb

    public CartItem addToCart(UUID userId, String productId, Integer quantity) {
        // cross fetching data from mongodb
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in catalog"));

        // transaction write to postgres cart (entity)
        CartItem item = CartItem.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .priceAtAddition(product.getPrice())
                .build();
        return cartRepository.save(item);

    }

    @Transactional
    public void clearCart(UUID userId) {
        cartRepository.deleteByUserId(userId);
    }

    public List<CartItem> getCart(UUID userId) {
        return cartRepository.findByUserId(userId);
    }

}
