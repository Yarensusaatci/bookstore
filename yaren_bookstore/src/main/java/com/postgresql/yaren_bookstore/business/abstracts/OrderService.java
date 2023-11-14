package com.postgresql.yaren_bookstore.business.abstracts;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.postgresql.yaren_bookstore.business.requests.CreateOrderRequest;
import com.postgresql.yaren_bookstore.business.responses.GetByOrderIdOrderDetailsResponse;
import com.postgresql.yaren_bookstore.business.responses.GetByUserIdOrdersResponse;

public interface OrderService {
    List<GetByUserIdOrdersResponse> getByUserId(Long userId);
    List<GetByOrderIdOrderDetailsResponse> getByOrderId(Long orderId);
    ResponseEntity<String> add(CreateOrderRequest createOrderRequest);
}