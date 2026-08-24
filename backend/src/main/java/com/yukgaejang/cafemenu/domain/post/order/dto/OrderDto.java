package com.yukgaejang.cafemenu.domain.post.order.dto;

import com.yukgaejang.cafemenu.domain.post.order.entity.Order;

import java.time.LocalDateTime;

public record OrderDto(
        String email,
        String zipCode,
        String address,
        LocalDateTime orderDate
) {

    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getEmail(),
                order.getZipCode(),
                order.getAddress(),
                order.getOrderDate()
        );
    }

}
