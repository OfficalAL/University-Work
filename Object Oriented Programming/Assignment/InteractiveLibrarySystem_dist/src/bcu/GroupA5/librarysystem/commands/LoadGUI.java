package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.gui.MainWindow;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class LoadGUI implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        new MainWindow(library);
    }
    
}