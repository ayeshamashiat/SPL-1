import java.time.LocalDateTime;

public class Task {
    private int taskId;
    private String description;
    private PriorityLevel priority;
    private LocalDateTime deadline;
    private TaskStatus status;
    private boolean isRecurring;
    private TimeInterval recurrenceInterval;

    public Task(int taskId, String description, PriorityLevel priority, LocalDateTime deadline, TaskStatus status, boolean isRecurring, TimeInterval recurrenceInterval) {
        this.taskId = taskId;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
        this.isRecurring = isRecurring;
        this.recurrenceInterval = recurrenceInterval;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void markAsDone() {
        this.status = TaskStatus.COMPLETED;
    }

    public void markAsPending() {
        this.status = TaskStatus.PENDING;
    }

    public void updateTask(String description, PriorityLevel priority, LocalDateTime deadline) {
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    public void setRecurring(TimeInterval interval) {
        this.isRecurring = true;
        this.recurrenceInterval = interval;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public TimeInterval getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId +
                "\nDescription: " + description +
                "\nPriority: " + priority +
                "\nDeadline: " + deadline +
                "\nStatus: " + status +
                "\nRecurring: " + (isRecurring ? "Yes (" + recurrenceInterval + ")" : "No") + "\n";
    }
}
