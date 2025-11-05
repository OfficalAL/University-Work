/**
 * Custom checked exception thrown when a rainfall measurement is negative.
 * This exception extends the standard Exception class.
 */
public class InvalidRainfallException extends Exception {
    // Extends Exception to create checked exception requiring explicit handling
    // Custom exception provides domain-specific error type for rainfall validation
    // Separates rainfall-specific errors from generic programming errors
    
    /**
     * Creates a new InvalidRainfallException with the specified detail message.
     * 
     * @param message the detail message explaining the exception
     */
    public InvalidRainfallException(String message) {
        super(message);
        // Parameterized constructor allows specific error messages for debugging
        // Delegates to parent Exception constructor for standard behavior
    }
    
    /**
     * Creates a new InvalidRainfallException with a default message.
     */
    public InvalidRainfallException() {
        super("Invalid rainfall measurement: negative value not allowed");
        // Default constructor provides standard error message
        // Simplifies exception throwing when specific message not needed
    }
}