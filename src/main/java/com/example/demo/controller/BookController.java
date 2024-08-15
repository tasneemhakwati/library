package com.example.demo.controller;

import com.example.demo.entities.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(){
        return this.bookService.getBooks();
    }

/*    @GetMapping("/{id}")
    public Optional<Book> getBookByID(@PathVariable Long id){
        return this.bookService.getBookByID(id);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body("Invalid ID format. ID must be a positive number.");
        }

        // Fetch the book by ID (e.g., using a service)
        Book book = bookService.getBookByID(id).orElse(null);

        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        return ResponseEntity.ok(book);
    }
/*    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id,@Valid @RequestBody Book book){
        return this.bookService.updateBook(id, book);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable @Positive Long id,
            @RequestBody @Valid Book book) {

        // Check if the book exists
        if (bookService.getBookByID(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        // Set the ID of the book to be updated
        book.setID(id);

        // Update the book
        Book updatedBook = bookService.updateBook(id, book);

        return ResponseEntity.ok(updatedBook);
    }
//    @PostMapping
//    public Book addBook(@Valid @RequestBody Book book){
//        return this.bookService.addBook(book);
//    }

    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult result) {
        if (result.hasErrors()) {
            // Collect error messages and return as response
            String errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation failed: " + errorMessages);
        }

        // Add the book if validation is successful
         this.bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        this.bookService.deleteBook(id);
    }



}
