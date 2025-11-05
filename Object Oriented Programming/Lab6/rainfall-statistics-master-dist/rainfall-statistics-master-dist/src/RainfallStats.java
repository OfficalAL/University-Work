/**
 * A class that records rainfall measurements and computes statistics.
 * This class tracks the count, total, and maximum of rainfall measurements.
 */
class RainfallStats {
    
    // Instance variables to store statistics
    private int count;
    // Count chosen as int - sufficient range for measurement counting
    // Private to maintain encapsulation and prevent external modification
    private double total;
    // Double for precise decimal arithmetic in rainfall summation
    // Accumulates all measurements for mean calculation (total ÷ count)
    private double max;
    // Double to match measurement precision for maximum value tracking
    // Updated with each new measurement to maintain running maximum
    
    /**
     * Constructor initializes all values to 0.
     */
    public RainfallStats() {
        count = 0;
        total = 0.0;
        max = 0.0;
        // Zero initialization ensures clean starting state
        // Prevents undefined behavior from uninitialized variables
    }
    
    /**
     * Adds a rainfall measurement to the statistics.
     * 
     * @param measurement the rainfall measurement in millimeters
     * @throws InvalidRainfallException if the measurement is negative
     */
    public void addMeasurement(double measurement) throws InvalidRainfallException {
        // Check if measurement is negative
        if (measurement < 0) {
            throw new InvalidRainfallException("Rainfall measurement cannot be negative: " + measurement);
            // Custom exception provides type-safe error handling for domain validation
            // Negative rainfall is physically impossible, so this enforces data integrity
        }
        
        // Update statistics
        count++;
        // Increment count for each valid measurement added
        total += measurement;
        // Running sum accumulation for efficient mean calculation
        
        // Update maximum (for first measurement or if this is larger)
        if (count == 1 || measurement > max) {
            max = measurement;
            // Conditional update maintains running maximum without storing all values
            // First measurement automatically becomes initial maximum
        }
    }
    
    /**
     * Returns the number of measurements recorded.
     * 
     * @return the count of measurements as an int
     */
    public int getCount() {
        return count;
    }
    
    /**
     * Returns the mean of the rainfall measurements.
     * 
     * @return the mean rainfall as a double
     * @throws IllegalStateException if no measurements have been added
     */
    public double getMean() {
        if (count == 0) {
            throw new IllegalStateException("Cannot calculate mean: no measurements have been added");
            // IllegalStateException chosen for invalid object state (no data to calculate mean)
            // Prevents division by zero and provides meaningful error message
        }
        return total / count;
        // Simple arithmetic mean calculation using accumulated total and count
    }
    
    /**
     * Returns the maximum rainfall measurement.
     * 
     * @return the maximum rainfall as a double
     * @throws IllegalStateException if no measurements have been added
     */
    public double getMax() {
        if (count == 0) {
            throw new IllegalStateException("Cannot get maximum: no measurements have been added");
            // Consistent error handling pattern with getMean method
            // Prevents returning meaningless maximum when no data exists
        }
        return max;
        // Direct return of maintained running maximum - O(1) efficiency
    }
}
