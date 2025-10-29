import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

public class RainfallStatsTest {
	private final RainfallStats stats = new RainfallStats();
	 
	/*
	 * The count should initially be 0.
	 */
	@Test
	public void testInitialState() {
		assertEquals(0, stats.getCount());
	}
	
	/*
	 * If no measurements have been added, the getMean method
	 * should throw an IllegalStateException.
	 */
	@Test
	public void testMeanOfNoMeasurements() {
		// TODO: write unit test
	}
	
	/*
	 * If no measurements have been added, the getMax method
	 * should throw an IllegalStateException.
	 */
	@Test
	public void testMaxOfNoMeasurements() {
		// TODO: write unit test
	}
	
	/*
	 * If one measurement is added, the count should be one,
	 * and the mean and max should equal that measurement.
	 */
	@Test
	public void testAddMeasurement() throws InvalidRainfallException {
		stats.addMeasurement(4.0);
		assertEquals(1, stats.getCount());
		assertEquals(4.0, stats.getMean(), 0);
		assertEquals(4.0, stats.getMax(), 0);
	}
	
	/*
	 * The addMeasurement method should throw an InvalidRainfallException
	 * if the measurement is negative.
	 */
	@Test
	public void testAddInvalidMeasurement() {
		// TODO: write unit test
	}
	
	/*
	 * After three measurements are added, the count should be 3.
	 */
	@Test
	public void testCount() throws InvalidRainfallException {
		stats.addMeasurement(3.0);
		stats.addMeasurement(5.0);
		stats.addMeasurement(4.0);
		assertEquals(3, stats.getCount());
	}
	
	/*
	 * Test the mean of three measurements.
	 */
	@Test
	public void testMean() throws InvalidRainfallException {
		stats.addMeasurement(3.0);
		stats.addMeasurement(5.0);
		stats.addMeasurement(4.0);
		Assert.assertEquals(4.0, stats.getMean(), 0);
	}
	
	/*
	 * Test the max of three measurements.
	 */
	@Test
	public void testMax() throws InvalidRainfallException {
		stats.addMeasurement(3.0);
		stats.addMeasurement(5.0);
		stats.addMeasurement(4.0);
		assertEquals(5.0, stats.getMax(), 0);
	}

    private void assertEquals(int i, String count) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
