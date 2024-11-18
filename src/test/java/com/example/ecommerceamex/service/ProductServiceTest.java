package com.example.ecommerceamex.service;

import com.example.ecommerceamex.model.Product;
import com.example.ecommerceamex.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void testConcurrentStockUpdates() throws InterruptedException {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setStockQuantity(10);
        product.setPrice(new BigDecimal("99.99"));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            product.setStockQuantity(savedProduct.getStockQuantity());
            return savedProduct;
        });

        int numberOfThreads = 20;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicInteger successfulUpdates = new AtomicInteger(0);

        try {
            for (int i = 0; i < numberOfThreads; i++) {
                service.submit(() -> {
                    try {
                        if (productService.updateStock(1L, 1)) {
                            successfulUpdates.incrementAndGet();
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            assertEquals(10, successfulUpdates.get());
            assertEquals(0, product.getStockQuantity());
        } finally {
            service.shutdown();
        }
    }
}