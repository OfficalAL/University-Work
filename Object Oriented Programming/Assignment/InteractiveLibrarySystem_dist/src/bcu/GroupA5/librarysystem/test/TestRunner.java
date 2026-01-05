package bcu.GroupA5.librarysystem.test;

import bcu.GroupA5.librarysystem.commands.AddBook;
import bcu.GroupA5.librarysystem.commands.AddPatron;
import bcu.GroupA5.librarysystem.commands.BorrowBook;
import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Library;
import java.time.LocalDate;

public class TestRunner {
    /**
     * Lightweight test runner to exercise a few mutating commands and
     * verify persistence. This is not a unit test framework — it is a
     * convenience harness to smoke-test the key flows quickly during
     * development.
     */
    public static void main(String[] args) {
        try {
            Library library = LibraryData.load();

            // Add a book
            AddBook addBook = new AddBook("TRuntime Book", "T Author", "2025", "T Publisher");
            addBook.execute(library, LocalDate.now());

            // Add a patron
            AddPatron addPatron = new AddPatron("Tester", "0123456789", "test@example.com");
            addPatron.execute(library, LocalDate.now());

            // Borrow the book by the new patron (IDs should be the last ones)
            int bookId = library.getBooks().get(library.getBooks().size() - 1).getId();
            int patronId = library.getPatrons().get(library.getPatrons().size() - 1).getId();
            BorrowBook borrow = new BorrowBook(patronId, bookId, LocalDate.now().plusDays(7));
            borrow.execute(library, LocalDate.now());

            System.out.println("TestRunner completed successfully.");
        } catch (LibraryException | java.io.IOException ex) {
            System.err.println("TestRunner failed: " + ex.getMessage());
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
