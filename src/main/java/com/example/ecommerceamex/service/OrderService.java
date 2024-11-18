package com.example.ecommerceamex.service;

import com.example.ecommerceamex.model.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<Long> cartItemIds);
    Order getOrder(Long id);
    List<Order> getUserOrders(Long userId);
}