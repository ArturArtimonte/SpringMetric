
# SpringMetric Library

Designed to provide real-time monitoring and reporting capabilities for processes, especially when dealing with unsanitized data, denormalized databases, and other intricate data processing tasks.


## Initialization

The **'ExecutionMonitor'** can be initialized using one of its constructors:

#### Default Constructor:

```Java
    public ExecutionMonitor(ExecutionMonitorProperties properties)
```

Initializes the ExecutionMonitor with properties provided by Spring and schedules a task to print progress and errors at regular intervals defined by printIntervalSeconds.

#### Custom Constructor:

```Java
    public ExecutionMonitor(int printIntervalSeconds, ExecutionMonitorProperties properties)
```

This constructor allows for a custom print interval but is currently a placeholder for future implementation.

#### Properties

The ExecutionMonitorProperties class provides configuration options for the ExecutionMonitor. The primary property is printIntervalSeconds, which determines how often the monitor prints progress and error updates.


## Public Methods

#### Increment Lines Processed:

```Java
    public void incrementLinesProcessed()
```

Call this method whenever a line or unit of work has been processed to update the progress count.

#### Add Error:

```Java
    public void addError(int lineNumber, String error)
```

Use this method to log an error message associated with a specific line number or unit of work.(usage with the csv parser, as the error will be in text )

#### Print Progress and Errors:

```Java
    public void printProgressAndErrors()
```

Manually trigger a console print of the current progress and any tracked errors.

#### Close Method (AutoCloseable Interface):

```Java
    @Override
    public void close() throws Exception
```

Shuts down the scheduler used for automatic progress and error printing. Useful when using the try-with-resources statement.

## Usage Example:

To integrate the ExecutionMonitor into your processes:

```Java
    // Define properties with a desired print interval
    ExecutionMonitorProperties properties = new ExecutionMonitorProperties();
    properties.setPrintIntervalSeconds(5);

    // Initialize ExecutionMonitor with properties
    ExecutionMonitor monitor = new ExecutionMonitor(properties);

    // Update progress count as work is done
    monitor.incrementLinesProcessed();

    // Log errors as they occur
    monitor.addError(42, "Error encountered on line 42");

    // The monitor will automatically print progress and errors at the specified interval
```
