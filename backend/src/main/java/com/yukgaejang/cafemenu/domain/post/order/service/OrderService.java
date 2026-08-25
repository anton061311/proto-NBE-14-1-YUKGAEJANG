package com.yukgaejang.cafemenu.domain.post.order.service;

import com.yukgaejang.cafemenu.domain.post.order.entity.Order;
import com.yukgaejang.cafemenu.domain.post.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<Order> getList(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.orderRepository.findAll(pageable);
    }
}
