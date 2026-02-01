package com.example.Backend.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.entity.CartItem.CartItem;
import java.util.List;


public interface CartRepository extends JpaRepository<CartItem,UUID> {
    List<CartItem> findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
