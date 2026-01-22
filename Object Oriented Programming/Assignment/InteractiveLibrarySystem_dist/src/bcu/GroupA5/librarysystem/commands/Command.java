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
            + "    showbook [id]                   show book details\n"
            + "    showpatron [id]                 show patron details\n"
            + "    borrow [patronId] [bookId]      borrow a book\n"
            + "    renew [patronId] [bookId]       renew a book\n"
            + "    return [patronId] [bookId]      return a book\n"
            + "    deletebook [id]                 delete (hide) a book\n"
            + "    deletepatron [id]               delete (hide) a patron\n"
            + "    loadgui                         loads the GUI version of the app*\n"
            + "    help                            prints this help message*\n"
            + "    exit                            exits the program*\n";

    
    public void execute(Library library, LocalDate currentDate) throws LibraryException;
    
}