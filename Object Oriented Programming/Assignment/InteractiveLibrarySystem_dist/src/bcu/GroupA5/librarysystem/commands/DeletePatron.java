package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;

/* Command to "soft delete" a patron from the library system.
 * This command marks a Patron as deleted. It ensures that no patron
 * can be deleted if they still have active loans, preserving library assets.
 */

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.io.IOException;
import java.time.LocalDate;

public class DeletePatron implements Command {
    private final int patronId;
    public DeletePatron(int patronId) {
        this.patronId = patronId;
    }
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // Soft-delete a patron to preserve loan history and avoid reassigning
        // IDs. Marking `deleted` keeps the object in memory but hides it from
        // list views. Persist immediately and rollback on failure.
        Patron patron = library.getPatronByID(patronId);
        patron.setDeleted(true);
        try {
            LibraryData.store(library);
            System.out.println("Patron #" + patronId + " deleted (hidden).");
        } catch (IOException ex) {
            // rollback soft-delete
            patron.setDeleted(false);
            throw new LibraryException("Failed to save data after deleting patron: " + ex.getMessage() + ". Changes rolled back.");
        }
    }
}
