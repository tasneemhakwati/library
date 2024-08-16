package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return this.bookRepository.findAll();
    }

    public Optional<Book> getBookByID(Long id){
        return this.bookRepository.findById(id);
    }

    @Transactional
    public Book addBook(Book book){
        return this.bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book){
        Book p = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        p.setAuthor(book.getAuthor());
        p.setTitle(book.getTitle());
        p.setBorrowingRecords(book.getBorrowingRecords());
        this.bookRepository.save(p);
        return p;
    }

    @Transactional
    public void deleteBook(Long id){
        this.bookRepository.deleteById(id);
    }

}
