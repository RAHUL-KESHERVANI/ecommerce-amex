package com.example.ecommerceamex.service;

import com.example.ecommerceamex.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product getProduct(Long id);
    Page<Product> getProducts(Pageable pageable);
    Page<Product> getProductsByCategory(String category, Pageable pageable);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    boolean updateStock(Long productId, int quantity);
}