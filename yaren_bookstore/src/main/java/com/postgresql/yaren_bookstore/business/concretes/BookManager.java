package com.postgresql.yaren_bookstore.business.concretes;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.postgresql.yaren_bookstore.dataAccess.abstracts.BookRepository;
import com.postgresql.yaren_bookstore.business.abstracts.BookService;
import com.postgresql.yaren_bookstore.business.requests.CreateBookRequest;
import com.postgresql.yaren_bookstore.business.requests.UpdateBookRequest;
import com.postgresql.yaren_bookstore.business.responses.GetAllBooksResponse;
import com.postgresql.yaren_bookstore.business.responses.GetByIdBookResponse;
import com.postgresql.yaren_bookstore.common.utilities.mappers.ModelMapperService;
import com.postgresql.yaren_bookstore.entities.concretes.Book;

import lombok.AllArgsConstructor;

@Service // Business
@AllArgsConstructor
public class BookManager implements BookService{
    private BookRepository bookRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllBooksResponse> getAll() {
        List<Book> books = bookRepository.findAll();

        List<GetAllBooksResponse> booksResponse = books.stream()
                .map(book -> this.modelMapperService.forResponse()
                        .map(book, GetAllBooksResponse.class))
                .collect(Collectors.toList());
        return booksResponse;
    }

    @Override
    public List<GetAllBooksResponse> getAllPagination(int pageNumber) {
        final int pageSize = 3; // page size
        List<Book> books = bookRepository.findAll();

        // updatedAt DESC
        books.sort((book1, book2) -> book2.getUpdatedAt().compareTo(book1.getUpdatedAt()));

        List<GetAllBooksResponse> booksResponse = books.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .map(book -> this.modelMapperService.forResponse()
                        .map(book, GetAllBooksResponse.class))
                .collect(Collectors.toList());

        return booksResponse;
    }


    @Override
    public GetByIdBookResponse getById(String isbn) {
        Book book = this.bookRepository.findById(isbn).orElseThrow();
        // mapping
        GetByIdBookResponse response = this.modelMapperService.forResponse().map(book, GetByIdBookResponse.class);
        return response;
    }

    @Override
    public ResponseEntity<String> add(CreateBookRequest createBookRequest) {
        // control
        System.out.println(bookRepository.findById(createBookRequest.getIsbn()));
        if(bookRepository.findById(createBookRequest.getIsbn()).isPresent()) { // ilgili isbn'ye ait kitap var ise
            return new ResponseEntity<>("The book for the ISBN code you entered already exists.", HttpStatus.BAD_REQUEST);
        }
        // mapping
        Book book = this.modelMapperService.forRequest().map(createBookRequest, Book.class);

        this.bookRepository.save(book);
        return new ResponseEntity<>("Book successfully added.", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> update(UpdateBookRequest updateBookRequest, String isbn) {
        var oldBook = bookRepository.findById(isbn);
        // control
        if(!oldBook.isPresent()) {
            return new ResponseEntity<>("No book with the relevant ISBN code was found.", HttpStatus.BAD_REQUEST);
        }
        // mapping
        Book book = this.modelMapperService.forRequest().map(updateBookRequest, Book.class);
        book.setIsbn(isbn);
        book.setCreatedAt(oldBook.get().getCreatedAt());
        this.bookRepository.save(book);
        return new ResponseEntity<>("Book successfully edited.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(String isbn) {
        // control
        if(!bookRepository.findById(isbn).isPresent()) {
            return new ResponseEntity<>("No book with the relevant ISBN code was found.", HttpStatus.BAD_REQUEST);
        }
        this.bookRepository.deleteById(isbn);
        return new ResponseEntity<>("Book successfully deleted.", HttpStatus.OK);
    }
}