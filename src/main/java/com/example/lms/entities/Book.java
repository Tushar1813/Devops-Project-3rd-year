package com.example.lms.entities;

public record Book(String isbn, String title, String author, String genre, boolean isAvailable) {
    public Book withAvailability(boolean available) {
        return new Book(isbn, title, author, genre, available);
    }
}
