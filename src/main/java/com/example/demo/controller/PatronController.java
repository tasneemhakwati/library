package com.example.demo.controller;

import com.example.demo.entities.Book;
import com.example.demo.exception.PatronNotFoundException;
import com.example.demo.entities.Patron;
import com.example.demo.service.PatronService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    public List<Patron> getPatrons(){
        return this.patronService.getPatrons();
    }

 /*   @GetMapping("/{id}")
    public Optional<Patron> getPatronByID(@PathVariable Long id){
        return this.patronService.getPatronByID(id);
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatronById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body("Invalid ID format. ID must be a positive number.");
        }

        // Fetch the patron by ID (e.g., using a service)
        Patron patron = patronService.getPatronByID(id).orElse(null);

        if (patron == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron not found.");
        }

        return ResponseEntity.ok(patron);
    }

/*    @PutMapping("/{id}")
    public Patron updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patron){
        return this.patronService.updatePatron(id, patron);
    }*/
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatron(
            @PathVariable @Positive Long id,
            @RequestBody @Valid Patron patron) {

        // Check if the patron exists
        if (patronService.getPatronByID(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron not found.");
        }

        // Set the ID of the patron to be updated
        patron.setID(id);

        // Update the patron
        Patron updatedPatron = patronService.updatePatron(id, patron);

        return ResponseEntity.ok(updatedPatron);
    }

/*
    @PostMapping
    public Patron addPatron(@Valid @RequestBody Patron patron){
        return this.patronService.addPatron(patron);
    }
*/

    @PostMapping
    public ResponseEntity<String> addPatron(@Valid @RequestBody Patron patron, BindingResult result) {
        if (result.hasErrors()) {
            // Collect error messages and return as response
            String errorMessages = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Validation failed: " + errorMessages);
        }

        // Add the patron if validation is successful
        this.patronService.addPatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patron added successfully");
    }

    @DeleteMapping("/{id}")
    public void deletePatron(@PathVariable Long id){
         this.patronService.deletePatron(id);
    }


}

