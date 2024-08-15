package com.example.demo.controller;

import com.example.demo.entities.BorrowingRecord;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PatronRepository;
import com.example.demo.service.BorrowingService;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class BorrowingController {
    private final BorrowingService borrowingService;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public BorrowingController(BorrowingService borrowingRecordService, BookRepository bookRepository, PatronRepository patronRepository) {
        this.borrowingService = borrowingRecordService;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }
    
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public BorrowingRecord updateBorrowingRecord(@PathVariable Long bookId,@PathVariable Long patronId){
        return this.borrowingService.updateBorrowingRecord(this.bookRepository.findById(bookId).get()
                                                            .getBorrowingRecords()
                                                            .get(this.bookRepository
                                                                    .findById(bookId)
                                                                    .get()
                                                                    .getBorrowingRecords()
                                                                    .size()-1
                                                            ));
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public BorrowingRecord addBorrowingRecord(@PathVariable Long bookId, @PathVariable Long patronId){
        return this.borrowingService.addBorrowingRecord(this.bookRepository.findById(bookId).get(), this.patronRepository.findById(patronId).get());
    }
}
