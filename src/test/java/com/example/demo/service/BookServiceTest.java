package com.example.demo.service;

import com.example.demo.entities.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.PatronNotFoundException;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    public void addBook(){

        Book book = new Book("title", "author");
        when(bookRepository.save(book)).thenReturn(book) ;

        Book savedBook = bookService.addBook(book);

        assertEquals("title",savedBook.getTitle());
        assertEquals("author",savedBook.getAuthor());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void getBookByID_found(){
        Book book = new Book(1L, "title", "author");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book)) ;

        Optional<Book> foundBook = bookService.getBookByID(1L);

        assertTrue(foundBook.isPresent());
        assertEquals(1L, foundBook.get().getID());
        assertEquals("title", foundBook.get().getTitle());
        assertEquals("author", foundBook.get().getAuthor());

        verify(bookRepository, times(1)).findById(1L);



    }

    @Test
    public void getBookById_notFound(){
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookByID(id);
        });

        verify(bookRepository, times(1)).findById(id);

    }

    @Test
    public void deleteBook(){
        Long id = 1L;

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void updateBook_success(){
        Book oldBook = new Book(1L, "title", "author");
        Book newBook = new Book(1L, "updatedTitle", "updatedAuthor");

        when(bookRepository.findById(oldBook.getID())).thenReturn(Optional.of(oldBook));
        when(bookRepository.save(oldBook)).thenReturn(newBook);

        Book updatedBook = bookService.updateBook(oldBook.getID(), newBook);

        assertNotNull(updatedBook);
        assertEquals(newBook.getAuthor(), updatedBook.getAuthor());
        assertEquals(newBook.getTitle(), updatedBook.getTitle());

        verify(bookRepository, times(1)).save(oldBook);
        verify(bookRepository, times(1)).findById(oldBook.getID());
    }

    @Test
    public void updateBook_failed(){
        Book book = new Book(1L, "title", "author");

        when(bookRepository.findById(book.getID())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook(book.getID(), book);
        });

        verify(bookRepository, times(1)).findById(book.getID());
        verify(bookRepository, times(0)).save(book);


    }
}
