package com.codexp.springmetric.monitor;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ErrorTrackerTest {

        @Test
        void testAddSingleError() {
                ErrorTracker errorTracker = new ErrorTracker();
                errorTracker.addError(1, "Sample error message");

                Map<Integer, String> errors = errorTracker.getErrors();
                assertEquals(1, errors.size());
                assertTrue(errors.containsKey(1));
                assertEquals("Sample error message", errors.get(1));
        }

        @Test
        void testAddMultipleErrors() {
                ErrorTracker errorTracker = new ErrorTracker();
                errorTracker.addError(1, "Error 1");
                errorTracker.addError(2, "Error 2");
                errorTracker.addError(3, "Error 3");

                Map<Integer, String> errors = errorTracker.getErrors();
                assertEquals(3, errors.size());
                assertEquals("Error 1", errors.get(1));
                assertEquals("Error 2", errors.get(2));
                assertEquals("Error 3", errors.get(3));
        }

        @Test
        void testReplaceError() {
                ErrorTracker errorTracker = new ErrorTracker();
                errorTracker.addError(1, "Original error");
                errorTracker.addError(1, "New error");

                Map<Integer, String> errors = errorTracker.getErrors();
                assertEquals(1, errors.size());
                assertEquals("New error", errors.get(1));
        }

        @Test
        void testGetErrorsReturnsCopy() {
                ErrorTracker errorTracker = new ErrorTracker();
                errorTracker.addError(1, "Sample error");

                Map<Integer, String> errors = errorTracker.getErrors();
                errors.put(2, "Modified error");

                Map<Integer, String> actualErrors = errorTracker.getErrors();
                assertFalse(actualErrors.containsKey(2));
        }

        @Test
        void testPrintErrors() {
                ErrorTracker errorTracker = new ErrorTracker();
                errorTracker.addError(1, "Error 1");
                errorTracker.addError(2, "Error 2");

                ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));

                errorTracker.printErrors();

                String lineSeparator = System.lineSeparator();
                String expectedOutput = "Errors (2):" + lineSeparator + "Line 1: Error 1" + lineSeparator + "Line 2: Error 2" + lineSeparator;
                assertEquals(expectedOutput, outContent.toString());

                System.setOut(System.out);  // Reset the standard output
        }

}
