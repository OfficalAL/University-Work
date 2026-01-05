package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import java.io.IOException;
import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Backward compatibility constructor
    public AddPatron(String name, String phone) {
        this(name, phone, "");
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // Generate a new unique ID for the patron
        int newId = 1;
        if (!library.getPatrons().isEmpty()) {
            newId = library.getPatrons().stream().mapToInt(p -> p.getId()).max().getAsInt() + 1;
        }
        /*
         * Create and persist a new patron. We generate a new ID by taking the
         * max existing ID + 1. Immediately storing keeps GUI state and file
         * storage consistent. On failure we remove the newly added patron as
         * a simple rollback mechanism.
         */
        bcu.GroupA5.librarysystem.model.Patron patron = new bcu.GroupA5.librarysystem.model.Patron(newId, name, phone, email);
        try {
            library.addPatron(patron);
            LibraryData.store(library);
            System.out.println("Patron added: " + name + " (ID: " + newId + ")");
        } catch (IOException ex) {
            // rollback the in-memory insertion
            library.removePatron(newId);
            throw new LibraryException("Failed to save data after adding patron: " + ex.getMessage() + ". Changes rolled back.");
        }
    }
}