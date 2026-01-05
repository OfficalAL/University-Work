package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import java.io.IOException;
import java.time.LocalDate;

public class DeleteBook implements Command {
    private final int bookId;
    public DeleteBook(int bookId) {
        this.bookId = bookId;
    }
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // Soft-delete: mark book as deleted rather than removing it. This
        // preserves historical loans and avoids changing IDs of other books.
        Book book = library.getBookByID(bookId);
        book.setDeleted(true);
        try {
            LibraryData.store(library);
            System.out.println("Book #" + bookId + " deleted (hidden).");
        } catch (IOException ex) {
            // rollback the soft-delete if persistence fails
            book.setDeleted(false);
            throw new LibraryException("Failed to save data after deleting book: " + ex.getMessage() + ". Changes rolled back.");
        }
    }
}
