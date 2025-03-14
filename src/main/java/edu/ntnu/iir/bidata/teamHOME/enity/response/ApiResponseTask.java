package edu.ntnu.iir.bidata.teamHOME.enity.response;

/**
 * Response object for a task.
 */
public class ApiResponseTask {
    private String message;
    private Task task;
    
    public ApiResponseTask(String message, Task task) {
        this.message = message;
        this.task = task;
    }

    public String getMessage() {
        return message;
    }

    public Task getTask() {
        return task;
    }
}
