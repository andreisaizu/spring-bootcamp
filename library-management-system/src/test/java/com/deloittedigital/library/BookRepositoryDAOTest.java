package com.deloittedigital.library;

import com.deloittedigital.library.model.domain.Book;
import com.deloittedigital.library.repository.BookRepositoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryDAOTest {

    @Autowired
    private BookRepositoryDAO bookRepositoryDAO;

    private Book book;

    private Book availableBook;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("The Martian");
        book.setAuthor("Andy Weir");
        book.setNrCopies(20);
        book.setPublisher("Crown/Archetype");
        book.setIsbn("0553418025");

        availableBook = new Book();
        availableBook.setTitle("Fahrenheit 451");
        availableBook.setAuthor("Ray Bradbury");
        availableBook.setPublisher("Art");
        availableBook.setNrCopies(5);
        availableBook.setAvailableCopies(4);
        availableBook.setIsbn("9789731249711");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreateBook() {
        Book savedBook = bookRepositoryDAO.create(book);
        assertNotNull(savedBook);
        assertEquals("The Martian", savedBook.getTitle());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateBook() {
        Book savedBook = bookRepositoryDAO.create(book);
        savedBook.setPages(400);

        Book updatedBook = bookRepositoryDAO.update(savedBook);
        assertNotNull(updatedBook);
        assertEquals(400, updatedBook.getPages());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteBook() {
        Book savedBook = bookRepositoryDAO.create(book);

        bookRepositoryDAO.delete(savedBook.getId());

        Book foundBook = bookRepositoryDAO.findById(savedBook.getId());
        assertNull(foundBook);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindBookById() {
        Book savedBook = bookRepositoryDAO.create(book);

        Book foundBook = bookRepositoryDAO.findById(savedBook.getId());
        assertNotNull(foundBook);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindBookByTitle() {
        bookRepositoryDAO.create(book);

        Book foundBook = bookRepositoryDAO.findBookByTitle("Martian");
        assertNotNull(foundBook);
        assertEquals("The Martian", foundBook.getTitle());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindBookByISBN() {
        bookRepositoryDAO.create(book);

        Book foundBook = bookRepositoryDAO.findBookByISBN("0553418025");
        assertNotNull(foundBook);
        assertEquals("0553418025", foundBook.getIsbn());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindAvailableBooks() {
        bookRepositoryDAO.create(book);
        bookRepositoryDAO.create(availableBook);

        List<Book> availableBooks = bookRepositoryDAO.getAvailableBooks();
        assertNotNull(availableBooks);
        assertEquals("Fahrenheit 451", availableBooks.get(0).getTitle());
    }
}
