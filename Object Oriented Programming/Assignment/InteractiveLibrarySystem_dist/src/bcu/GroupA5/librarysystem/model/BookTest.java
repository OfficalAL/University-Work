package bcu.GroupA5.librarysystem.model;

public class BookTest {
    /**
     * Tiny smoke tests for Book fields (publisher getters/setters).
     * These quick checks help detect regressions while developing.
     */
    public static void main(String[] args) {
        Book book = new Book(1, "Test Title", "Test Author", "2020", "Test Publisher");
        assert book.getPublisher().equals("Test Publisher") : "Publisher field not set correctly";
        book.setPublisher("New Publisher");
        assert book.getPublisher().equals("New Publisher") : "Publisher setter not working";
        System.out.println("Book publisher field test passed.");
    }
}
