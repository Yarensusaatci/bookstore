package com.postgresql.yaren_bookstore.entities.concretes;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="books")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @Column(name="isbn", unique = true)
    private String isbn; // unique
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="price")
    private Double price;
    @Column(name="stockQuantity")
    private Integer stockQuantity;
    @Column(name="createdAt")
    private Date createdAt;
    @Column(name="updatedAt")
    private Date updatedAt;

    @PrePersist // add
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate // update
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}

// Book --> isbn, author ...