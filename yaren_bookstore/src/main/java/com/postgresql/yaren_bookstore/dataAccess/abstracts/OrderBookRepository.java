package com.postgresql.yaren_bookstore.dataAccess.abstracts;

import java.util.List;

import com.postgresql.yaren_bookstore.entities.concretes.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
    @Query("SELECT ob FROM OrderBook ob WHERE ob.order.id = :orderId")
    List<OrderBook> findOrderBooksByOrderId(@Param("orderId") Long orderId);
}
