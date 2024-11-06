import java.time.LocalDate;
import java.util.List;


public class Planner {
    public TaskManager taskManager;
    public Scheduler scheduler;
    public Statistics statistics;

    public Planner(){
        this.taskManager = new TaskManager();
        this.scheduler = new Scheduler();
        this.statistics = new Statistics();
    }

    public void addTask(Task task) {
        taskManager.addTask(task);
    }

    public void removeTask(int taskId) {
        taskManager.removeTask(taskId);
    }

    public String getWeeklyStats() {
        return statistics.generateWeeklyStats();
    }

    public int getNextTaskId() {
        return taskManager.getAllTasks().size() + 1; 
    }

    public boolean markTaskAsDone(int taskId) {
        Task task = taskManager.findTaskById(taskId);
        if (task != null) {
            task.markAsDone();
            return true;
        }
        return false;
    }

    public List<Task> getTasksForToday(LocalDate date) {
        return taskManager.getAllTasks();
    }
}
