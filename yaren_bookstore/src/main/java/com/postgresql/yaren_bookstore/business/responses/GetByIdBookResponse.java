package com.postgresql.yaren_bookstore.business.responses;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdBookResponse extends RepresentationModel<GetByIdBookResponse> { // using HATEOAS
    private String isbn; // unique
    private String title;
    private String author;
    private Double price;
    private Integer stockQuantity;
    private Date createdAt;
    private Date updatedAt;
}
