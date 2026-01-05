package bcu.GroupA5.librarysystem.model;

public class PatronTest {
    /**
     * Small unit-style checks for Patron fields. These are simple assertions
     * to help validate basic behaviour during development rather than a
     * full testing framework.
     */
    public static void main(String[] args) {
        Patron patron = new Patron(1, "Test Name", "1234567890", "test@email.com");
        assert patron.getEmail().equals("test@email.com") : "Email field not set correctly";
        patron.setEmail("new@email.com");
        assert patron.getEmail().equals("new@email.com") : "Email setter not working";
        System.out.println("Patron email field test passed.");
    }
}
