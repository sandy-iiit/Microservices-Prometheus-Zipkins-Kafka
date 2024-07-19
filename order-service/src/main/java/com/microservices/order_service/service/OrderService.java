package com.microservices.order_service.service;

import com.microservices.order_service.client.InventoryClient;
import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.event.OrderPlacedEvent;
import com.microservices.order_service.model.Order;
import com.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    public void placeOrder(OrderRequest orderRequest) {

        var isProductInStock=inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

        if(isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.skuCode());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);

//            Sending the message to the kafka topic

            OrderPlacedEvent orderPlacedEvent=new OrderPlacedEvent();
            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
            orderPlacedEvent.setEmail(orderRequest.userDetails().email());

            log.info("Start-Sending order placed event {} to the kafka topic order_placed",orderPlacedEvent);
            kafkaTemplate.send("order_placed",orderPlacedEvent);
            log.info("End-Sending order placed event {} to the kafka topic order_placed",orderPlacedEvent);

        }
        else{
            throw new RuntimeException("Product with skuCode "+orderRequest.skuCode()+" is not in stock");
        }
    }
}
