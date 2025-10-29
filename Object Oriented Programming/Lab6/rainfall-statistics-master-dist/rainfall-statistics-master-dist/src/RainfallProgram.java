import java.io.*;

public class RainfallProgram {
	public static void main(String[] args) throws IOException {
		BufferedReader keyboard = new BufferedReader(
			new InputStreamReader(System.in) 
		);
		 
		RainfallStats stats = new RainfallStats();
		System.out.println("Enter rainfall measurements (in mm), or \"end\" to stop.");
		
		while(true) {
			System.out.print("> ");
			String line = keyboard.readLine(); 
			if("end".equals(line)) {
				break;
			}
			
			try {
				// Parse the measurement and add it to stats
				double measurement = Double.parseDouble(line);
				stats.addMeasurement(measurement);
				System.out.println("Added measurement: " + measurement + " mm");
			} catch (NumberFormatException e) {
				// Print a message if the measurement cannot be parsed
				System.out.println("Error: '" + line + "' is not a valid number. Please enter a numeric value.");
			} catch (InvalidRainfallException e) {
				// Print a message if the measurement is negative
				System.out.println("Error: " + e.getMessage());
			}
		}
		
		System.out.println(stats.getCount() + " measurement(s) entered.");
		
		// Prevent the exception when no measurements have been entered
		if (stats.getCount() > 0) {
			System.out.println("Mean rainfall: " + stats.getMean() + " mm");
			System.out.println("Maximum rainfall: " + stats.getMax() + " mm");
		} else {
			System.out.println("No measurements were entered.");
		}
	}
}
