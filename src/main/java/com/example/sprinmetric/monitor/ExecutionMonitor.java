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

        private final ExecutionMonitorProperties properties;

        @Autowired
        public ExecutionMonitor(ExecutionMonitorProperties properties) {
                this.properties = properties;
                int printIntervalSeconds = properties.getPrintIntervalSeconds();
                if (printIntervalSeconds > 0) {
                        scheduler.scheduleAtFixedRate(this::printProgress, printIntervalSeconds, printIntervalSeconds, TimeUnit.SECONDS);
                }
        }

        public ExecutionMonitor(int printIntervalSeconds, ExecutionMonitorProperties properties) {
                this.properties = properties;
        }

        public void incrementProcessedLines() {
                progressMonitor.incrementProcessedLines();
        }

        public void addError(int lineNumber, String error) {
                errorTracker.addError(lineNumber, error);
        }

        public void printProgress() {
                // TODO print
        }

        @Override
        public void close() throws Exception {
                scheduler.shutdown();
                // TODO shutdown logic
        }
}

