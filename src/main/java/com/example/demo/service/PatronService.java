package com.example.demo.service;

import com.example.demo.entities.Patron;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.PatronNotFoundException;
import com.example.demo.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getPatrons(){
        return this.patronRepository.findAll();
    }

    public Optional<Patron> getPatronByID(Long id){
        return this.patronRepository.findById(id);
    }

    @Transactional
    public Patron addPatron(Patron patron){
        return this.patronRepository.save(patron);
    }

    @Transactional
    public Patron updatePatron(Long id, Patron patron){
        Patron p = this.patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException(id));
        p.setName(patron.getName());
        p.setContactInfo(patron.getContactInfo());
        p.setBorrowingRecords(patron.getBorrowingRecords());
        this.patronRepository.save(p);
        return p;
    }

    @Transactional
    public void deletePatron(Long id){
        this.patronRepository.deleteById(id);
    }
}
