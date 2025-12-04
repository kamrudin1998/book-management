package com.kamrudin.bookmanagement.service;

import com.kamrudin.bookmanagement.entity.Book;
import com.kamrudin.bookmanagement.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Override
    public Book updateBook(String id, Book bookDetails) {
        Book existing = getBookById(id);
        existing.setTitle(bookDetails.getTitle());
        existing.setAuthor(bookDetails.getAuthor());
        existing.setCategory(bookDetails.getCategory());
        existing.setPrice(bookDetails.getPrice());
        existing.setPublishedYear(bookDetails.getPublishedYear());
        existing.setStock(bookDetails.getStock());
        return bookRepository.save(existing);
    }

    @Override
    public void deleteBook(String id) {
        Book existing = getBookById(id);
        bookRepository.delete(existing);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}
