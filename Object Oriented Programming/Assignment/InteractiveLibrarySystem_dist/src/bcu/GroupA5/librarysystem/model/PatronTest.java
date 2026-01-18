package bcu.GroupA5.librarysystem.model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class PatronTest {

    @Test
    public void testEmailGetterSetter() {
        Patron patron = new Patron(1, "Test Name", "1234567890", "test@email.com");
        assertEquals("test@email.com", patron.getEmail());
        patron.setEmail("new@email.com");
        assertEquals("new@email.com", patron.getEmail());
    }

    @Test
    public void testIdNamePhoneGetters() {
        Patron patron = new Patron(2, "Alice", "555-1234", "alice@email.com");
        assertEquals(2, patron.getId());
        assertEquals("Alice", patron.getName());
        assertEquals("555-1234", patron.getPhone());
    }

    @Test
    public void testDeletedFlag() {
        Patron patron = new Patron(3, "Bob", "555-5678", "bob@email.com");
        assertFalse(patron.isDeleted());
        patron.setDeleted(true);
        assertTrue(patron.isDeleted());
    }

    @Test
    public void testAddBookAndGetBooks() {
        Patron patron = new Patron(4, "Carol", "555-0000", "carol@email.com");
        Book book = new Book(10, "BookTitle", "BookAuthor", "2022", "BookPublisher");
        assertTrue(patron.getBooks().isEmpty());
        patron.addBook(book);
        assertTrue(patron.getBooks().contains(book));
    }

    @Test
    public void testGetLoanHistoryInitiallyEmpty() {
        Patron patron = new Patron(5, "Dan", "555-1111", "dan@email.com");
        assertNotNull(patron.getLoanHistory());
        assertTrue(patron.getLoanHistory().isEmpty());
    }

    @Test
    public void testToString() {
        Patron patron = new Patron(6, "Eve", "555-2222", "eve@email.com");
        assertEquals("Eve (ID: 6)", patron.toString());
    }

    @Test(expected = bcu.GroupA5.librarysystem.main.LibraryException.class)
    public void testBorrowBookThrowsIfAlreadyBorrowed() throws bcu.GroupA5.librarysystem.main.LibraryException {
        Patron patron = new Patron(7, "Frank", "555-3333", "frank@email.com");
        Book book = new Book(20, "BookTitle", "BookAuthor", "2022", "BookPublisher");
        patron.borrowBook(book, LocalDate.now().plusDays(7));
        patron.borrowBook(book, LocalDate.now().plusDays(14)); // should throw
    }

    @Test(expected = bcu.GroupA5.librarysystem.main.LibraryException.class)
    public void testRenewBookThrowsIfNotBorrowed() throws bcu.GroupA5.librarysystem.main.LibraryException {
        Patron patron = new Patron(8, "Grace", "555-4444", "grace@email.com");
        Book book = new Book(21, "BookTitle", "BookAuthor", "2022", "BookPublisher");
        patron.renewBook(book, LocalDate.now().plusDays(7)); // should throw
    }

    @Test
    public void testBorrowAndReturnBook() throws bcu.GroupA5.librarysystem.main.LibraryException {
        Patron patron = new Patron(9, "Heidi", "555-5555", "heidi@email.com");
        Book book = new Book(22, "BookTitle", "BookAuthor", "2022", "BookPublisher");
        patron.borrowBook(book, LocalDate.now().plusDays(7));
        assertTrue(patron.getBooks().contains(book));
        // Simulate loan association
        Loan loan = new Loan(patron, book, LocalDate.now(), LocalDate.now().plusDays(7));
        book.setLoan(loan);
        patron.returnBook(book);
        assertFalse(patron.getBooks().contains(book));
        assertTrue(book.getLoan() == null || book.getLoan().isTerminated());
    }

    @Test(expected = bcu.GroupA5.librarysystem.main.LibraryException.class)
    public void testReturnBookThrowsIfNotBorrowed() throws bcu.GroupA5.librarysystem.main.LibraryException {
        Patron patron = new Patron(10, "Ivan", "555-6666", "ivan@email.com");
        Book book = new Book(23, "BookTitle", "BookAuthor", "2022", "BookPublisher");
        patron.returnBook(book); // should throw
    }
}
