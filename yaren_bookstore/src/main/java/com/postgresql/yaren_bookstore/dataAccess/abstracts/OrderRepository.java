package com.postgresql.yaren_bookstore.dataAccess.abstracts;

import java.util.List;

import com.postgresql.yaren_bookstore.entities.concretes.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends JpaRepository<Orders, Long>{
    @Query("SELECT o FROM Orders o WHERE o.user.id = :userId")
    List<Orders> findOrdersByUserId(@Param("userId") Long userId);
}