package com.yukgaejang.cafemenu.domain.post.product.controller;

import com.yukgaejang.cafemenu.domain.post.product.dto.ProductCreateRequest;
import com.yukgaejang.cafemenu.domain.post.product.dto.ProductResponse;
import com.yukgaejang.cafemenu.domain.post.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    //상품 등록
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        ProductResponse response = productService.create(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    //상품 수정(update)
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductCreateRequest request
    ) {
        ProductResponse response = productService.updateProduct(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}