package bcu.GroupA5.librarysystem.model;

import bcu.GroupA5.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patron class represents a library user.
 * Stores user details, loan history, and manages borrowing, renewing, and returning books.
 * Provides methods for patron data management and book association.
 * 
 * Implemented by Alex Rush
 */
public class Patron {
    
    private int id;
    private String name;
    private String phone;
    private String email; // Added for 50% feature
    
    // Soft Delete Flag
    private boolean isDeleted = false;

    private final List<Book> books = new ArrayList<>();
    private final List<Loan> loanHistory = new ArrayList<>();

    public Patron(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public List<Book> getBooks() { return books; }
    
    public List<Loan> getLoanHistory() { return loanHistory; }

    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        // Logic handled by Command, but Model stores the reference
        this.books.add(book);
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        // Logic handled by Command/Loan
    }

    public void returnBook(Book book) throws LibraryException {
        this.books.remove(book);
    }
    
    public void addBook(Book book) {
        this.books.add(book);
    }
}