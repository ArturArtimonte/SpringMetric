package com.codexp.springmetric.model;

public class TaskInfo {
        private String description;
        private String executionTime;

        public TaskInfo(String description, String executionTime) {
                this.description = description;
                this.executionTime = executionTime;
        }

        public String getDescription() {
                return description;
        }

        public String getExecutionTime() {
                return executionTime;
        }

        @Override
        public String toString() {
                return description + " took " + executionTime;
        }
}

