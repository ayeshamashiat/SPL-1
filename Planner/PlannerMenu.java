import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class PlannerMenu {
    public Planner planner;
    public Scanner scanner;
    public PomodoroClock pomodoroClock;

    public PlannerMenu() {
        this.planner = new Planner();
        this.scanner = new Scanner(System.in);
        this.pomodoroClock = null;
    }

    public void displayMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Planner Menu ---");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Mark Task as Done");
            System.out.println("4. View Tasks for Today");
            System.out.println("5. View Stats");
            System.out.println("6. Pomodoro Sessions");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> promptAddTask();
                case 2 -> promptRemoveTask();
                case 3 -> promptMarkTasksAsDone();
                case 4 -> viewTasksForToday();
                case 5 -> displayWeeklyStatistics();
                case 6 -> displayPomodoroMenu();
                case 7 -> exit = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public static char toLowerCase(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return (char) (ch + 32); 
        }
        return ch; 
    }

    public static char toUpperCase(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 32); 
        }
        return ch; 
    }

    public static int stringLength(String str) {
        if (str == null) {
            return 0;
        }

        int count = 0;
        while (true) {
            try {
                str.charAt(count);
                count++;
            } catch (StringIndexOutOfBoundsException e) {
                break;
            }
        }
        return count;
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }

        if (str1 == null || str2 == null || stringLength(str1) != stringLength(str2)) {
            return false;
        }

        for (int i = 0; i < stringLength(str1); i++) {
            char char1 = toLowerCase(str1.charAt(i));
            char char2 = toLowerCase(str2.charAt(i));
            
            if (char1 != char2) {
                return false;
            }
        }
        return true;
    }

    public void promptAddTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter task priority (LOW, MEDIUM, HIGH): ");
        String priorityInput = scanner.nextLine();
        PriorityLevel priority = parsePriority(priorityInput);

        if (priority == null) {
            System.out.println("Invalid priority level. Please enter LOW, MEDIUM, or HIGH.");
            return;
        }

        System.out.print("Enter task deadline (YYYY-MM-DD): ");
        LocalDate deadline = parseDate(scanner.nextLine());
        if (deadline == null) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            return;
        }

        System.out.print("Recurring status [Yes/No]: ");
        String recurringOrNot = scanner.nextLine();
        boolean recurring = equalsIgnoreCase(recurringOrNot, "Yes");

        TimeInterval interval = null;
        if (recurring) {
            System.out.print("Interval [DAILY/WEEKLY/MONTHLY]: ");
            interval = parseInterval(scanner.nextLine());

            if (interval == null) {
                System.out.println("Invalid interval. Please enter DAILY, WEEKLY, or MONTHLY.");
                return;
            }
        }

        TaskStatus status = TaskStatus.PENDING;
        Task task = new Task(planner.getNextTaskId(), description, priority, deadline.atStartOfDay(), status, recurring, interval);
        planner.addTask(task);
        System.out.println("Task added successfully!");
    }

    private PriorityLevel parsePriority(String input) {
        if (equalsIgnoreCase(input, "LOW")) {
            return PriorityLevel.LOW;
        }
        else if (equalsIgnoreCase(input, "MEDIUM")) {
            return PriorityLevel.MEDIUM;
        }
        else if (equalsIgnoreCase(input, "HIGH")) {
            return PriorityLevel.HIGH;
        }
        return null;
    }

    private LocalDate parseDate(String input) {
        if (stringLength(input) != 10) return null;
        try {
            int year = Integer.parseInt(input.substring(0, 4));
            int month = Integer.parseInt(input.substring(5, 7));
            int day = Integer.parseInt(input.substring(8, 10));
            return LocalDate.of(year, month, day);
        } catch (NumberFormatException e) {
            return null; 
        } catch (DateTimeException e) {
            return null; 
        }
    }

    private TimeInterval parseInterval(String input) {
        if (equalsIgnoreCase(input, "DAILY")) {
            return TimeInterval.DAILY;
        }
        else if (equalsIgnoreCase(input, "WEEKLY")) {
            return TimeInterval.WEEKLY;
        }
        else if (equalsIgnoreCase(input, "MONTHLY")) {
            return TimeInterval.MONTHLY;
        }
        return null;
    }

    public void promptRemoveTask() {
        System.out.print("Enter task ID to remove: ");
        int taskId = scanner.nextInt();
        scanner.nextLine(); 
        planner.removeTask(taskId);
        System.out.println("Task removed successfully!");
    }

    public void promptMarkTasksAsDone() {
        System.out.print("Enter task ID to mark as done: ");
        int taskId = scanner.nextInt();
        scanner.nextLine(); 
        boolean success = planner.markTaskAsDone(taskId);
        if (success) {
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Task not found.");
        }
    }

    public void viewTasksForToday() {
        System.out.println("Tasks for Today:");
        LocalDate today = LocalDate.now();
        boolean tasksFound = false;

        for (Task task : planner.getTasksForToday(today)) {
            System.out.println("ID: " + task.getTaskId() + ", Description: " + task.getDescription());
            tasksFound = true;
        }

        if (!tasksFound) {
            System.out.println("No tasks for today.");
        }
    }

    private void displayPomodoroMenu() {
        if (pomodoroClock == null) {
            configurePomodoroClock();
        }

        boolean backToMain = false;
        while (!backToMain) {
            System.out.println("\n--- Pomodoro Menu ---");
            System.out.println("1. Start Session");
            System.out.println("2. View Session Status");
            System.out.println("3. Reset Pomodoro Cycle");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> startPomodoroSession();
                case 2 -> viewPomodoroStatus();
                case 3 -> resetPomodoroCycle();
                case 4 -> backToMain = true;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public void configurePomodoroClock() {
        System.out.print("Enter session time (minutes): ");
        int sessionTime = scanner.nextInt();
        System.out.print("Enter break time (minutes): ");
        int breakTime = scanner.nextInt();
        System.out.print("Enter total sessions for a cycle: ");
        int totalSessions = scanner.nextInt();
        scanner.nextLine(); 

        pomodoroClock = new PomodoroClock(sessionTime, breakTime, totalSessions);
        System.out.println("Pomodoro clock configured successfully.");
    }

    private void startPomodoroSession() {
        if (pomodoroClock == null) {
            System.out.println("Pomodoro clock not configured. Configure it first.");
        } else {
            if (pomodoroClock.getCurrentState().equals("SESSION") || pomodoroClock.getCurrentState().equals("BREAK")) {
                System.out.println("Session is already running.");
            } else {
                pomodoroClock.startSession();
            }
        }
    }

    private void viewPomodoroStatus() {
        if (pomodoroClock == null) {
            System.out.println("Pomodoro clock not configured. Configure it first.");
        } else {
            System.out.println("Current State: " + pomodoroClock.getCurrentState());
            System.out.println("Time Remaining: " + pomodoroClock.formatTimeRemaining(pomodoroClock.timeRemaining()));
            System.out.println("Sessions Completed: " + pomodoroClock.getSessionsCompleted());
            pomodoroClock.updateTimer();
        }
    }

    private void resetPomodoroCycle() {
        if (pomodoroClock != null) {
            pomodoroClock.reset();
            System.out.println("Pomodoro cycle reset.");
        } else {
            System.out.println("Pomodoro clock not configured.");
        }
    }

    public void displayWeeklyStatistics() {
        String stats = planner.getWeeklyStats();
        System.out.println(stats);
    }
}
