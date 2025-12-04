package com.kamrudin.bookmanagement.controller;

import com.kamrudin.bookmanagement.entity.Book;
import com.kamrudin.bookmanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // CREATE (ADMIN only)
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book saved = bookService.createBook(book);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // READ ALL with pagination (USER or ADMIN)
    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> result = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(result);
    }

    // READ BY ID (USER or ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // UPDATE (ADMIN only)
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id,
                                           @Valid @RequestBody Book bookDetails) {
        Book updated = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updated);
    }

    // DELETE (ADMIN only)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH BY TITLE (USER or ADMIN)
    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }

    // SEARCH BY AUTHOR (USER or ADMIN)
    @GetMapping("/search/author")
    public ResponseEntity<List<Book>> searchByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.searchByAuthor(author));
    }
}
