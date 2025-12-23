package com.example.lms.repository;

import com.example.lms.entities.Book;
import com.example.lms.entities.User;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository {
    void addBook(Book book);

    void removeBook(String isbn);

    Optional<Book> findBookByIsbn(String isbn);

    List<Book> findAllBooks();

    List<Book> searchBooks(String query);

    void saveUser(User user);

    Optional<User> findUserById(String id);

    List<User> findAllUsers();
}
