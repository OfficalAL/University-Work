package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.model.Library;
import java.time.LocalDate;

public class Help implements Command {
    /**
     * Prints the help message describing available CLI commands. Kept as
     * an independent command so the help text is discoverable and testable.
     */

    @Override
    public void execute(Library library, LocalDate currentDate) {
        System.out.println(Command.HELP_MESSAGE);
    }
}