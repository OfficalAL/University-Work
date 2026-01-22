package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
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

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // Auto-generate ID
        int maxId = 0;
        if (library.getPatrons().size() > 0) {
            int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId();
        }
        
        Patron patron = new Patron(++maxId, name, phone, email);
        library.addPatron(patron);
        
        try {
            LibraryData.store(library);
            System.out.println("Patron #" + patron.getId() + " added.");
        } catch (IOException ex) {
            library.getPatrons().remove(patron); 
            throw new LibraryException("Failed to save data. Patron addition rolled back.");
        }
    }
}