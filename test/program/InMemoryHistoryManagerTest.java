package program;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import tasks.AbstractTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

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

    @Test
    void taskRemainsInHistoryAfterItIsDeleted() {
        Task task1 = taskManager.addTask(new Task("Задача 1", "Описание"));
        taskManager.getTaskByID(task1.getId());
        assertEquals(task1, taskManager.getHistory().getFirst());
        Task taskHistory = taskManager.getTaskByID(task1.getId());
        taskManager.removeTaskById(task1.getId());
        assertNotNull(taskManager.getHistory());
        assertEquals(taskHistory, taskManager.getHistory().getFirst());
    }

    @Test
    void noDuplicateInTheHistory() {
        Task task1 = taskManager.addTask(new Task("Задача1", "Описание"));
        for (int i = 0; i < 10; i++) {
            historyManager.add(task1);
        }
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void tasksThatAreDuplicatedButNotConsecutiveAreAlsoDeleted() {
        Task task1 = taskManager.addTask(new Task("Задача1", "Описание"));
        Task task2 = taskManager.addTask(new Task("Задача2", "Описание"));
        Task task3 = taskManager.addTask(new Task("Задача3", "Описание"));

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task1);
        historyManager.add(task3);
        historyManager.add(task1);

        List<AbstractTask> history = historyManager.getHistory();
        Task taskSecond = taskManager.getTaskByID(task2.getId());
        assertEquals(taskSecond, history.getFirst(), "Дубликаты не удаляются или удаляются неверно");
        assertEquals(3, history.size(), "Остались дубликаты");
    }
}
