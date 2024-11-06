public class PomodoroClock{
    private int sessionTime;
    private int breakTime;
    private int totalSessions;
    private int sessionsCompleted;
    private String currentState;
    private long startTime;
    private long endTime;

    public PomodoroClock(int sessionTime, int breakTime, int totalSessions) {
        this.sessionTime = sessionTime;
        this.breakTime = breakTime;
        this.totalSessions = totalSessions;
        this.sessionsCompleted = 0;
        this.currentState = "SESSION";
        this.startTime = 0;
        this.endTime = 0;
    }

    public void startSession() {
        currentState = "SESSION";
        startTime = currentMillis();
        endTime = startTime + sessionTime * 60 * 1000;
        System.out.println("Session started. Time remaining: " + formatTimeRemaining(timeRemaining()));
    }

    public void startBreak() {
        currentState = "BREAK";
        startTime = currentMillis();
        endTime = startTime + breakTime * 60 * 1000;
        System.out.println("Break started. Time remaining: " + formatTimeRemaining(timeRemaining()));
    }

    public long timeRemaining() {
        long remaining = endTime - currentMillis();
        return remaining < 0 ? 0 : remaining;
    }


    public String formatTimeRemaining(long millis) {
        long totalSeconds = millis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + " minutes, " + seconds + " seconds";
    }
    public void updateTimer() {
        if (timeRemaining() == 0) {
            if (currentState.equals("SESSION")) {
                sessionsCompleted++;
                if (sessionsCompleted < totalSessions) {
                    startBreak();
                } else {
                    startBreak();
                }
            } else if (currentState.equals("BREAK")) {
                if (sessionsCompleted < totalSessions) {
                    startSession();
                } else {
                    currentState = "CYCLE_COMPLETED";
                    System.out.println("Pomodoro cycle completed.");
                }
            }
        }
    }


    public void reset() {
        sessionsCompleted = 0;
        currentState = "SESSION";
        startTime = 0;
        endTime = 0;
        System.out.println("Pomodoro reset.");
    }

    private long currentMillis() {
        return System.currentTimeMillis();
    }

    public void setEndTime(long millis) {
        this.endTime = millis;
    }

    public int getSessionsCompleted() {
        return sessionsCompleted;
    }

    public String getCurrentState() {
        return currentState;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
