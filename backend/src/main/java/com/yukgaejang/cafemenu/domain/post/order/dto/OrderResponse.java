package com.yukgaejang.cafemenu.domain.post.order.dto;

import com.yukgaejang.cafemenu.domain.post.order.entity.Order;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String email,
        String zipCode,
        String address,
        LocalDateTime orderDate
) {

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getEmail(),
                order.getZipCode(),
                order.getAddress(),
                order.getOrderDate()
        );
    }

}
