package com.FaheDevs.orderservice.controller;

import com.FaheDevs.orderservice.dto.OrderRequest;
import com.FaheDevs.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallBackMethod")
    // what ever calls made by the placeOrder method the circuit breaker pattern will be applied
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order Placed Successfully";
    }

    // fall back method needs to have the same return type as the place order method
    public String fallBackMethod(OrderRequest request, RuntimeException runtimeException){
        return "Oops ! something went wrong, please order after some time";
    }
}
