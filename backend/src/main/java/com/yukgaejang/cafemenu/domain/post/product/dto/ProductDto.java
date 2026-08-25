package com.yukgaejang.cafemenu.domain.post.product.dto;

public record ProductDto(
        Long id,
        String name,
        int price,
        String imageUrl
) {

}