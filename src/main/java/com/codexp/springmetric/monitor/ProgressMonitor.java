package com.codexp.springmetric.monitor;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressMonitor {
        private final AtomicInteger processedLines = new AtomicInteger();
        private final long startTime = System.currentTimeMillis();

        public void incrementProcessedLines() {
                processedLines.incrementAndGet();
        }

        public void printProgress(float totalLines) {
                float percentage = (processedLines.get() / totalLines) * 100;
                String lineSeparator = System.lineSeparator();
                System.out.printf(Locale.US, "Progress: %d lines processed out of %.0f (%.2f%%)%s", processedLines.get(), totalLines, percentage, lineSeparator);
        }

        public String getExecutionTime() {
                long elapsedMillis = System.currentTimeMillis() - startTime;
                long elapsedSeconds = elapsedMillis / 1000;
                long minutes = elapsedSeconds / 60;
                long seconds = elapsedSeconds % 60;
                return String.format("%02d:%02d", minutes, seconds);
        }
}
