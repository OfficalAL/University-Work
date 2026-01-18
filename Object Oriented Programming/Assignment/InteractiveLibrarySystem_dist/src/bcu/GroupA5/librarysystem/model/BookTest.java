package bcu.GroupA5.librarysystem.model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class BookTest {

    @Test
    public void testPublisherGetterSetter() {
        Book book = new Book(1, "Test Title", "Test Author", "2020", "Test Publisher");
        assertEquals("Test Publisher", book.getPublisher());
        book.setPublisher("New Publisher");
        assertEquals("New Publisher", book.getPublisher());
    }

    @Test
    public void testIdGetterSetter() {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        assertEquals(1, book.getId());
        book.setId(2);
        assertEquals(2, book.getId());
    }

    @Test
    public void testTitleGetterSetter() {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        assertEquals("Title", book.getTitle());
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
    }

    @Test
    public void testAuthorGetterSetter() {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        assertEquals("Author", book.getAuthor());
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    public void testPublicationYearGetterSetter() {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        assertEquals("2020", book.getPublicationYear());
        book.setPublicationYear("2021");
        assertEquals("2021", book.getPublicationYear());
    }

    @Test
    public void testDeletedFlag() {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        assertFalse(book.isDeleted());
        book.setDeleted(true);
        assertTrue(book.isDeleted());
    }

    @Test
    public void testGetDetailsShort() {
        Book book = new Book(5, "ShortTitle", "Author", "2020", "Publisher");
        assertEquals("Book #5 - ShortTitle", book.getDetailsShort());
    }

    @Test
    public void testGetDetailsLongAvailable() {
        Book book = new Book(2, "LongTitle", "Author", "2020", "Publisher");
        String details = book.getDetailsLong();
        assertTrue(details.contains("Book #2 - LongTitle"));
        assertTrue(details.contains("Status: Available"));
    }

    @Test
    public void testOnLoanAndReturnToLibrary() {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        assertFalse(book.isOnLoan());
        // Create dummy Patron and Loan
        Patron patron = new Patron(1, "Patron Name", "1234567890", "email@example.com");
        Loan loan = new Loan(patron, book, LocalDate.now(), LocalDate.now().plusDays(7));
        book.setLoan(loan);
        assertTrue(book.isOnLoan());
        book.returnToLibrary();
        assertFalse(book.isOnLoan());
    }

    @Test
    public void testGetStatusAndDueDate() {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        assertNull(book.getStatus());
        assertNull(book.getDueDate());
        Patron patron = new Patron(1, "Patron Name", "1234567890", "email@example.com");
        LocalDate due = LocalDate.now().plusDays(10);
        Loan loan = new Loan(patron, book, LocalDate.now(), due);
        book.setLoan(loan);
        assertTrue(book.getStatus().contains("On loan to Patron #1"));
        assertEquals(due, book.getDueDate());
    }

    @Test(expected = bcu.GroupA5.librarysystem.main.LibraryException.class)
    public void testSetDueDateThrowsIfNotOnLoan() throws bcu.GroupA5.librarysystem.main.LibraryException {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        book.setDueDate(LocalDate.now());
    }

    @Test
    public void testSetDueDateWhenOnLoan() throws bcu.GroupA5.librarysystem.main.LibraryException {
        Book book = new Book(1, "Title", "Author", "2020", "Publisher");
        Patron patron = new Patron(1, "Patron Name", "1234567890", "email@example.com");
        Loan loan = new Loan(patron, book, LocalDate.now(), LocalDate.now().plusDays(5));
        book.setLoan(loan);
        LocalDate newDue = LocalDate.now().plusDays(15);
        book.setDueDate(newDue);
        assertEquals(newDue, book.getDueDate());
    }

    @Test
    public void testToString() {
        Book book = new Book(7, "BookTitle", "BookAuthor", "2022", "BookPublisher");
        assertEquals("BookTitle by BookAuthor (ID: 7)", book.toString());
    }
}
