package com.example.sprinmetric.config;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "execution.monitor")
public class ExecutionMonitorProperties {
        @Min(1)
        private int printIntervalSeconds = 30;

        // getters and setters
        public int getPrintIntervalSeconds() {
                return printIntervalSeconds;
        }

        public void setPrintIntervalSeconds(int printIntervalSeconds) {
                this.printIntervalSeconds = printIntervalSeconds;
        }
}
