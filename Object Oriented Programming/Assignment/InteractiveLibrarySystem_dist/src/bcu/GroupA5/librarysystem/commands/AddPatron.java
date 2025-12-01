package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;

import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name;
    private final String phone;

    public AddPatron(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // TODO: implementation here
    }
}