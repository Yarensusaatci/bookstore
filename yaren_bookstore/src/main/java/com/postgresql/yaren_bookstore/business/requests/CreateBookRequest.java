package com.postgresql.yaren_bookstore.business.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {
    private String isbn; // unique
    private String title;
    private String author;
    private Double price;
    private Integer stockQuantity;
    // createdAt and updatedAt auto
}
