// The Book class represents a book publication and extends the Publication class.
// It adds a publisher field and customizes the Harvard reference format for books.
package HVR001;

import java.util.List;

/**
 * Book is a subclass of Publication that models a book reference.
 * It includes the publisher as an additional field.
 */
public class Book extends Publication {
    // The publisher of the book.
    private final String publisher;

    /**
     * Constructs a Book object with the given authors, title, year, and publisher.
     * @param authors   the list of authors of the book
     * @param title     the title of the book
     * @param year      the year the book was published
     * @param publisher the publisher of the book
     */
    public Book(List<Author> authors, String title, int year, String publisher) {
        super(authors, title, year);
        this.publisher = publisher;
    }

    /**
     * Returns the publisher of the book.
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Returns the Harvard reference string for the book, including the publisher.
     * @return the Harvard reference for this book
     */
    @Override
    public String harvardReference() {
        return super.harvardReference() + " " + publisher + ".";
    }
}
