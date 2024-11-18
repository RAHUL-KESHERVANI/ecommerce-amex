package com.example.ecommerceamex.service.impl;

import com.example.ecommerceamex.exception.InsufficientStockException;
import com.example.ecommerceamex.model.Cart;
import com.example.ecommerceamex.model.CartItem;
import com.example.ecommerceamex.model.Order;
import com.example.ecommerceamex.model.OrderItem;
import com.example.ecommerceamex.repository.CartRepository;
import com.example.ecommerceamex.repository.OrderRepository;
import com.example.ecommerceamex.service.OrderService;
import com.example.ecommerceamex.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public Order createOrder(Long userId, List<Long> cartItemIds) {
        Cart cart = cartRepository.findByUserId(userId);
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());

        for (CartItem item : cart.getItems()) {
            if (!productService.updateStock(item.getProduct().getId(), item.getQuantity())) {
                throw new InsufficientStockException("Insufficient stock for product: " + item.getProduct().getId());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());
            order.getItems().add(orderItem);
        }

        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return List.of();
    }

}