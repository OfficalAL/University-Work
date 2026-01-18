
package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Book;
import java.time.LocalDate;

/**
 * Command to show details of a book by ID (created 18 Jan 2026)
 */
public class ShowBook implements Command {
    private final int bookId;

    public ShowBook(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Book book = library.getBookByID(bookId);
        if (book == null) {
            throw new LibraryException("Book not found: " + bookId);
        }
        System.out.println("Book Details:");
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Publication Year: " + book.getPublicationYear());
        // Add more fields if needed
    }
}
