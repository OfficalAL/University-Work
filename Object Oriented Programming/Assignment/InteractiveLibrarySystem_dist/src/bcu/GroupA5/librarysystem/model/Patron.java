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
    private String email;
    private final List<Book> books = new ArrayList<>();
    private final List<Loan> loanHistory = new ArrayList<>();
    private boolean deleted = false;
        public List<Loan> getLoanHistory() {
            return loanHistory;
        }
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Patron(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Backward compatibility constructor
    public Patron(int id, String name, String phone) {
        this(id, name, phone, "");
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        // Prevent duplicate borrow entries for the same book
        if (books.contains(book)) {
            throw new LibraryException("Book already borrowed by this patron.");
        }
        // Add the association between patron and book. The actual Loan object
        // is created by the command object and stored on the Book. We keep a
        // local list of borrowed `Book` references for quick access and GUI
        // display. This separation keeps responsibilities clear: `Loan` owns
        // loan metadata while `Patron` simply tracks currently-held books.
        books.add(book);
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        // Ensure the patron currently holds this book before renewing.
        if (!books.contains(book)) {
            throw new LibraryException("This patron has not borrowed this book.");
        }
        // Delegate the due date update to the Book/Loan instance. Keeping
        // the actual due-date change inside `Book`/`Loan` centralises loan
        // state management and avoids duplicated logic.
        book.setDueDate(dueDate);
    }

    public void returnBook(Book book) throws LibraryException {
        // Validate the patron actually borrowed this book
        if (!books.contains(book)) {
            throw new LibraryException("This patron has not borrowed this book.");
        }
        // Terminate the loan and record the return date. We update the Loan
        // object so loan history retains an accurate record, then remove the
        // association from both Book and Patron. This preserves history while
        // keeping current state consistent for lookups/UI.
        if (book.getLoan() != null) {
            book.getLoan().setTerminated(true);
            book.getLoan().setReturnDate(java.time.LocalDate.now());
        }
        book.returnToLibrary();
        books.remove(book);
    }

    public void addBook(Book book) {
        // Utility used by loaders/rollback to add a book reference to this
        // patron without performing validations (caller manages correctness).
        if (!books.contains(book)) {
            books.add(book);
        }
    }
    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}