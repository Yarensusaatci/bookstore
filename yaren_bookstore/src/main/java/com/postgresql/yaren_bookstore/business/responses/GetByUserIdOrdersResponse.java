package com.postgresql.yaren_bookstore.business.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByUserIdOrdersResponse {
    private Long orderId;
    private Double price;
    private String userName;
    private Date orderDate;
    private Date createdAt;
    private Date updatedAt;
}
