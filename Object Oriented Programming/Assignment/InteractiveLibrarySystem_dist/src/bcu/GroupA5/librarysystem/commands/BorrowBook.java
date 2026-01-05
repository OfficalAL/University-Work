package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.*;
import java.io.IOException;
import java.time.LocalDate;

public class BorrowBook implements Command {
    /**
     * Command to issue a book to a patron.
     *
     * Design notes:
     * - We enforce a small default borrow limit here (2) to follow the
     *   coursework specification. Making it a constant inside the command
     *   keeps the rule local to the action that needs it.
     * - After applying the in-memory changes we persist using `LibraryData`.
     *   If persistence fails, we perform a rollback (undo) to leave the
     *   system in a consistent state. This mimics a simple transaction.
     */
    private final int patronId;
    private final int bookId;
    private final LocalDate dueDate;

    public BorrowBook(int patronId, int bookId, LocalDate dueDate) {
        this.patronId = patronId;
        this.bookId = bookId;
        this.dueDate = dueDate;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);
        int MAX_BOOKS = 2; // default limit per spec
        if (patron.getBooks().size() >= MAX_BOOKS) {
            throw new LibraryException("Patron has reached the maximum number of borrowed books (" + MAX_BOOKS + ").");
        }
        if (book.getLoan() != null) {
            throw new LibraryException("Book is already on loan.");
        }
        Loan loan = new Loan(patron, book, currentDate, dueDate);
        book.setLoan(loan);
        patron.borrowBook(book, dueDate);
        patron.getLoanHistory().add(loan);
        // Persist changes immediately to ensure GUI and CLI sessions remain
        // in sync with file storage. If the store fails we undo the in-memory
        // changes to avoid partial updates.
        try {
            LibraryData.store(library);
            System.out.println("Book ID " + bookId + " issued to Patron ID " + patronId);
        } catch (IOException ex) {
            // Rollback: remove associations created above so state is unchanged.
            book.setLoan(null);
            patron.getBooks().remove(book);
            patron.getLoanHistory().remove(loan);
            throw new LibraryException("Failed to save data after issuing book: " + ex.getMessage() + ". Changes rolled back.");
        }
    }
}
