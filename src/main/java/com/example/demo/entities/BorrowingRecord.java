package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BorrowingRecord {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;


    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference("Book-record")
    Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id")
    @JsonBackReference("patron-record")
    Patron patron;

    @Column
    LocalDateTime borrowingDate;

    @Column
    LocalDateTime returnDate;

    public BorrowingRecord() {
    }

    public BorrowingRecord(Book book, Patron patron, LocalDateTime borrowingDate) {
        this.book = book;
        this.patron = patron;
        this.borrowingDate = borrowingDate;
    }

    public BorrowingRecord(long id, Book book, Patron patron, LocalDateTime now) {
        this.ID = id;
        this.book = book;
        this.patron = patron;
        this.borrowingDate = now;
    }

    public Long getID() {
        return ID;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDateTime getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDateTime borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowingRecord{" +
                "ID=" + ID +
//                ", book=" + book.getID() +
//                ", patron=" + patron.getID() +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
