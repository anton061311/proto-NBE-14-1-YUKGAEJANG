package com.yukgaejang.cafemenu.domain.post.product.service;

import com.yukgaejang.cafemenu.domain.post.product.dto.ProductCreateRequest;
import com.yukgaejang.cafemenu.domain.post.product.dto.ProductResponse;
import com.yukgaejang.cafemenu.domain.post.product.entity.Product;
import com.yukgaejang.cafemenu.domain.post.product.repository.ProductRepository;
import com.yukgaejang.cafemenu.global.exceptionHandler.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    //상품 등록용(create)
    public ProductResponse create(ProductCreateRequest request) {
        Product product = new Product(
                request.name(),
                request.price(),
                request.imageUrl()
        );

        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);

    }


    //상품 수정(update)
    public ProductResponse updateProduct(Long id, ProductCreateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND,
                        "PRODUCT_NOT_FOUND",
                        "상품이 존재하지 않습니다."
                ));

        product.update(
                request.name(),
                request.price(),
                request.imageUrl()
        );

        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);

    }

    //상품 목록 조회
    @Transactional(readOnly = true)
    public Page<Product> getProducts(int page, String direction) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id"); //등록한 순서(기본조회)

        if ("asc".equalsIgnoreCase(direction)) {
            sort = Sort.by(Sort.Direction.ASC, "price"); //가격 낮은순
        } else if ("desc".equalsIgnoreCase(direction)) {
            sort = Sort.by(Sort.Direction.DESC, "price"); //가격 높은순
        }

        Pageable pageable = PageRequest.of(page, 10, sort);

        return productRepository.findAll(pageable);
    }

    public void deleteProduct(Long productId) {
        boolean isExistedProduct = productRepository.existsById(productId);

        if (!isExistedProduct) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND,
                    "PRODUCT_NOT_FOUND",
                    "product not found"
            );
        }

        productRepository.deleteById(productId);
    }
}




