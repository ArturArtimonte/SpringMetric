package com.example.sprinmetric.monitor;

import java.util.concurrent.atomic.AtomicInteger;

public class ProgressMonitor {
        private final AtomicInteger processedLines = new AtomicInteger();
        private final long startTime = System.currentTimeMillis();

        public void incrementProcessedLines() {
                processedLines.incrementAndGet();
        }

        public int getProcessedLines() {
                return processedLines.get();
        }

        public String getExecutionTime() {
                long elapsedMillis = System.currentTimeMillis() - startTime;
                long elapsedSeconds = elapsedMillis / 1000;
                long minutes = elapsedSeconds / 60;
                long seconds = elapsedSeconds % 60;
                return String.format("%02d:%02d", minutes, seconds);
        }
}
