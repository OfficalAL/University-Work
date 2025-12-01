package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class ListBooks implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Book> books = library.getBooks();
        for (Book book : books) {
            System.out.println(book.getDetailsShort());
        }
        System.out.println(books.size() + " book(s)");
    }
}