// The Author interface represents an author of a publication.
// It provides methods to get the author's full name and citation name for referencing.
package HVR001;

/**
 * The Author interface defines the contract for author objects used in publications.
 * Implementations can represent individual or institutional authors.
 */
public interface Author {
    /**
     * Returns the full name of the author as it should appear in a reference list.
     * For example: "Knuth, D." or "Oracle".
     * @return the full name of the author
     */
    public String fullName();

    /**
     * Returns the citation name of the author, typically used for in-text citations.
     * For example: "Knuth" or "Oracle".
     * @return the citation name of the author
     */
    public String citeName();
}
