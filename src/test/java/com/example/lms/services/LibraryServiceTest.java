package com.example.lms.services;

import com.example.lms.repository.InMemoryLibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {
    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService(new InMemoryLibraryRepository());
    }

    @Test
    void testAddBook() {
        libraryService.addBook("123", "Test Book", "Author", "Genre");
        assertTrue(libraryService.searchBooks("Test").size() > 0);
    }

    @Test
    void testBorrowBookSuccess() {
        libraryService.addBook("123", "Test Book", "Author", "Genre");
        libraryService.registerUser("U1", "Alice");

        libraryService.borrowBook("U1", "123");

        var user = libraryService.getUserById("U1").get();
        assertTrue(user.borrowedBookIsbns().contains("123"));
        assertFalse(libraryService.searchBooks("123").get(0).isAvailable());
    }

    @Test
    void testBorrowBookUnavailable() {
        libraryService.addBook("123", "Test Book", "Author", "Genre");
        libraryService.registerUser("U1", "Alice");
        libraryService.registerUser("U2", "Bob");

        libraryService.borrowBook("U1", "123");

        assertThrows(IllegalStateException.class, () -> libraryService.borrowBook("U2", "123"));
    }

    @Test
    void testReturnBook() {
        libraryService.addBook("123", "Test Book", "Author", "Genre");
        libraryService.registerUser("U1", "Alice");
        libraryService.borrowBook("U1", "123");

        libraryService.returnBook("U1", "123");

        var user = libraryService.getUserById("U1").get();
        assertFalse(user.borrowedBookIsbns().contains("123"));
        assertTrue(libraryService.searchBooks("123").get(0).isAvailable());
    }
}
