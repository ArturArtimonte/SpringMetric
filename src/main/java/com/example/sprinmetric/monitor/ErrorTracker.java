package com.example.sprinmetric.monitor;

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
}