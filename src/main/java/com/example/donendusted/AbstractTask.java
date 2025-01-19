package com.example.donendusted;

/**
 * Abstract base class for tasks.
 */
public abstract class AbstractTask {
    protected String description;
    protected String priority;
    protected String date;  // Optional: null if not set
    protected String notes; // Optional: null if not set
    protected boolean completed;

    // Constructor with all arguments
    public AbstractTask(String description, String priority, String date, String notes, boolean completed) {
        if (description == null || priority == null) {
            throw new IllegalArgumentException("Description and Priority cannot be null.");
        }
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.notes = notes;
        this.completed = completed;
    }

    // Constructor with optional arguments (defaults to not completed)
    public AbstractTask(String description, String priority, String date, String notes) {
        this(description, priority, date, notes, false);
    }

    // Constructor with minimal arguments
    public AbstractTask(String description, String priority) {
        this(description, priority, null, null, false);
    }

    // Abstract method for task-specific details
    public abstract String getDetails();

    // Common methods (can be overridden if necessary)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(description);
        builder.append(" (").append(priority);
        if (date != null && !date.isEmpty()) {
            builder.append(", Due: ").append(date);
        }
        builder.append(")");
        return builder.toString();
    }
}

