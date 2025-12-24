package com.example.lms.cli;

import com.example.lms.entities.Book;
import com.example.lms.entities.User;
import com.example.lms.repository.InMemoryLibraryRepository;
import com.example.lms.services.LibraryService;

import java.util.List;
import java.util.Scanner;

public class MainCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryService libraryService = new LibraryService(new InMemoryLibraryRepository());

    public static void main(String[] args) {
        // Pre-populate some data for demo
        seedData();

        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addBook();
                case "2" -> searchBooks();
                case "3" -> borrowBook();
                case "4" -> returnBook();
                case "5" -> viewUserBorrowals();
                case "6" -> registerUser();
                case "7" -> {
                    TerminalUtils.printColored(TerminalUtils.YELLOW, "Exiting LMS. Goodbye!");
                    System.exit(0);
                }
                default -> TerminalUtils.printError("Invalid option. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        TerminalUtils.printHeader("Library Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Search Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. View User Borrowals");
        System.out.println("6. Register User");
        System.out.println("7. Exit");
        System.out.print(TerminalUtils.CYAN + "Enter option: " + TerminalUtils.RESET);
    }

    private static void addBook() {
        TerminalUtils.printHeader("Add New Book");
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        try {
            libraryService.addBook(isbn, title, author, genre);
            TerminalUtils.printSuccess("Book added successfully.");
        } catch (Exception e) {
            TerminalUtils.printError(e.getMessage());
        }
    }

    private static void searchBooks() {
        TerminalUtils.printHeader("Search Books");
        System.out.print("Enter search query (Title/Author/ISBN): ");
        String query = scanner.nextLine();
        List<Book> results = libraryService.searchBooks(query);

        if (results.isEmpty()) {
            TerminalUtils.printColored(TerminalUtils.YELLOW, "No books found matching '" + query + "'");
        } else {
            results.forEach(b -> System.out.printf("[%s] %s by %s (%s) - %s\n",
                    b.isbn(), b.title(), b.author(), b.genre(),
                    b.isAvailable() ? "AVAILABLE" : "BORROWED"));
        }
    }

    private static void borrowBook() {
        TerminalUtils.printHeader("Borrow Book");
        System.out.print("User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Book ISBN: ");
        String isbn = scanner.nextLine();

        try {
            libraryService.borrowBook(userId, isbn);
            TerminalUtils.printSuccess("Book borrowed successfully.");
        } catch (Exception e) {
            TerminalUtils.printError(e.getMessage());
        }
    }

    private static void returnBook() {
        TerminalUtils.printHeader("Return Book");
        System.out.print("User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Book ISBN: ");
        String isbn = scanner.nextLine();

        try {
            libraryService.returnBook(userId, isbn);
            TerminalUtils.printSuccess("Book returned successfully.");
        } catch (Exception e) {
            TerminalUtils.printError(e.getMessage());
        }
    }

    private static void registerUser() {
        TerminalUtils.printHeader("Register New User");
        System.out.print("User ID: ");
        String userId = scanner.nextLine();
        System.out.print("User Name: ");
        String name = scanner.nextLine();

        try {
            libraryService.registerUser(userId, name);
            TerminalUtils.printSuccess("User registered successfully.");
        } catch (Exception e) {
            TerminalUtils.printError(e.getMessage());
        }
    }

    private static void viewUserBorrowals() {
        TerminalUtils.printHeader("User Borrowals");
        System.out.print("User ID: ");
        String userId = scanner.nextLine();

        libraryService.getUserById(userId).ifPresentOrElse(
                user -> {
                    System.out.println("User: " + user.name());
                    if (user.borrowedBookIsbns().isEmpty()) {
                        System.out.println("No borrowed books.");
                    } else {
                        System.out.println("Borrowed Books (ISBNs): " + user.borrowedBookIsbns());
                    }
                },
                () -> TerminalUtils.printError("User not found."));
    }

    private static void seedData() {
        libraryService.addBook("978-0134685991", "Effective Java", "Joshua Bloch", "Technology");
        libraryService.addBook("978-0132350884", "Clean Code", "Robert C. Martin", "Technology");
        libraryService.registerUser("U001", "Alice");
        libraryService.registerUser("U002", "Bob");
    }
}
