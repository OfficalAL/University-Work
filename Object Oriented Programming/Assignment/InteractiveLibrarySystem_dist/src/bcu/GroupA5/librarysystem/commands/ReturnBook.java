package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.*;
import java.io.IOException;
import java.time.LocalDate;

public class ReturnBook implements Command {
    private final int patronId;
    private final int bookId;

    public ReturnBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);
        if (book.getLoan() == null || !book.getLoan().getPatron().equals(patron)) {
            throw new LibraryException("This book is not currently on loan to this patron.");
        }
        // Capture previous loan state so we can rollback if persistence fails.
        Loan loan = book.getLoan();
        boolean prevTerminated = loan.isTerminated();
        java.time.LocalDate prevReturnDate = loan.getReturnDate();
        // perform return: mark terminated, set return date and update objects
        loan.setTerminated(true);
        loan.setReturnDate(currentDate != null ? currentDate : java.time.LocalDate.now());
        book.returnToLibrary();
        patron.getBooks().remove(book);
        // Persist change; on failure restore previous state to avoid partial updates
        try {
            LibraryData.store(library);
            System.out.println("Book ID " + bookId + " returned by Patron ID " + patronId);
        } catch (IOException ex) {
            // rollback: restore loan and patron/book associations
            book.setLoan(loan);
            loan.setTerminated(prevTerminated);
            loan.setReturnDate(prevReturnDate);
            if (!patron.getBooks().contains(book)) {
                patron.getBooks().add(book);
            }
            throw new LibraryException("Failed to save data after returning book: " + ex.getMessage() + ". Changes rolled back.");
        }
    }
}
