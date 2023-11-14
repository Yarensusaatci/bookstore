package com.postgresql.yaren_bookstore.business.abstracts;

import com.postgresql.yaren_bookstore.business.requests.CreateBookRequest;
import com.postgresql.yaren_bookstore.business.responses.GetAllBooksResponse;
import com.postgresql.yaren_bookstore.business.responses.GetByIdBookResponse;
import com.postgresql.yaren_bookstore.business.requests.UpdateBookRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    List<GetAllBooksResponse> getAll(); // response method
    List<GetAllBooksResponse> getAllPagination(int pageNumber);
    GetByIdBookResponse getById(String isbn);

    ResponseEntity<String> add(CreateBookRequest createBookRequest);
    ResponseEntity<String> update(UpdateBookRequest updateBookRequest, String isbn);

    ResponseEntity<String> update(UpdateBookRequest updateBookRequest, String isbn);

    ResponseEntity<String> delete(String isbn);
}
