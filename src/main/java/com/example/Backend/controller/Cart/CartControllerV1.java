package com.example.Backend.controller.Cart;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.cart.AddToCartRequestV1;
import com.example.Backend.entity.UserModel;
import com.example.Backend.entity.CartItem.CartItem;
import com.example.Backend.service.CartService.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartControllerV1 {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @Valid @RequestBody AddToCartRequestV1 request, 
            @AuthenticationPrincipal UserModel user
    ) {
        return ResponseEntity.ok(cartService.addToCart(user.getId(), request.getProductId(), request.getQuantity()));
    }
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@AuthenticationPrincipal UserModel user){
        cartService.clearCart(user.getId());
        return ResponseEntity.ok("Cart cleared successfully!")
    }
}
