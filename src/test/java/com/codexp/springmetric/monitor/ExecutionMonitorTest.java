package com.codexp.springmetric.monitor;

import com.codexp.springmetric.config.ExecutionMonitorProperties;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionMonitorTest {

        private ExecutionMonitorProperties createDefaultProperties() {
                ExecutionMonitorProperties properties = new ExecutionMonitorProperties();
                properties.setPrintIntervalSeconds(0);  // To prevent scheduled tasks during testing
                return properties;
        }

        @Test
        void testIncrementProcessedLines() {
                ExecutionMonitor executionMonitor = new ExecutionMonitor(createDefaultProperties());

                ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));

                executionMonitor.incrementLinesProcessed();
                executionMonitor.printProgressAndErrors();

                String lineSeparator = System.lineSeparator();
                String expectedOutput = "Progress: 1 lines processed out of 1000 (0.10%)" + lineSeparator + "Errors (0):" + lineSeparator ;
                assertEquals(expectedOutput, outContent.toString());

                System.setOut(System.out);  // Reset the standard output
        }

        @Test
        void testAddError() {
                ExecutionMonitor executionMonitor = new ExecutionMonitor(createDefaultProperties());
                executionMonitor.addError(1, "Error 1");
                executionMonitor.addError(2, "Error 2");

                // Assuming you've added a getter for errorTracker in ExecutionMonitor
                Map<Integer, String> errors = executionMonitor.getErrorTracker().getErrors();
                assertEquals(2, errors.size());
                assertEquals("Error 1", errors.get(1));
                assertEquals("Error 2", errors.get(2));
        }

        @Test
        void testPrintProgressAndErrors() {
                ExecutionMonitor executionMonitor = new ExecutionMonitor(createDefaultProperties());
                executionMonitor.addError(1, "Error 1");
                executionMonitor.incrementLinesProcessed();

                ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));

                executionMonitor.printProgressAndErrors();

                String lineSeparator = System.lineSeparator();
                String expectedOutput = "Progress: 1 lines processed out of 1000 (0.10%)" + lineSeparator + "Errors (1):" + lineSeparator + "Line 1: Error 1" + lineSeparator;
                assertEquals(expectedOutput, outContent.toString());

                System.setOut(System.out);  // Reset the standard output
        }

        @Test
        void testClose() {
                ExecutionMonitor executionMonitor = new ExecutionMonitor(createDefaultProperties());

                try {
                        executionMonitor.close();
                } catch (Exception e) {
                        fail("Exception thrown during close: " + e.getMessage());
                }

                // TODO: Add logic to verify that resources (like executors) are properly shut down. if isExecutorShutdown() in ExecutionMonitor: assertTrue(executionMonitor.isExecutorShutdown());
        }
}
