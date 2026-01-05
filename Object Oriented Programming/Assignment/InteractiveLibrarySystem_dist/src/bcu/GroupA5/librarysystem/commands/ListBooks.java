package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import java.time.LocalDate;
import java.util.List;

public class ListBooks implements Command {
    // Note: class-level Javadoc added earlier; methods are intentionally
    // concise and focused on output formatting for CLI use.
    /**
     * Command to list books (read-only). Displays book summaries for the
     * CLI. Keeping listing separate from model changes encourages the
     * Single Responsibility Principle.
     */

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Book> books = library.getBooks();
        for (Book book : books) {
            System.out.println(book.getDetailsShort());
        }
        System.out.println(books.size() + " book(s)");
    }
}