package com.codexp.springmetric.monitor;

import com.codexp.springmetric.config.ExecutionMonitorProperties;
import com.codexp.springmetric.model.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
        private long executionTime = 0;
        private List<TaskInfo> taskInfoList = new ArrayList<>();
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
                        executionTime += (endTime - startTime);

                        long taskExecutionTime = endTime - startTime;
                        long elapsedSeconds = taskExecutionTime / 1000;
                        long minutes = elapsedSeconds / 60;
                        long seconds = elapsedSeconds % 60;
                        String formattedTime = String.format("%02d:%02d", minutes, seconds);

                        TaskInfo currentTaskInfo = new TaskInfo(description, formattedTime);
                        taskInfoList.add(currentTaskInfo);

                        if (printToConsole) {
                                System.out.println(currentTaskInfo.toString());
                        }

                        // TODO: Implement logging and persistency for the execution time and description.
                }
        }

        public List<TaskInfo> getTaskInfoList() {
                return taskInfoList;
        }

        ErrorTracker getErrorTracker() {
                return errorTracker;
        }

        public String getExecutionTime() {
                return progressMonitor.getExecutionTime();
        }

        @Override
        public void close() throws Exception {

                System.out.println("Closing ExecutionMonitor...");
                scheduler.shutdown();
                if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                        scheduler.shutdownNow();
                }
                System.out.println("ExecutionMonitor closed successfully.");
        }
}
