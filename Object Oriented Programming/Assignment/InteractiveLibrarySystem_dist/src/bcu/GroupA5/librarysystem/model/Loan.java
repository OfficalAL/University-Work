package bcu.GroupA5.librarysystem.model;

import java.time.LocalDate;

/**
 * Loan class manages the borrowing process of books by patrons.
 * Tracks loan start date, due date, return date, and termination status.
 * Links books and patrons for each loan transaction.
 * 
 * Implemented by Alex Rush
 */
public class Loan {
    /**
     * Loan represents a single borrowing event linking a Patron and a Book.
     * It records start, due and return dates, and a terminated flag. The
     * separation of Loan from Book/Patron keeps historical data explicit
     * and allows loan history to be retained even after a book is marked
     * deleted or a patron is hidden.
     */
	private Patron patron;
    private Book book;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isTerminated;

    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
        this.patron = patron;
        this.book = book;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.isTerminated = false;
    }
    
    // Getters
    public Patron getPatron() { return patron; }
    public Book getBook() { return book; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isTerminated() { return isTerminated; }

    // Setters
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setTerminated(boolean terminated) { isTerminated = terminated; }
}