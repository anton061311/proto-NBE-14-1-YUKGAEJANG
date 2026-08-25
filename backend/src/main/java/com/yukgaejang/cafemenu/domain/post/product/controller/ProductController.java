package com.yukgaejang.cafemenu.domain.post.product.controller;

import com.yukgaejang.cafemenu.domain.post.product.dto.ProductCreateRequest;
import com.yukgaejang.cafemenu.domain.post.product.dto.ProductResponse;
import com.yukgaejang.cafemenu.domain.post.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;


    //create 상품 등록
    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        return productService.create(request);
    }

    //단건 상품 조회
    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }


    //전체 상품 조회
    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }


    //상품 수정(update)
    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductCreateRequest request
    ) {
        return productService.updateProduct(id, request);
    }
}