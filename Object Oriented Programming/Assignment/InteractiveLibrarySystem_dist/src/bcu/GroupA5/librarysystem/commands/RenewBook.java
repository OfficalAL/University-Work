package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Loan;
import bcu.GroupA5.librarysystem.model.Patron;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Command to renew a book loan by updating its due date.
 */
public class RenewBook implements Command {
    private final int patronId;
    private final int bookId;
    private final LocalDate newDueDate;

    public RenewBook(int patronId, int bookId, LocalDate newDueDate) {
        this.patronId = patronId;
        this.bookId = bookId;
        this.newDueDate = newDueDate;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);
        Loan loan = book.getLoan();
        if (loan == null || !loan.getPatron().equals(patron)) {
            throw new LibraryException("This book is not currently on loan to this patron.");
        }
        // Update the due date and persist. We snapshot the previous due date so
        // we can undo the change if writing to storage fails.
        LocalDate prevDue = loan.getDueDate();
        loan.setDueDate(newDueDate);
        try {
            LibraryData.store(library);
            System.out.println("Book ID " + bookId + " renewed by Patron ID " + patronId + " with new due date " + newDueDate);
        } catch (IOException ex) {
            // Rollback on failure to avoid a mismatch between in-memory state
            // and persistent storage.
            loan.setDueDate(prevDue);
            throw new LibraryException("Failed to save data after renewing book: " + ex.getMessage() + ". Changes rolled back.");
        }
    }
}
