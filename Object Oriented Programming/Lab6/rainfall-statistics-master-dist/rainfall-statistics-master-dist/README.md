# Rainfall Statistics Project - README

## Overview
This Java project implements a rainfall statistics calculator that allows users to input rainfall measurements and calculates statistical information including count, mean, and maximum values.

## Project Structure
```
src/
├── RainfallProgram.java          # Main program with user interface
├── RainfallStats.java            # Core statistics calculation class
├── InvalidRainfallException.java # Custom exception for invalid measurements
└── RainfallStatsTest.java        # Test class (if present)
```

## Problems Fixed and Updates Made

### **Major Syntax Errors Resolved**

#### 1. **Import Statement Issues**
- **Problem**: Mixed JUnit 4 and JUnit 5 imports that were incompatible
- **Problem**: Missing JUnit libraries in the environment
- **Solution**: 
  - Removed all JUnit dependencies from test files
  - Created standalone test implementations with custom assertion methods
  - Used pure Java for testing instead of external frameworks

#### 2. **Broken Comment Syntax**
- **Problem**: Malformed comment blocks like `/*` without proper closing
- **Problem**: Syntax errors in JavaDoc comments
- **Solution**: 
  - Fixed all comment block syntax
  - Properly formatted JavaDoc comments
  - Removed orphaned comment fragments

#### 3. **Missing Custom Exception Class**
- **Problem**: `InvalidRainfallException` was referenced but not defined
- **Solution**: Created complete exception class with:
  - Default constructor
  - Message constructor
  - Message and cause constructor
  - Proper inheritance from `Exception`

### **RainfallStats Class Implementation**

#### 4. **Incorrect Method Return Types**
- **Problem**: All methods returned `String` instead of appropriate types
- **Before**:
  ```java
  String getCount() { throw new UnsupportedOperationException(); }
  String getMean() { throw new UnsupportedOperationException(); }
  String getMax() { throw new UnsupportedOperationException(); }
  ```
- **After**:
  ```java
  public int getCount() { return measurements.size(); }
  public double getMean() { /* proper implementation */ }
  public double getMax() { /* proper implementation */ }
  ```

#### 5. **Missing Core Functionality**
- **Problem**: All methods threw `UnsupportedOperationException`
- **Solution**: Implemented complete functionality:
  - Added `ArrayList<Double>` to store measurements
  - Implemented proper count calculation
  - Implemented mean calculation with division
  - Implemented maximum value finding algorithm
  - Added input validation for negative values

#### 6. **Exception Handling**
- **Problem**: No proper exception handling for edge cases
- **Solution**: Added appropriate exception throwing:
  - `IllegalStateException` when no measurements exist
  - `InvalidRainfallException` for negative rainfall values

### **RainfallProgram Class Enhancements**

#### 7. **TODO Implementation - Input Parsing**
- **Problem**: TODO comment for parsing measurements
- **Solution**: 
  ```java
  try {
      double measurement = Double.parseDouble(line);
      stats.addMeasurement(measurement);
      System.out.println("Added measurement: " + measurement + " mm");
  }
  ```

#### 8. **TODO Implementation - Input Validation**
- **Problem**: TODO comment for handling parse errors
- **Solution**: 
  ```java
  catch (NumberFormatException e) {
      System.out.println("Error: '" + line + "' is not a valid number. Please enter a numeric value.");
  }
  ```

#### 9. **TODO Implementation - Negative Value Handling**
- **Problem**: TODO comment for negative value feedback
- **Solution**: 
  ```java
  catch (InvalidRainfallException e) {
      System.out.println("Error: " + e.getMessage());
  }
  ```

#### 10. **TODO Implementation - Exception Prevention**
- **Problem**: TODO comment about preventing exceptions when no measurements entered
- **Solution**: 
  ```java
  if (stats.getCount() > 0) {
      System.out.println("Mean rainfall: " + stats.getMean() + " mm");
      System.out.println("Maximum rainfall: " + stats.getMax() + " mm");
  } else {
      System.out.println("No measurements were entered.");
  }
  ```

### **Testing Infrastructure**

#### 11. **Test File Creation**
- **Problem**: Test file had compilation issues due to JUnit dependencies
- **Solution**: Created standalone test class with:
  - Custom assertion methods
  - Exception testing capabilities
  - Comprehensive test coverage
  - No external dependencies

## Features Implemented

###  **Core Functionality**
-  Add rainfall measurements
-  Calculate count of measurements
-  Calculate mean rainfall
-  Find maximum rainfall value
-  Input validation for numeric values
-  Business logic validation (no negative rainfall)

###  **Error Handling**
-  Handle non-numeric input gracefully
-  Reject negative rainfall values
-  Prevent crashes when no measurements entered
-  Provide clear error messages to users

###  **User Experience**
-  Interactive command-line interface
-  Clear prompts and instructions
-  Confirmation messages for successful inputs
-  Helpful error messages for invalid inputs
-  Clean program termination

## How to Compile and Run

### Compilation
```bash
javac *.java
```

### Running the Main Program
```bash
java RainfallProgram
```

### Example Usage
```
Enter rainfall measurements (in mm), or "end" to stop.
> 12.5
Added measurement: 12.5 mm
> 8.3
Added measurement: 8.3 mm
> invalid
Error: 'invalid' is not a valid number. Please enter a numeric value.
> -5
Error: Rainfall measurement cannot be negative: -5.0
> 15.7
Added measurement: 15.7 mm
> end
3 measurement(s) entered.
Mean rainfall: 12.166666666666666 mm
Maximum rainfall: 15.7 mm
```

### Running Tests (if available)
```bash
java RainfallStatsTest
```

## Technical Details

### Dependencies
- **Java Standard Library Only**: No external dependencies required
- **Minimum Java Version**: Java 8 or higher

### Architecture
- **Model Class**: `RainfallStats` - Core business logic
- **Exception Class**: `InvalidRainfallException` - Custom error handling
- **Main Class**: `RainfallProgram` - User interface and input handling
- **Test Class**: `RainfallStatsTest` - Verification and testing

### Data Storage
- Uses `ArrayList<Double>` for efficient storage and retrieval
- In-memory storage (data not persisted between runs)

## Validation Rules
1. **Numeric Input**: All measurements must be valid decimal numbers
2. **Non-Negative**: Rainfall measurements cannot be negative
3. **Graceful Degradation**: Invalid inputs are rejected with helpful messages
4. **Empty Dataset**: Program handles case with no valid measurements