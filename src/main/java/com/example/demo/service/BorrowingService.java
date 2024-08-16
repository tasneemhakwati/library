package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.entities.BorrowingRecord;
import com.example.demo.entities.Patron;
import com.example.demo.repository.BorrowingRecordRepository;
import java.time.LocalDateTime;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {

    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingService(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
    }
    

    @Transactional
    //Add Borrowing Record
    //Executed when a patron Borrows a book
    public BorrowingRecord addBorrowingRecord(Book book, Patron patron){
        BorrowingRecord borrowingRecord = new BorrowingRecord(book, patron,LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        this.borrowingRecordRepository.save(borrowingRecord);
        return borrowingRecord;
    }

    @Transactional
    //Update Borrowing Record
    //Executed when a patron returns a book
    public BorrowingRecord updateBorrowingRecord(BorrowingRecord borrowingRecord){
        BorrowingRecord p = this.borrowingRecordRepository.findById(borrowingRecord.getID()).get();
        p.setReturnDate(LocalDateTime.now());
        this.borrowingRecordRepository.save(p);
        return p;
    }
}
