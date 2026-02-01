package com.example.Backend.controller.Product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.Backend.dto.product.ProductRequestV1;
import com.example.Backend.dto.product.ProductResponseV1;
import com.example.Backend.dto.product.ProductSearchCriteriaV1;
import com.example.Backend.entity.UserModel;
import com.example.Backend.service.ProductService.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductControllerV1 {
    private final ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponseV1> createProduct(@Valid @RequestBody ProductRequestV1 request, @AuthenticationPrincipal UserModel seller) {
        return ResponseEntity.ok(productService.createProduct(request,seller.getId()));
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponseV1>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        Pageable pageable= PageRequest.of(page, size, Sort.by(sortBy).descending());
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }
    
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductResponseV1>> filterProducts(ProductSearchCriteriaV1 criteria,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam(defaultValue = "price") String sortBy,
         @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")?
                                       Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ResponseEntity.ok(productService.searchProductsAdvanced(criteria, pageable));
    }
    
    
}
