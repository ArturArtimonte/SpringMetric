package com.codexp.springmetric.monitor;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ProgressMonitorTest {

        @Test
        void testIncrementProcessedLines() {
                ProgressMonitor progressMonitor = new ProgressMonitor();
                String lineSeparator = System.lineSeparator();

                ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));

                progressMonitor.incrementProcessedLines();
                progressMonitor.printProgress(1000.0f);

                String expectedOutput = "Progress: 1 lines processed out of 1000 (0.10%)" + lineSeparator;
                assertEquals(expectedOutput, outContent.toString());

                System.setOut(System.out);  // Reset the standard output
        }

        @Test
        void testPrintProgress() {
                ProgressMonitor progressMonitor = new ProgressMonitor();
                String lineSeparator = System.lineSeparator();

                ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));

                progressMonitor.incrementProcessedLines();
                progressMonitor.printProgress(1000.0f);

                String expectedOutput = "Progress: 1 lines processed out of 1000 (0.10%)" + lineSeparator;
                assertEquals(expectedOutput, outContent.toString());

                System.setOut(System.out);  // Reset the standard output
        }


        @Test
        void testGetExecutionTime() throws InterruptedException {
                ProgressMonitor progressMonitor = new ProgressMonitor();

                // Test initial state
                assertTrue(progressMonitor.getExecutionTime().startsWith("00:00"));

                // Wait for a specific amount of time
                Thread.sleep(2000);  // 2 seconds

                // Check the execution time
                assertTrue(progressMonitor.getExecutionTime().startsWith("00:02"));
        }
}