package com.example.Backend.service.ProductService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.example.Backend.document.Product;
import com.example.Backend.dto.product.ProductRequestV1;
import com.example.Backend.dto.product.ProductResponseV1;
import com.example.Backend.dto.product.ProductSearchCriteriaV1;
import com.example.Backend.repository.mongo.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final MongoTemplate mongoTemplate;
    private final ProductRepository productRepository;


    public void decrementStock(String productId, Integer quantity){
        // fetch from mongodb
        Product product = productRepository.findById(productId)
                                         .orElseThrow(()-> new RuntimeException("Product not found in catalog "+ productId));

        if(product.getStock() < quantity){
            throw new RuntimeException("Insufficient stock for product: "+ product.getName() +
                                       " Requested: " + quantity + ", Available: "+product.getStock());
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
    public Page<ProductResponseV1> searchProductsAdvanced(ProductSearchCriteriaV1 criteria, Pageable pageable){
        Query query = new Query().with(pageable);
        List<Criteria> filters = new ArrayList<>();

        // full text search on product(case insensitive)
        if(criteria.getName() != null && !criteria.getName().isEmpty()){
            filters.add(Criteria.where("name").regex(criteria.getName(),"i"));
        }
        // search based on category
        if(criteria.getCategory() != null && !criteria.getCategory().isEmpty()){
            filters.add(Criteria.where("category").is(criteria.getCategory()));
        }

        // dynamic price range from min to max using gte and lte
        if(criteria.getMinPrice() != null || criteria.getMaxPrice() !=null){
            Criteria priceCriteria = Criteria.where("price");
            if(criteria.getMinPrice() != null) priceCriteria = priceCriteria.gte(criteria.getMinPrice());
            if(criteria.getMaxPrice()!=null) priceCriteria = priceCriteria.lte(criteria.getMaxPrice());
            filters.add(priceCriteria);
        }

        // availability filter
        if(Boolean.TRUE.equals(criteria.getInStock())){
            filters.add(Criteria.where("stock").gt(0));
        }

        // combine all active filters
        if(!filters.isEmpty()){
            query.addCriteria(new Criteria().andOperator(filters.toArray(new Criteria[0])));
        }

        // execute query and count total results for pagination
        List<Product> products = mongoTemplate.find(query, Product.class);
        return PageableExecutionUtils.getPage(
            products.stream().map(this::mapToResponse).toList(),
        pageable,
        ()-> mongoTemplate.count(Query.of(query).limit(-1).skip(-1) ,
        Product.class));
        

    }
    public ProductResponseV1 createProduct(ProductRequestV1 request,UUID sellerId){
        Product product = Product.builder()
                                 .name(request.getName())
                                 .description(request.getDescription())
                                 .price(request.getPrice())
                                 .stock(request.getStock())
                                 .category(request.getCategory())
                                 .sellerId(sellerId)
                                 .createdAt(Instant.now())
                                 .build();
        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);

    }

    public Page<ProductResponseV1> getAllProducts(Pageable pageable){
         return productRepository.findAll(pageable)
                                 .map(this::mapToResponse);

    }

    public ProductResponseV1 mapToResponse(Product product){
        ProductResponseV1 response = ProductResponseV1.builder()
                                                      .id(product.getId())
                                                      .name(product.getName())
                                                      .description(product.getDescription())
                                                      .price(product.getPrice())
                                                      .stock(product.getStock())
                                                      .category(product.getCategory())
                                                      .sellerId(product.getSellerId())
                                                      .createdAt(product.getCreatedAt())
                                                      .build();
        return response;
    }
}
