// The InstitutionalAuthor class represents an organization or institution as an author.
// It implements the Author interface and provides the institution's name.
package HVR001;

/**
 * InstitutionalAuthor models an author that is an institution or organization.
 * It stores the name of the institution.
 */
public class InstitutionalAuthor implements Author {
    // The name of the institution.
    private final String name;

    /**
     * Constructs an InstitutionalAuthor with the given name.
     * @param name the name of the institution
     */
    public InstitutionalAuthor(String name) {
        this.name = name;
    }

    /**
     * Returns the full name of the institution for referencing.
     * @return the full name
     */
    @Override
    public String fullName() {
        return name;
    }

    /**
     * Returns the citation name of the institution (same as full name).
     * @return the citation name
     */
    @Override
    public String citeName() {
        return name;
    }
}
