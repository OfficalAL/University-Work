package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import java.time.LocalDate;

public interface Command {

    /**
     * Marker interface for command objects that encapsulate an operation
     * on the `Library` model. Using the Command pattern simplifies GUI
     * integration and testing: GUI windows instantiate and invoke command
     * objects while command classes contain the domain and persistence
     * logic.
     */

    public static final String HELP_MESSAGE =
            "Commands:\n"
            + "    listbooks                       print all books*\n"
            + "    listpatrons                     print all patrons\n"
            + "    addbook                         add a new book*\n"
            + "    addpatron                       add a new patron\n"
            + "    showbook                        show book details\n"
            + "    showpatron                      show patron details\n"
            + "    borrow                          borrow a book\n"
            + "    renew                           renew a book\n"
            + "    return                          return a book\n"
            + "    loadgui                         loads the GUI version of the app*\n"
            + "    help                            prints this help message*\n"
            + "    exit                            exits the program*\n";

    
    public void execute(Library library, LocalDate currentDate) throws LibraryException;
    
}