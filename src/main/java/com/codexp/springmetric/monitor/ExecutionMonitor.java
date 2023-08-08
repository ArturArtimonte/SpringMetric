package com.codexp.springmetric.monitor;

import com.codexp.springmetric.config.ExecutionMonitorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExecutionMonitor implements AutoCloseable {
        private final ProgressMonitor progressMonitor = new ProgressMonitor();
        private final ErrorTracker errorTracker = new ErrorTracker();
        private final AtomicInteger processedLines = new AtomicInteger();
        private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        private ExecutionMonitorProperties properties;

        @Autowired
        public ExecutionMonitor(ExecutionMonitorProperties properties) {
                this.properties = properties;
                int printIntervalSeconds = properties.getPrintIntervalSeconds();
                if (printIntervalSeconds > 0) {
                        scheduler.scheduleAtFixedRate(this::printProgressAndErrors, printIntervalSeconds, printIntervalSeconds, TimeUnit.SECONDS);
                }
        }

        public ExecutionMonitor(int printIntervalSeconds, ExecutionMonitorProperties properties) {
        }

        public ExecutionMonitor() {

        }

        public void incrementLinesProcessed() {
                processedLines.incrementAndGet();
                progressMonitor.incrementProcessedLines();
        }


        public void addError(int lineNumber, String error) {
                errorTracker.addError(lineNumber, error);
        }

        public void printProgressAndErrors() {
                progressMonitor.printProgress(1000.0f);
                errorTracker.printErrors();
        }

        public void trackExecution(Runnable task, String description, boolean printToConsole) {
                long startTime = System.currentTimeMillis();
                try {
                        task.run();
                } finally {
                        long endTime = System.currentTimeMillis();
                        long executionTime = endTime - startTime;

                        // If the flag is set, print the execution time to the console.
                        if (printToConsole) {
                                System.out.println(description + " took " + executionTime + "ms.");
                        }

                        // TODO: Implement logging and persistency for the execution time and description.
                }
        }

        ErrorTracker getErrorTracker() {
                return errorTracker;
        }

        @Override
        public void close() throws Exception {
                scheduler.shutdown();
                if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                        scheduler.shutdownNow();
                }
        }
}
