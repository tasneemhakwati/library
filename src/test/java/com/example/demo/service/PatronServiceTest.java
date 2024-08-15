package com.example.demo.service;

import com.example.demo.entities.Patron;
import com.example.demo.entities.Patron;
import com.example.demo.exception.PatronNotFoundException;
import com.example.demo.exception.PatronNotFoundException;
import com.example.demo.repository.PatronRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatronServiceTest {

    @Mock
    PatronRepository patronRepository;

    @InjectMocks
    PatronService patronService;

    @Test
    public void addPatron(){
        Patron patron =new Patron("name", "name@name.com");

        when(patronRepository.save(patron)).thenReturn(patron);

        Patron savedPatron = patronService.addPatron(patron);

        assertEquals(patron.getName(), savedPatron.getName());
        assertEquals(patron.getContactInfo(), savedPatron.getContactInfo());
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    public void getPatronByID(){
        Patron patron = new Patron(1L, "name", "name@name.com");

        when(patronRepository.findById(patron.getID())).thenReturn(Optional.of(patron));

        Optional<Patron> foundPatron = patronService.getPatronByID(patron.getID());

        assertTrue(foundPatron.isPresent());
        assertEquals(patron.getID(), foundPatron.get().getID());
        assertEquals(patron.getName(), foundPatron.get().getName());
        assertEquals(patron.getContactInfo(), foundPatron.get().getContactInfo());
        verify(patronRepository, times(1)).findById(patron.getID());
    }

    @Test
    public void getPatronByID_notFound() {
        Long id = 1L;
        when(patronRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> {
            patronService.getPatronByID(id);
        });

        verify(patronRepository, times(1)).findById(id);

    }

    @Test
    public void deletePatron(){
        Long id = 1L;

        patronService.deletePatron(id);

        verify(patronRepository, times(1)).deleteById(id);
    }

    @Test
    public void updatePatron_success(){
        Patron oldPatron = new Patron(1L, "name", "name@name.com");
        Patron newPatron = new Patron(1L, "newName", "newEmail@email.com");

        when(patronRepository.findById(oldPatron.getID())).thenReturn(Optional.of(oldPatron));
        when(patronRepository.save(oldPatron)).thenReturn(oldPatron);

        Patron updatedPatron = patronService.updatePatron(oldPatron.getID(), newPatron);

        assertEquals(newPatron.getName(), updatedPatron.getName());
        assertEquals(newPatron.getContactInfo(), updatedPatron.getContactInfo());

        verify(patronRepository, times(1)).findById(oldPatron.getID());
        verify(patronRepository, times(1)).save(oldPatron);

    }

    @Test
    public void updatePatron_failed(){
        Patron patron = new Patron(1L, "name", "name@name.com");

        when(patronRepository.findById(patron.getID())).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> {
            patronService.updatePatron(patron.getID(), patron);
        });

        verify(patronRepository, times(1)).findById(patron.getID());
        verify(patronRepository, times(0)).save(patron);


    }
}
