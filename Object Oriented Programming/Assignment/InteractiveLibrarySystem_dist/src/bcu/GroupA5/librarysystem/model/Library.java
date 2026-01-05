package bcu.GroupA5.librarysystem.model;

import bcu.GroupA5.librarysystem.main.LibraryException;
import java.util.*;

/**
 * Library class manages the collection of books and patrons.
 * Handles operations for adding, removing, and searching books and patrons.
 * Coordinates loans and returns, and maintains overall library data integrity.
 * 
 * Implemented by Alex Rush
 */
public class Library {
    
    private final int loanPeriod = 7;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public List<Book> getBooks() {
        List<Book> out = new ArrayList<>();
        for (Book book : books.values()) {
            if (!book.isDeleted()) {
                out.add(book);
            }
        }
        return Collections.unmodifiableList(out);
    }

    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }
        return books.get(id);
    }


    public Patron getPatronByID(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no such patron with that ID.");
        }
        return patrons.get(id);
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }

    /**
     * Remove a book from the internal collection (used for rollback scenarios).
     */
    public void removeBook(int id) {
        books.remove(id);
    }

    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
            throw new IllegalArgumentException("Duplicate patron ID.");
        }
        patrons.put(patron.getId(), patron);
    }

    /**
     * Remove a patron from the internal collection (used for rollback scenarios).
     */
    public void removePatron(int id) {
        patrons.remove(id);
    }

    // Note: `removeBook` and `removePatron` are intentionally minimal
    // helpers used only for rollback scenarios where an attempted in-memory
    // insert must be undone. We avoid more complex transaction logic here
    // to keep the coursework implementation straightforward.

    public List<Patron> getPatrons() {
        List<Patron> out = new ArrayList<>();
        for (Patron patron : patrons.values()) {
            if (!patron.isDeleted()) {
                out.add(patron);
            }
        }
        return Collections.unmodifiableList(out);
    }
}