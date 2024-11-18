package com.example.ecommerceamex.service;

import com.example.ecommerceamex.exception.InsufficientStockException;
import com.example.ecommerceamex.model.*;
import com.example.ecommerceamex.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductService productService;

    @Test
    void createOrder_WithInsufficientStock_ShouldThrowException() {
        Cart cart = new Cart();
        User user = new User();
        user.setId(1L);
        cart.setUser(user);

        CartItem item = new CartItem();
        Product product = new Product();
        product.setId(1L);
        item.setProduct(product);
        item.setQuantity(5);
        cart.setItems(Arrays.asList(item));

        when(cartRepository.findByUserId(1L)).thenReturn(cart);
        when(productService.updateStock(1L, 5)).thenReturn(false);

        assertThrows(InsufficientStockException.class, () ->
                orderService.createOrder(1L, Arrays.asList(1L)));
    }
}