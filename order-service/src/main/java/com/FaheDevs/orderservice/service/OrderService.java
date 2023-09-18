package com.FaheDevs.orderservice.service;

import com.FaheDevs.orderservice.dto.InventoryResponse;
import com.FaheDevs.orderservice.dto.OrderLineItemsDto;
import com.FaheDevs.orderservice.dto.OrderRequest;
import com.FaheDevs.orderservice.event.OrderPlacedEvent;
import com.FaheDevs.orderservice.model.Order;
import com.FaheDevs.orderservice.model.OrderLineItems;
import com.FaheDevs.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){
        var order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        var orderLineItems =  orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        var skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // call inventory service and place order if product is in stock
        var inventoryResponses =  webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder ->
                        uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); // synchronous communication

        var allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
        }else{
            throw new IllegalArgumentException("Product is not in stock please try again ");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        var orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
