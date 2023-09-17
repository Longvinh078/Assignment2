package edu.psgv.sweng861;
//JUnit imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Java I/O imports
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Scanner;

//Total test case: 16
//4 Test cases for toMil method with valid metric value and unit
//5 Test cases for toMm method with valid imperial value and unit
//2 Test using assertThrows to expect IllegalArgumentException to be thrown for
//invalid input
//5 Test cases for main method

class UnitsConvertorTest {

	@Test
	// 4 Test cases for toMil method with valid metric value and unit
    public void testValidToMil() {
        assertEquals(39.3701, UnitsConvertor.toMil(1, "mm"));
        assertEquals(393.701, UnitsConvertor.toMil(1, "cm"));
        assertEquals(39370.1, UnitsConvertor.toMil(1, "meter"));
        assertEquals(3.93701E7, UnitsConvertor.toMil(1, "km"));
    }

    @Test
    public void testValidToMm() {
        // 5 Test cases for toMm method with valid imperial value and unit
        assertEquals(25.4, UnitsConvertor.toMm(1, "inch"));
        assertEquals(304.8, UnitsConvertor.toMm(1, "feet"));
        assertEquals(914.4, UnitsConvertor.toMm(1, "yd"));
        assertEquals(1609344.0, UnitsConvertor.toMm(1, "mi"));
        assertEquals(0.0254, UnitsConvertor.toMm(1, "mil"));
    }

    @Test
    public void testTwoMethodWithInvalidUnit() {
        // 2 Test using assertThrows to expect IllegalArgumentException to be thrown
        // when an invalid unit is provided to the toMil and toMm method.
        assertThrows(IllegalArgumentException.class, () -> {
            UnitsConvertor.toMil(1, "kmm");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            UnitsConvertor.toMm(1, "inc");
        });
        
    }
    
    // normalizeExpectedOutput - generate the eol character at run-time.
	// then there is no need to hard-code "\r\n" or "\n" for eol
	// and string comparisons are portable between Windows, macOS, Linux.
    public String normalizeExpectedOutput(String expectedOutput) {
    	String normExpectedOutput;
    	String [] outputs = expectedOutput.split("\n");
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	for (String str: outputs) {
    	pw.println(str);
    	}
    	pw.close();
    	normExpectedOutput = sw.toString();
    	return normExpectedOutput;
	}
    // getExpectedOutput - return desired expected output for given
    // input in test case
    public String getExpectedOutput(String input) {
        // Determined expected output base on the input
    	// I will send 4 test cases that can cover
    	// whole main method
    	String expectedOutput =  "Please Enter the input value followed by the unit:\n";
        if (input.equals("1 inch")) {
            return expectedOutput + "1 inch is:\n" +
                    "25.400000 mm\n" +
                    "2.540000 cm\n" +
                    "0.025400 m\n" +
                    "0.000025 km\n";
        } else if (input.equals("1 cm")) {
        	return expectedOutput +"1 cm is:\n" +
        		"3.9e+02 mil\n" +
        		"0.39 inch\n" +
        		"0.033 ft\n" +
        		"0.011 yard\n" +
        		"6.2e-06 mile\n";
        } else if (input.equals("1 invalidUnit")) {
            return expectedOutput + "Invalid input";
        } else if (input.equals("invalidInput")) {
            return expectedOutput + "Invalid input";
        } else if (input.equals("!!!!")) {
            return expectedOutput + "Invalid input";
        }
        return expectedOutput; // Return default if unknown input
    }
    @Test
    //Test main method by using Java I/O only
    //to control System.in and System.out
    public void mainTest() {
    //simulate user input for multiple case to fully coverage the main code
	//by store input into String array
        String[] testCase = {
        		//All test cases to cover whole main method
                "1 inch",
                "1 cm",
                "1 invalidUnit",
                "invalidInput",
                "!!!!"
            };
    //Store original System.in and System.out
    	InputStream sysIn = System.in;
    	PrintStream sysOut = System.out;
    //Initialize a stream to capture console output
	// This is the technique to capture the textual output
	// from System.out.print calls.
    	ByteArrayOutputStream myOut = new ByteArrayOutputStream();
    	System.setOut(new PrintStream(myOut));
    // Loop through each test case/input store in testCase array
    	for (String input: testCase) {
    		//Assigned the input stream with the current test case
    		InputStream myIn = new ByteArrayInputStream(input.getBytes());
    		System.setIn(myIn);
    		// Start the main test
        	UnitsConvertor.main(null);
        	//Capture the console output by calling method getExpectedOutput
        	//base on test case will provided its desired output
        	String unNormalizedExpectedOutput = getExpectedOutput(input);
        	String normalizedExpectedOutput = normalizeExpectedOutput(unNormalizedExpectedOutput);
        	// Check results
        	String printResult = myOut.toString();// Trim to remove leading/trailing whitespace
        	assertEquals(normalizedExpectedOutput, printResult);
        	//reset the output stream for next test case
        	myOut.reset();
    	}
        // Restore System variables to their original values
        System.setOut(sysOut);
        System.setIn(sysIn);
   }
}