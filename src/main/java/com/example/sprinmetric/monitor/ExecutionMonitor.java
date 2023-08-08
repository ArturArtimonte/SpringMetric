package com.example.sprinmetric.monitor;

import com.example.sprinmetric.config.ExecutionMonitorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ExecutionMonitor implements AutoCloseable {
        private final ProgressMonitor progressMonitor = new ProgressMonitor();
        private final ErrorTracker errorTracker = new ErrorTracker();
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

        public void incrementProcessedLines() {
                progressMonitor.incrementProcessedLines();
        }

        public void addError(int lineNumber, String error) {
                errorTracker.addError(lineNumber, error);
        }

        public void printProgressAndErrors() {
                int processedLines = progressMonitor.getProcessedLines();
                // Assuming totalLines is known or can be fetched. Placeholder value used here.Todo Fetch the total lines
                float totalLines = 1000.0f;
                progressMonitor.printProgress(processedLines, totalLines);
                errorTracker.printErrors();
        }

        @Override
        public void close() throws Exception {
                scheduler.shutdown();
                if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                        scheduler.shutdownNow();
                }
        }
}
