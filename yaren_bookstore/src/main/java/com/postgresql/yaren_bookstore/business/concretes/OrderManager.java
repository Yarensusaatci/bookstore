package com.postgresql.yaren_bookstore.business.concretes;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.postgresql.yaren_bookstore.business.abstracts.OrderService;
import com.postgresql.yaren_bookstore.business.requests.BookOrder;
import com.postgresql.yaren_bookstore.business.requests.CreateOrderRequest;
import com.postgresql.yaren_bookstore.business.responses.GetByOrderIdOrderDetailsResponse;
import com.postgresql.yaren_bookstore.business.responses.GetByUserIdOrdersResponse;
import com.postgresql.yaren_bookstore.common.utilities.mappers.ModelMapperService;
import com.postgresql.yaren_bookstore.dataAccess.abstracts.BookRepository;
import com.postgresql.yaren_bookstore.dataAccess.abstracts.OrderBookRepository;
import com.postgresql.yaren_bookstore.dataAccess.abstracts.OrderRepository;
import com.postgresql.yaren_bookstore.dataAccess.abstracts.UserRepository;
import com.postgresql.yaren_bookstore.entities.concretes.Book;
import com.postgresql.yaren_bookstore.entities.concretes.OrderBook;
import com.postgresql.yaren_bookstore.entities.concretes.Orders;

import lombok.AllArgsConstructor;

@Service // Business
@AllArgsConstructor
public class OrderManager implements OrderService{
    private OrderRepository orderRepository;
    private OrderBookRepository orderBookRepository;
    private BookRepository bookRepository;
    private ModelMapperService modelMapperService;
    private UserRepository userRepository;

    @Override
    public List<GetByUserIdOrdersResponse> getByUserId(Long userId) {
        List<Orders> orders = orderRepository.findOrdersByUserId(userId);

        orders.sort((o1, o2) -> o2.getUpdatedAt().compareTo(o1.getUpdatedAt())); // updatedAt DESC

        List<GetByUserIdOrdersResponse> myResponse = orders.stream()
                .map(book -> this.modelMapperService.forResponse()
                        .map(book, GetByUserIdOrdersResponse.class))
                .collect(Collectors.toList());
        return myResponse;
    }

    @Override
    public List<GetByOrderIdOrderDetailsResponse> getByOrderId(Long orderId) {
        List<OrderBook> orderBooks = orderBookRepository.findOrderBooksByOrderId(orderId);
        List<GetByOrderIdOrderDetailsResponse> myResponse = orderBooks.stream()
                .map(book -> this.modelMapperService.forResponse()
                        .map(book, GetByOrderIdOrderDetailsResponse.class))
                .collect(Collectors.toList());
        return myResponse;
    }

    // add and add helper methods.
    @Override
    public ResponseEntity<String> add(CreateOrderRequest createOrderRequest) {
        double totalAmount = calculateTotalAmount(createOrderRequest.getBooks());
        if(totalAmount < 25) {
            return new ResponseEntity<>("Minimum order amount is 25 TL.", HttpStatus.BAD_REQUEST);
        }
        Orders newOrder = saveNewOrder(createOrderRequest.getUserId(), totalAmount);
        saveOrderBooks(newOrder, createOrderRequest.getBooks());
        return new ResponseEntity<>("Your order has been completed successfully.", HttpStatus.CREATED);
    }

    private double calculateTotalAmount(List<BookOrder> bookOrders) {
        double totalAmount = 0;
        for (BookOrder bookOrder : bookOrders) {
            String isbn = bookOrder.getIsbn();
            @SuppressWarnings("deprecation")
            Book book = bookRepository.getById(isbn);
            var price = book.getPrice();
            var amount = price * bookOrder.getQuantity();
            totalAmount += amount;
        }
        return totalAmount;
    }

    @SuppressWarnings("deprecation")
    private Orders saveNewOrder(Long userId, double totalAmount) {
        Orders newOrder = new Orders();
        newOrder.setUser(userRepository.getById(userId));
        newOrder.setPrice(totalAmount);
        return orderRepository.save(newOrder);
    }

    @SuppressWarnings("deprecation")
    private void saveOrderBooks(Orders order, List<BookOrder> bookOrders) {
        for (BookOrder bookOrder : bookOrders) {
            OrderBook orderBook = new OrderBook();
            orderBook.setOrder(order);
            orderBook.setBook(bookRepository.getById(bookOrder.getIsbn()));
            orderBook.getBook().setStockQuantity(orderBook.getBook().getStockQuantity() - bookOrder.getQuantity()); // stockQuantity - orderedQuantity
            orderBook.setQuantity(bookOrder.getQuantity());
            orderBookRepository.save(orderBook);
        }
    }


}
