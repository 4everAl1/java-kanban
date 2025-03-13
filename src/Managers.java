public class Managers {
    private static final TaskManager defaultTaskManager = new InMemoryTaskManager();
    private static final HistoryManager defaultHistoryManager = new InMemoryHistoryManager();


    public static TaskManager getDefaultTaskManager() {
        return defaultTaskManager;
    }

    public static HistoryManager getDefaultHistoryManager() {
        return defaultHistoryManager;
    }
}
