public class Managers {
    private final TaskManager defaultTaskManager = new InMemoryTaskManager();
    private final HistoryManager defaultHistoryManager = new InMemoryHistoryManager();


    public TaskManager getDefaultTaskManager() {
        return defaultTaskManager;
    }

    public HistoryManager getDefaultHistoryManager() {
        return defaultHistoryManager;
    }
}
