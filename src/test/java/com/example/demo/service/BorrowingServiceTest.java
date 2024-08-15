package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.entities.BorrowingRecord;
import com.example.demo.entities.Patron;
import com.example.demo.repository.BorrowingRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceTest {

    @Mock
    BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    BorrowingService borrowingService;

    @Test
    public void addBorrowingRecord(){
        Book book = new Book("title","author");
        Patron patron = new Patron("name", "name@name.com");
        BorrowingRecord borrowingRecord = new BorrowingRecord( book, patron, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord savedRecord = borrowingService.addBorrowingRecord(book, patron);

        assertEquals(book.getTitle(), savedRecord.getBook().getTitle());
        assertEquals(book.getAuthor(), savedRecord.getBook().getAuthor());
        assertEquals(patron.getName(), savedRecord.getPatron().getName());
        assertEquals(patron.getContactInfo(), savedRecord.getPatron().getContactInfo());

        ArgumentCaptor<BorrowingRecord> captor = ArgumentCaptor.forClass(BorrowingRecord.class);
        verify(borrowingRecordRepository, times(1)).save(captor.capture());

        BorrowingRecord capturedRecord = captor.getValue();
        assertEquals(book.getTitle(), capturedRecord.getBook().getTitle());
        assertEquals(book.getAuthor(), capturedRecord.getBook().getAuthor());
        assertEquals(patron.getName(), capturedRecord.getPatron().getName());
        assertEquals(patron.getContactInfo(), capturedRecord.getPatron().getContactInfo());
    }

    @Test
    public void updateBorrowingRecord(){
        Book book = new Book(1L, "title","author");
        Patron patron = new Patron(1l, "name", "name@name.com");
        BorrowingRecord oldBorrowingRecord = new BorrowingRecord(1L, book, patron, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        BorrowingRecord newBorrowingRecord = new BorrowingRecord(1L, book, patron, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        when(borrowingRecordRepository.findById(oldBorrowingRecord.getID())).thenReturn(Optional.of(oldBorrowingRecord));
        when(borrowingRecordRepository.save(oldBorrowingRecord)).thenReturn(newBorrowingRecord);

        BorrowingRecord updatedBorrowingRecord = borrowingService.updateBorrowingRecord(newBorrowingRecord);

        assertNotNull(updatedBorrowingRecord);
        assertNotNull(updatedBorrowingRecord.getReturnDate());

        verify(borrowingRecordRepository, times(1)).save(oldBorrowingRecord);
        verify(borrowingRecordRepository, times(1)).findById(oldBorrowingRecord.getID());
    }
}
