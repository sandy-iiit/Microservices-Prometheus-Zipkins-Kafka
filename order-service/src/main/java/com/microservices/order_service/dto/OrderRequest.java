package com.microservices.order_service.dto;

import io.swagger.v3.oas.annotations.info.Contact;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity,UserDetails userDetails) {

    public record UserDetails(String email, String firstName, String lastName){}
}

