// repository/OrderItemRepository.java
package com.yukgaejang.cafemenu.domain.post.order.repository;

import com.yukgaejang.cafemenu.domain.post.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}