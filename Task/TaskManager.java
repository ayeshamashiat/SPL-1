import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.PENDING) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }

    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.COMPLETED) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    public Task findTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public void removeTask(int taskId) {
        Task task = findTaskById(taskId);
        if (task != null) {
            tasks.remove(task);
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }
}
