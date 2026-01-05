package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.time.LocalDate;
import java.util.List;

public class ListPatrons implements Command {
    /**
     * Command to list patrons. This command is read-only and does not
     * modify state; it formats and prints the patrons list for CLI use.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons();
        for (Patron patron : patrons) {
            System.out.println("ID: " + patron.getId() + ", Name: " + patron.getName() + ", Phone: " + patron.getPhone());
        }
        System.out.println(patrons.size() + " patron(s)");
    }
}
