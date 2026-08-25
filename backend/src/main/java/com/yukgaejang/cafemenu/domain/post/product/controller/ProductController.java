package com.yukgaejang.cafemenu.domain.post.product.controller;

import com.yukgaejang.cafemenu.domain.post.product.dto.ProductResponse;
import com.yukgaejang.cafemenu.domain.post.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getProducts() {
        List<ProductResponse> products =
                productService.getProducts();

        return products;
    }
}
