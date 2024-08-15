package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
public class Book {

    @Column(name = "book_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;

    @Column
    @NotEmpty(message = "Book title is required")
    String title;

    @Column
    @NotEmpty(message = "Book author is required")
    String author;

    @OneToMany(mappedBy = "book",fetch = FetchType.EAGER)
    @JsonManagedReference("Book-record")

    List<BorrowingRecord> borrowingRecords;

    public Book(Long ID, String title, String author, List<BorrowingRecord> borrowingRecords) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.borrowingRecords = borrowingRecords;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book() {
    }

    public Book(long id, String title, String author) {
        this.ID = id;
        this.title = title;
        this.author = author;

    }

    public Long getID() {
        return ID;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(List<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }

    public Boolean borrowBook(BorrowingRecord borrowingRecord){
        return this.borrowingRecords.add(borrowingRecord);
    }

    public Boolean returnBook(BorrowingRecord borrowingRecord){
        return this.borrowingRecords.add(borrowingRecord);
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrowingRecords=" + borrowingRecords +
                '}';
    }

    public void setID(Long id) {
        this.ID = id;
    }
}
