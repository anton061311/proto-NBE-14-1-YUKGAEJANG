package com.yukgaejang.cafemenu.domain.post.order.dto;

import com.yukgaejang.cafemenu.domain.post.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String email,
        String zipCode,
        String address,
        LocalDateTime orderDate,
        List<OrderItemResponse> items
) {
    public record OrderItemResponse(Long productId, String productName, Integer quantity, Integer price) {}

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getEmail(),
                order.getZipCode(),
                order.getAddress(),
                order.getOrderDate(),
                order.getOrderItems().stream()
                        .map(i -> new OrderItemResponse(
                                i.getProduct().getId(), i.getProduct().getName(), i.getQuantity(),i.getProduct().getPrice()))
                        .toList()
        );
    }
}