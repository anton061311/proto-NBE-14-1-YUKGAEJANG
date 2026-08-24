package com.yukgaejang.cafemenu.domain.post.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Order {

    /**
     * 주문 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 주문자 이메일
     */
    private String email;

    /**
     * 우편변호
     */
    private String zipCode;

    /**
     * 배송 주소
     */
    private String address;

    /**
     * 주문 일시
     */
    private LocalDateTime orderDate;

    public Order(
            String email,
            String zipCode,
            String address,
            LocalDateTime orderDate
    ) {
        this.email = email;
        this.zipCode = zipCode;
        this.address = address;
        this.orderDate = orderDate;
    }

}
