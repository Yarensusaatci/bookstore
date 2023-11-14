package com.postgresql.yaren_bookstore.webApi.controllers;


import java.util.Comparator;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.postgresql.yaren_bookstore.business.abstracts.BookService;
import com.postgresql.yaren_bookstore.business.requests.CreateBookRequest;
import com.postgresql.yaren_bookstore.business.requests.UpdateBookRequest;
import com.postgresql.yaren_bookstore.business.responses.GetAllBooksResponse;
import com.postgresql.yaren_bookstore.business.responses.GetByIdBookResponse;

import lombok.AllArgsConstructor;

// GET: /books --> Get all books but DESC (createdAt)
//GET: /books/{isbn} : Retrieve details of a book by ISBN.
// POST: /books --> Add a new book (Admin only)
// DELETE: /books/{isbn} : Delete a book by ISBN ("Admin Only)


@RestController // REST controller
@RequestMapping("/books")
@AllArgsConstructor
public class BooksController {
    private BookService bookService;

    @GetMapping
    public List<GetAllBooksResponse> getAll() {
        System.out.print("GET /books : Retrieve a list of all books ordered by creation date DESC.");
        List<GetAllBooksResponse> books = bookService.getAll();
        books.sort(Comparator.comparing(GetAllBooksResponse::getCreatedAt).reversed());
        return books;
    }

    @GetMapping("/page/{pageNumber}")
    public List<GetAllBooksResponse> getAllPagination(@PathVariable int pageNumber) {
        List<GetAllBooksResponse> books = bookService.getAllPagination(pageNumber);
        return books;
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<GetByIdBookResponse> getById(@PathVariable String isbn, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.print("GET: /books/"+isbn + " : Retrieve details of a book by ISBN.");

        GetByIdBookResponse response = bookService.getById(isbn);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }


        // HATEOS --> Normal bir kullanıcı  GET /books/{isbn} endpointi hakkında bilgi alıyor.
        Link selfLink = linkTo(methodOn(BooksController.class).getById(isbn, userDetails)).withSelfRel();
        response.add(selfLink);

        // HATEOS --> ROLE_ADMIN yetkisinde bir kullanıcı  GET, UPDATE, DELETE endpointleri hakkında bilgi alıyor.
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Link updateLink = linkTo(methodOn(BooksController.class).update(isbn, null)).withRel("update");
            Link deleteLink = linkTo(methodOn(BooksController.class).delete(isbn)).withRel("delete");

            response.add(updateLink, deleteLink);
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED)
    public ResponseEntity<String> add(CreateBookRequest createBookRequest) {
        System.out.print("POST: /books --> Add a new book (Admin only)");
        return this.bookService.add(createBookRequest);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<String> update(@PathVariable String isbn, @RequestBody UpdateBookRequest updateBookRequest) {
        System.out.print("PUT: /books/" + isbn + " : Update details of a book (Admin only)");
        return this.bookService.update(updateBookRequest, isbn);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> delete(@PathVariable String isbn) {
        return this.bookService.delete(isbn);
    }
}

