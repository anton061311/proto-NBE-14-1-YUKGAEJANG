package com.yukgaejang.cafemenu.domain.post.product.dto;

import com.yukgaejang.cafemenu.domain.post.product.entity.Product;

public record ProductDto(
        String name,
        Integer price,
        String imageUrl
) {

    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

}
