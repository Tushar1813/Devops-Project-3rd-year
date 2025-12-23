package com.example.lms.repository;

import com.example.lms.entities.Book;
import com.example.lms.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryLibraryRepository implements LibraryRepository {
    private final Map<String, Book> books = new ConcurrentHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void addBook(Book book) {
        books.put(book.isbn(), book);
    }

    @Override
    public void removeBook(String isbn) {
        books.remove(isbn);
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public List<Book> findAllBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<Book> searchBooks(String query) {
        String lowerQuery = query.toLowerCase();
        return books.values().stream()
                .filter(b -> b.title().toLowerCase().contains(lowerQuery) ||
                        b.author().toLowerCase().contains(lowerQuery) ||
                        b.isbn().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        users.put(user.id(), user);
    }

    @Override
    public Optional<User> findUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }
}
