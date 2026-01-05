package bcu.GroupA5.librarysystem.main;

/**
 * LibraryException extends {@link Exception} class and is a custom exception
 * that is used to notify the user about errors or invalid commands.
 * 
 */
public class LibraryException extends Exception {
    /**
     * Simple domain exception used to report business-rule violations and
     * data-loading errors. Using a custom exception type allows callers to
     * distinguish domain errors from IO/runtime exceptions.
     */

    public LibraryException(String message) {
        super(message);
    }
}