package com.example.ecommerceamex.service.impl;

import com.example.ecommerceamex.model.Product;
import com.example.ecommerceamex.repository.ProductRepository;
import com.example.ecommerceamex.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product getProduct(Long id) {
        return null;
    }

    @Override
    public Page<Product> getProducts(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Product> getProductsByCategory(String category, Pageable pageable) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }



        @Override
        @Transactional
        public synchronized boolean updateStock(Long productId, int quantity) {
            return productRepository.findById(productId)
                    .map(product -> {
                        if (product.getStockQuantity() >= quantity) {
                            product.setStockQuantity(product.getStockQuantity() - quantity);
                            product.setInStock(product.getStockQuantity() > 0);
                            productRepository.save(product);
                            return true;
                        }
                        return false;
                    })
                    .orElse(false);
        }

}