package bcu.GroupA5.librarysystem.data;

import bcu.GroupA5.librarysystem.main.LibraryException;
import bcu.GroupA5.librarysystem.model.Book;
import bcu.GroupA5.librarysystem.model.Library;
import bcu.GroupA5.librarysystem.model.Loan;
import bcu.GroupA5.librarysystem.model.Patron;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class LoanDataManager implements DataManager {
    /**
     * Loads and stores Loan records. Loans reference existing Book and
     * Patron objects and therefore are loaded after books and patrons.
     * We store start and due dates so loan history can be reconstructed
     * and displayed.
     */
    
    public final String RESOURCE = "./resources/data/loans.txt";

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int patronId = Integer.parseInt(properties[0]);
                    int bookId = Integer.parseInt(properties[1]);
                    LocalDate startDate = LocalDate.parse(properties[2]);
                    LocalDate dueDate = LocalDate.parse(properties[3]);
                    Patron patron = library.getPatronByID(patronId);
                    Book book = library.getBookByID(bookId);
                    Loan loan = new Loan(patron, book, startDate, dueDate);
                    book.setLoan(loan);
                    patron.borrowBook(book, dueDate);
                } catch (LibraryException | NumberFormatException ex) {
                    throw new LibraryException("Unable to parse loan on line " + line_idx + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Book book : library.getBooks()) {
                if (book.getLoan() != null) {
                    Loan loan = book.getLoan();
                    out.print(loan.getPatron().getId() + SEPARATOR);
                    out.print(loan.getBook().getId() + SEPARATOR);
                    out.print(loan.getStartDate().toString() + SEPARATOR);
                    out.print(loan.getDueDate().toString() + SEPARATOR);
                    out.println();
                }
            }
        }
    }
    
}