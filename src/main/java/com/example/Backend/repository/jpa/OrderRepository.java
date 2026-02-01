package com.example.Backend.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Backend.entity.Order.Order;

public interface OrderRepository extends JpaRepository<Order,UUID>{

}
