package com.postgresql.yaren_bookstore.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oredata.bookStore.entities.concretes.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
    Users findByEmail(String email);
}