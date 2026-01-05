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

    public static final String HELP_MESSAGE = "Commands:\n"
            + "\tlistbooks                       print all books*\n"
            + "\tlistpatrons                     print all patrons\n"
            + "\taddbook                         add a new book*\n"
            + "\taddpatron                       add a new patron\n"
            + "\tshowbook                        show book details\n"
            + "\tshowpatron                      show patron details\n"
            + "\tborrow                          borrow a book\n"
            + "\trenew                           renew a book\n"
            + "\treturn                          return a book\n"
            + "\tloadgui                         loads the GUI version of the app*\n"
            + "\thelp                            prints this help message*\n"
            + "\texit                            exits the program*";

    
    public void execute(Library library, LocalDate currentDate) throws LibraryException;
    
}