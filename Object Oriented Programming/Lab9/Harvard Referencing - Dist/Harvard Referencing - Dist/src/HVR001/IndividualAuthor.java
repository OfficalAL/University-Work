// The IndividualAuthor class represents a single person as an author.
// It implements the Author interface and provides the author's last name and initials.
package HVR001;

/**
 * IndividualAuthor models an author who is an individual (not an institution).
 * It stores the last name and initials of the author.
 */
public class IndividualAuthor implements Author {
    // The last name of the author.
    private final String lastName;
    // The initials of the author (e.g., "D.").
    private final String initials;

    /**
     * Constructs an IndividualAuthor with the given last name and initials.
     * @param lastName the last name of the author
     * @param initials the initials of the author
     */
    public IndividualAuthor(String lastName, String initials) {
        this.lastName = lastName;
        this.initials = initials;
    }

    /**
     * Returns the full name of the author in the format "LastName, Initials".
     * @return the full name for referencing
     */
    @Override
    public String fullName() {
        return lastName + ", " + initials;
    }

    /**
     * Returns the citation name (last name) of the author.
     * @return the citation name
     */
    @Override
    public String citeName() {
        return lastName;
    }
}
