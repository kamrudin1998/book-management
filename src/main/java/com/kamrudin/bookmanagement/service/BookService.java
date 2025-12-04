package com.kamrudin.bookmanagement.service;

import com.kamrudin.bookmanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Book createBook(Book book);

    Page<Book> getAllBooks(Pageable pageable);

    Book getBookById(String id);

    Book updateBook(String id, Book bookDetails);

    void deleteBook(String id);

    List<Book> searchByTitle(String title);

    List<Book> searchByAuthor(String author);
}
