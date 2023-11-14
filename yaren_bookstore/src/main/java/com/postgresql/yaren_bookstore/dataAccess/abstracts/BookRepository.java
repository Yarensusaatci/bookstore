package com.postgresql.yaren_bookstore.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresql.yaren_bookstore.entities.concretes.Book;

public interface BookRepository extends JpaRepository<Book, String>{

}
