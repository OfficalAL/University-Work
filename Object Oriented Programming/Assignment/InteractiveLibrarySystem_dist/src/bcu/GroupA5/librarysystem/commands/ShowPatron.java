package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.time.LocalDate;

/**
 * Command to show details of a patron by ID (created 18 Jan 2026)
 */
public class ShowPatron implements Command {
    private final int patronId;

    public ShowPatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        if (patron == null) {
            throw new LibraryException("Patron not found: " + patronId);
        }
        System.out.println("Patron Details:");
        System.out.println("ID: " + patron.getId());
        System.out.println("Name: " + patron.getName());
        System.out.println("Email: " + patron.getEmail());
        // Add more fields if needed
    }
}
