package com.codexp.springmetric.monitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ErrorTracker {
        private final Map<Integer, String> errors = new ConcurrentHashMap<>();

        public void addError(int lineNumber, String error) {
                errors.put(lineNumber, error);
        }

        public Map<Integer, String> getErrors() {
                return new HashMap<>(errors);  // return a copy to prevent external modification
        }

        public void printErrors() {
                System.out.println("Errors (" + errors.size() + "):");
                errors.forEach((line, error) -> System.out.println("Line " + line + ": " + error));
        }
}
