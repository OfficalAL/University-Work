package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.gui.MainWindow;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import java.time.LocalDate;

public class LoadGUI implements Command {
    /**
     * Command used to launch the GUI version of the application. The
     * separation between CLI commands and GUI allows the same domain
     * and persistence logic to be reused for both interfaces.
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        new MainWindow(library);
    }
    
}