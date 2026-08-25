package com.yukgaejang.cafemenu.domain.post.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
        @Size(max = 255)
        @NotBlank(message = "상품명은 필수입니다.")
        String name,

        @Min(0)
        @NotNull(message = "상품 판매 가격은 필수입니다.")
        Integer price,

        @Size(max = 255)
        String imageUrl
) {
}
