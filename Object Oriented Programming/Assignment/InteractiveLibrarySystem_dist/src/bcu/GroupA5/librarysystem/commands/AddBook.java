package bcu.GroupA5.librarysystem.commands;

import bcu.GroupA5.librarysystem.data.LibraryData;
import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import java.io.IOException;
import java.time.LocalDate;

public class AddBook implements Command {
    private final String title;
    private final String author;
    private final String publicationYear;
    private final String publisher;

    public AddBook(String title, String author, String publicationYear, String publisher) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }

    // Backward compatibility constructor
    public AddBook(String title, String author, String publicationYear) {
        this(title, author, publicationYear, "");
    }
    
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        int maxId = 0;
        if (!library.getBooks().isEmpty()) {
            int lastIndex = library.getBooks().size() - 1;
            maxId = library.getBooks().get(lastIndex).getId();
        }
        Book book = new Book(++maxId, title, author, publicationYear, publisher);
        /*
         * Persist immediately after adding the book. We generate the new ID
         * by taking the last book ID and incrementing it; this is simple and
         * deterministic for the coursework dataset. If storing to disk fails
         * we remove the added book to keep the in-memory state consistent
         * with persistent storage.
         */
        try {
            library.addBook(book);
            LibraryData.store(library);
            System.out.println("Book #" + book.getId() + " added.");
        } catch (IOException ex) {
            // rollback the in-memory insertion
            library.removeBook(book.getId());
            throw new LibraryException("Failed to save data after adding book: " + ex.getMessage() + ". Changes rolled back.");
        }
    }
}