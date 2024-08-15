package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
public class Patron {

    @Column(name = "patron_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;

    @Column
    @NotNull(message = "Your name is required")
    String name;

    @Column
    @NotNull(message = "Your Email is required")
    @Email(message = "Email should be valid")
    String contactInfo;

    @OneToMany(mappedBy = "patron",fetch = FetchType.EAGER)
    @JsonManagedReference("patron-record")
    Set<BorrowingRecord> borrowingRecords;

    public Patron(Long ID, String name, String contactInfo, Set<BorrowingRecord> borrowingRecords) {
        this.ID = ID;
        this.name = name;
        this.contactInfo = contactInfo;
        this.borrowingRecords = borrowingRecords;
    }

    public Patron() {
    }

    public Patron(String name, String mail) {
        this.name = name ;
        this.contactInfo = mail;
    }

    public Patron(long id, String name, String mail) {
        this.ID = id;
        this.name = name ;
        this.contactInfo = mail;
    }

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Set<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }

    @Override
    public String toString() {
        return "Patron{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", borrowingRecords=" + borrowingRecords +
                '}';
    }

    public void setID(Long id) {

        this.ID = id;
    }
}
