package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Patron;
import java.time.LocalDate;
import java.util.List;

public class ListPatrons implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons();
        int count = 0;
        for (Patron patron : patrons) {
            if (!patron.isDeleted()) {
                // Fix: Added Email to the output string
                System.out.println("ID: " + patron.getId() + 
                                   ", Name: " + patron.getName() + 
                                   ", Phone: " + patron.getPhone() + 
                                   ", Email: " + patron.getEmail());
                count++;
            }
        }
        System.out.println(count + " patron(s)");
    }
}
