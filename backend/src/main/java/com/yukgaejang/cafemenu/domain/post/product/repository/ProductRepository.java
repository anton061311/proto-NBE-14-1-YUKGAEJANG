package com.yukgaejang.cafemenu.domain.post.product.repository;

import com.yukgaejang.cafemenu.domain.post.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}