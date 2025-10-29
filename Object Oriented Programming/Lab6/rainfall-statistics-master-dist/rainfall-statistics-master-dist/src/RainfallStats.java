/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.List;

class RainfallStats {
    
    private final List<Double> measurements;
    
    /**
     * Creates a new RainfallStats instance.
     */
    public RainfallStats() {
        this.measurements = new ArrayList<>();
    }

    /**
     * Returns the number of measurements that have been added.
     * @return the count of measurements
     */
    public int getCount() {
        return measurements.size();
    }

    /**
     * Returns the mean of all measurements.
     * @return the mean value
     * @throws IllegalStateException if no measurements have been added
     */
    public double getMean() {
        if (measurements.isEmpty()) {
            throw new IllegalStateException("No measurements available");
        }
        
        double sum = 0.0;
        for (double measurement : measurements) {
            sum += measurement;
        }
        return sum / measurements.size();
    }

    /**
     * Returns the maximum of all measurements.
     * @return the maximum value
     * @throws IllegalStateException if no measurements have been added
     */
    public double getMax() {
        if (measurements.isEmpty()) {
            throw new IllegalStateException("No measurements available");
        }
        
        double max = measurements.get(0);
        for (double measurement : measurements) {
            if (measurement > max) {
                max = measurement;
            }
        }
        return max;
    }

    /**
     * Adds a new rainfall measurement.
     * @param measurement the rainfall measurement to add
     * @throws InvalidRainfallException if the measurement is negative
     */
    public void addMeasurement(double measurement) throws InvalidRainfallException {
        if (measurement < 0.0) {
            throw new InvalidRainfallException("Rainfall measurement cannot be negative: " + measurement);
        }
        measurements.add(measurement);
    }
}
