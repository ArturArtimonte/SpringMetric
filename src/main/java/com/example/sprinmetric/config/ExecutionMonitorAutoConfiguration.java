package com.example.sprinmetric.config;

import com.example.sprinmetric.monitor.ExecutionMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ExecutionMonitorProperties.class)
public class ExecutionMonitorAutoConfiguration {

        @Autowired
        private ExecutionMonitorProperties properties;

        @Bean
        public ExecutionMonitor executionMonitor() {
                return new ExecutionMonitor(properties.getPrintIntervalSeconds(), properties);
        }
}