package com.example.lms.services;

import com.example.lms.entities.Book;
import com.example.lms.entities.User;
import com.example.lms.repository.LibraryRepository;

import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final LibraryRepository repository;

    public LibraryService(LibraryRepository repository) {
        this.repository = repository;
    }

    public void addBook(String isbn, String title, String author, String genre) {
        if (repository.findBookByIsbn(isbn).isPresent()) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " already exists.");
        }
        Book book = new Book(isbn, title, author, genre, true);
        repository.addBook(book);
    }

    public void registerUser(String id, String name) {
        if (repository.findUserById(id).isPresent()) {
            throw new IllegalArgumentException("User with ID " + id + " already exists.");
        }
        User user = new User(id, name);
        repository.saveUser(user);
    }

    public void borrowBook(String userId, String isbn) {
        User user = repository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Book book = repository.findBookByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found."));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        // Update book status
        repository.addBook(book.withAvailability(false));

        // Update user's borrowed list
        user.borrowedBookIsbns().add(isbn);
        repository.saveUser(user);
    }

    public void returnBook(String userId, String isbn) {
        User user = repository.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Book book = repository.findBookByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found."));

        if (!user.borrowedBookIsbns().contains(isbn)) {
            throw new IllegalArgumentException("User did not borrow this book.");
        }

        // Update book status
        repository.addBook(book.withAvailability(true));

        // Update user's borrowed list
        user.borrowedBookIsbns().remove(isbn);
        repository.saveUser(user);
    }

    public List<Book> searchBooks(String query) {
        return repository.searchBooks(query);
    }

    public List<Book> getAllAvailableBooks() {
        return repository.findAllBooks().stream()
                .filter(Book::isAvailable)
                .toList();
    }

    public Optional<User> getUserById(String id) {
        return repository.findUserById(id);
    }
}
