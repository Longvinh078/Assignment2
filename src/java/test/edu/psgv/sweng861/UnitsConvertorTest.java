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
	    public void testToMilWithMm() {
	        assertEquals(39.3701, UnitsConvertor.toMil(1, "mm"));
	    }

	    @Test
	    public void testToMilWithCm() {
	        assertEquals(393.701, UnitsConvertor.toMil(1, "cm"));
	    }

	    @Test
	    public void testToMilWithMeter() {
	        assertEquals(39370.1, UnitsConvertor.toMil(1, "meter"));
	    }

	    @Test
	    public void testToMilWithKm() {
	        assertEquals(3.93701E7, UnitsConvertor.toMil(1, "km"));
	    }

	    @Test
	    public void testToMmWithInch() {
	        assertEquals(25.4, UnitsConvertor.toMm(1, "inch"));
	    }

	    @Test
	    public void testToMmWithFeet() {
	        assertEquals(304.8, UnitsConvertor.toMm(1, "feet"));
	    }

	    @Test
	    public void testToMmWithYard() {
	        assertEquals(914.4, UnitsConvertor.toMm(1, "yd"));
	    }

	    @Test
	    public void testToMmWithMile() {
	        assertEquals(1609344.0, UnitsConvertor.toMm(1, "mi"));
	    }

	    @Test
	    public void testToMmWithMil() {
	        assertEquals(0.0254, UnitsConvertor.toMm(1, "mil"));
	    }

	    @Test
	    public void testToMilWithInvalidUnit() {
	        assertThrows(IllegalArgumentException.class, () -> {
	            UnitsConvertor.toMil(1, "kmm");
	        });
	    }

	    @Test
	    public void testToMmWithInvalidUnit() {
	        assertThrows(IllegalArgumentException.class, () -> {
	            UnitsConvertor.toMm(1, "inc");
	        });
	    }

	    @Test
	    public void testMainWithValidImperialInput() {
	        String input = "1 inch";
	        String expectedOutput = "Please Enter the input value followed by the unit:\n" +
	                "1 inch is:\n" +
	                "25.400000 mm\n" +
	                "2.540000 cm\n" +
	                "0.025400 m\n" +
	                "0.000025 km";
	        runMainTest(input, expectedOutput);
	    }
	    @Test
	    public void testMainWithValidMetricInput() {
	        String input = "1 cm";
	        String expectedOutput = "Please Enter the input value followed by the unit:\n" +
	                "1 cm is:\n" +
	                "3.9e+02 mil\n" +
	                "0.39 inch\n" +
	                "0.033 ft\n" +
	                "0.011 yard\n" +
	                "6.2e-06 mile";
	        runMainTest(input, expectedOutput);
	    }
	    @Test
	    public void testMainWithInvalidInput() {
	        String input = "invalidInput";
	        String expectedOutput = "Please Enter the input value followed by the unit:\nInvalid input\n";
	        runMainTest(input, expectedOutput);
	    }

	    @Test
	    public void testMainWithInvalidUnit() {
	        String input = "1 invalidUnit";
	        String expectedOutput = "Please Enter the input value followed by the unit:\nInvalid input\n";
	        runMainTest(input, expectedOutput);
	    }

	    private void runMainTest(String input, String expectedOutput) {
	        InputStream sysIn = System.in;
	        PrintStream sysOut = System.out;
	        ByteArrayOutputStream myOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(myOut));
	        InputStream myIn = new ByteArrayInputStream(input.getBytes());
	        System.setIn(myIn);
	        UnitsConvertor.main(null);
	        String normalizedExpectedOutput = normalizeExpectedOutput(expectedOutput);
	        String printResult = myOut.toString();
	        assertEquals(normalizedExpectedOutput, printResult);
	        System.setOut(sysOut);
	        System.setIn(sysIn);
	    }

	    private String normalizeExpectedOutput(String expectedOutput) {
	        String normExpectedOutput;
	        String[] outputs = expectedOutput.split("\n");
	        StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        for (String str : outputs) {
	            pw.println(str);
	        }
	        pw.close();
	        normExpectedOutput = sw.toString();
	        return normExpectedOutput;
	    }
}
