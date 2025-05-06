package program;

import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    TaskManager taskManager = new InMemoryTaskManager();
    HistoryManager historyManager = new InMemoryHistoryManager();

    @BeforeEach
    void newTaskManager() {
        taskManager = new InMemoryTaskManager();
    }

    @BeforeEach
    void newHistoryManager() {
        historyManager = new InMemoryHistoryManager();
    }

}