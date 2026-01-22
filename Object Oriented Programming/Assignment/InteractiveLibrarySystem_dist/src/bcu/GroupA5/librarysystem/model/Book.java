package bcu.GroupA5.librarysystem.model;

import bcu.GroupA5.librarysystem.main.LibraryException;
import java.time.LocalDate;

/**
 * Book class represents a book in the library system.
 * It stores details such as title, author, publication year, publisher, and loan status.
 * Provides methods to manage book information, check loan status, and handle book returns.
 * Implements features for marking books as deleted and associating loans.
 * 
 * Implemented by Alex Rush
 */
public class Book {
    // Design note: Book contains only fields directly related to the
    // physical item; loan metadata is stored in a `Loan` object. This
    // avoids mixing historical loan data with the current book record.
	private int id;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher; // Added for 50% feature
    
    // Soft Delete Flag
    private boolean isDeleted = false; 

    private Loan loan;

    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    
    // Compatibility constructor for older code
    public Book(int id, String title, String author, String publicationYear) {
        this(id, title, author, publicationYear, "");
    }

    public int getId() { return id; } 
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublicationYear() { return publicationYear; }
    public void setPublicationYear(String publicationYear) { this.publicationYear = publicationYear; }
    
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    // 70% Feature: Getters/Setters for Soft Delete
    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

    public String getDetailsLong() {
        return "Book #" + id + "\nTitle: " + title + "\nAuthor: " + author + 
               "\nPub. Year: " + publicationYear + "\nPublisher: " + publisher;
    }
    
    public boolean isOnLoan() {
        return (loan != null);
    }
    
    public String getStatus() {
        return isOnLoan() ? "On Loan" : "Available";
    }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    public void returnToLibrary() {
        loan = null;
    }
}