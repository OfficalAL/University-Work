import java.io.*;

public class RainfallProgram {
	// Main class handles user interaction and input validation
	// Chosen as entry point to separate UI logic from calculation logic
	public static void main(String[] args) throws IOException {
		BufferedReader keyboard = new BufferedReader(
			new InputStreamReader(System.in) 
		);
		// BufferedReader chosen for efficient line-by-line input reading
		// Wraps InputStreamReader to handle character encoding from System.in
		 
		RainfallStats stats = new RainfallStats();
		// Single instance to accumulate all rainfall data throughout program execution
		// Encapsulates all statistical calculations and data storage
		System.out.println("Enter rainfall measurements (in mm), or \"end\" to stop.");
		
		while(true) {
			System.out.print("> ");
			String line = keyboard.readLine(); 
			// String variable to store each line of user input
			// Allows for easy comparison with "end" command
			if("end".equals(line)) {
				break;
			}
			
			try {
				// Parse the measurement and add it to stats
				double measurement = Double.parseDouble(line);
				// Double chosen to handle decimal rainfall values with precision
				// parseDouble converts string input to numeric value for calculations
				stats.addMeasurement(measurement);
			} catch (NumberFormatException e) {
				// Print a message if the measurement cannot be parsed
				System.out.println("Invalid measurement: " + line);
				// NumberFormatException handles non-numeric input gracefully
			} catch (InvalidRainfallException e) {
				// Print a message if the measurement is negative
				System.out.println("Invalid measurement: negative rainfall");
				// Custom exception provides domain-specific validation for rainfall data
			}
		}
		
		System.out.println(stats.getCount() + " measurement(s) entered.");
		
		// Prevent the exception when no measurements have been entered
		if (stats.getCount() > 0) {
			// Guard condition prevents division by zero and IllegalStateException
			// Ensures mean and max calculations only occur with valid data
			System.out.println("Mean rainfall: " + stats.getMean() + " mm");
			System.out.println("Maximum rainfall: " + stats.getMax() + " mm");
		}
	}
}
