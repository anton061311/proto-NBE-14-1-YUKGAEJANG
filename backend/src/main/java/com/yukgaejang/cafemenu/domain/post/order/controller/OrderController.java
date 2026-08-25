package com.yukgaejang.cafemenu.domain.post.order.controller;

import com.yukgaejang.cafemenu.domain.post.order.dto.OrderResponse;
import com.yukgaejang.cafemenu.domain.post.order.entity.Order;
import com.yukgaejang.cafemenu.domain.post.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<OrderResponse>> list(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Order> paging = orderService.getList(page);

        List<OrderResponse> responses = paging.getContent()
                .stream()
                .map(OrderResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
