package com.postgresql.yaren_bookstore.webApi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.postgresql.yaren_bookstore.business.abstracts.OrderService;
import com.postgresql.yaren_bookstore.business.requests.CreateOrderRequest;
import com.postgresql.yaren_bookstore.business.responses.GetByOrderIdOrderDetailsResponse;
import com.postgresql.yaren_bookstore.business.responses.GetByUserIdOrdersResponse;

import lombok.AllArgsConstructor;

// POST /orders : Place a new order for a user with a minimum price of 25$.
// GET /orders/{userId} : Get all orders for a specific user ordered by update date DESC.
// GET /orders/details/{orderId} : Get details of a specific order by its ID with the books under that order.
@RestController // REST controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {
    private OrderService orderService;

    @GetMapping("/{userId}")
    public List<GetByUserIdOrdersResponse> getByUserId(@PathVariable Long userId) {
        return orderService.getByUserId(userId);
    }

    @GetMapping("/details/{orderId}")
    public List<GetByOrderIdOrderDetailsResponse> getByOrderId(@PathVariable Long orderId){
        return orderService.getByOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED)
    public ResponseEntity<String> add(@RequestBody CreateOrderRequest createOrderRequest) {
        return this.orderService.add(createOrderRequest);
    }