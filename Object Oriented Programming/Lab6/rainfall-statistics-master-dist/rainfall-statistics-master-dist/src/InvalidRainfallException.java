/**
 * Exception thrown when an invalid rainfall measurement is provided.
 */
public class InvalidRainfallException extends Exception {
    
    /**
     * Creates a new InvalidRainfallException with no message.
     */
    public InvalidRainfallException() {
        super();
    }
    
    /**
     * Creates a new InvalidRainfallException with the specified message.
     * @param message the error message
     */
    public InvalidRainfallException(String message) {
        super(message);
    }
    
    /**
     * Creates a new InvalidRainfallException with the specified message and cause.
     * @param message the error message
     * @param cause the underlying cause
     */
    public InvalidRainfallException(String message, Throwable cause) {
        super(message, cause);
    }
}