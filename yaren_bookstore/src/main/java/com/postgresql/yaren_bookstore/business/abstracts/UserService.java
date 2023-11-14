package com.postgresql.yaren_bookstore.business.abstracts;

import org.springframework.http.ResponseEntity;

import com.postgresql.yaren_bookstore.business.requests.CreateUserRequest;
import com.postgresql.yaren_bookstore.entities.concretes.Users;

public interface UserService {
    Users getOneUserByEmail(String email);
    ResponseEntity<String> create(CreateUserRequest registerRequest);
}