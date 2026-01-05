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
    private String publisher;
    private Loan loan;
    private boolean deleted = false;
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }

    // Backward compatibility constructor
    public Book(int id, String title, String author, String publicationYear) {
        this(id, title, author, publicationYear, "");
    }
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
	
    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

        public String getDetailsLong() {
        if (loan != null) {
            return "Book #" + id + " - " + title + "\nAuthor: " + author + "\nPublication Year: " + publicationYear
                + "\nPublisher: " + publisher + "\nStatus: On loan to Patron #" + loan.getPatron().getId()
                + " until " + loan.getDueDate();
        }
        return "Book #" + id + " - " + title + "\nAuthor: " + author + "\nPublication Year: " + publicationYear
                + "\nPublisher: " + publisher + "\nStatus: Available";
    }
    
    public boolean isOnLoan() {
        return (loan != null);
    }
    
    public String getStatus() {
        if (loan != null) {
            return "On loan to Patron #" + loan.getPatron().getId() + " until " + loan.getDueDate();
        }
        return null;
    }

    public LocalDate getDueDate() {
            if (loan != null) {
                return loan.getDueDate();
            }
        return null;
    }
    
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        if (loan == null) {
            throw new LibraryException("This book is not currently on loan.");
        }
        loan.setDueDate(dueDate);
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void returnToLibrary() {
        loan = null;
    }
    @Override
    public String toString() {
        return title + " by " + author + " (ID: " + id + ")";
    }
}