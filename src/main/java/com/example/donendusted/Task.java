package com.example.donendusted;

/**
 * Task class extending AbstractTask.
 */
public class Task extends AbstractTask {

    // Constructor with all arguments
    public Task(String description, String priority, String date, String notes, boolean completed) {
        super(description, priority, date, notes, completed);
    }

    // Constructor with optional arguments (defaults to not completed)
    public Task(String description, String priority, String date, String notes) {
        super(description, priority, date, notes);
    }

    // Constructor with minimal arguments
    public Task(String description, String priority) {
        super(description, priority);
    }

    // Implementing the abstract method
    @Override
    public String getDetails() {
        return "Task Details:\n" +
                "Description: " + description + "\n" +
                "Priority: " + priority + "\n" +
                (date != null ? "Due Date: " + date + "\n" : "") +
                (notes != null ? "Notes: " + notes + "\n" : "") +
                "Completed: " + (completed ? "Yes" : "No");
    }
}
