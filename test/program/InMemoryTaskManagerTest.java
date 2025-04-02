package program;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
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
    void comparisonOfIdForSimilarityTask() {
        Task task1 = taskManager.addTask(new Task("Задача1", "Описание"));
        Task taskUpdate = new Task("Обновлённая задача", "С новым описанием",
                StatusTask.IN_PROGRESS, task1.getId());
        assertEquals(task1, taskUpdate);
    }

    @Test
    void addNewTask() {
        Task task = taskManager.addTask(new Task("Задача1", "Описание"));
        final int taskId = task.getId();

        final Task savedTask = taskManager.getTaskByID(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addNewEpic() {
        Epic epic = taskManager.addEpic(new Epic("Эпик1", "Описание"));
        final int epicId = epic.getId();

        final Epic savedEpic = taskManager.getEpicByID(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        final List<Epic> epics = taskManager.getAllEpics();

        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    void addTaskInHistory() {
        Task task = taskManager.addTask(new Task("Задача1", "Описание"));
        historyManager.add(task);
        final List<AbstractTask> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void noDuplicateInTheHistory() {
        Task task1 = taskManager.addTask(new Task("Задача1", "Описание"));
        historyManager.add(task1);
        historyManager.add(task1);
        historyManager.add(task1);
        historyManager.add(task1);
        historyManager.add(task1);
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
    void whenUpdatingATaskTheOldVersionIsDeletedAsADuplicate() {
        Task task = taskManager.addTask(new Task("Задача1", "Описание"));
        taskManager.getTaskByID(task.getId());
        final ArrayList<AbstractTask> history = new ArrayList<>();
        history.add(task);
        assertEquals(1, taskManager.getHistory().size());
        assertEquals(history, taskManager.getHistory());

        taskManager.updateTask(new Task("Тот же таск", "Должна сохраниться прошлая версия",
                StatusTask.IN_PROGRESS, task.getId()));
        taskManager.getTaskByID(task.getId());
        final List<AbstractTask> newHistory = new ArrayList<>();
        newHistory.add(task);
        assertEquals(1, taskManager.getHistory().size(), "Дубликат не удалён");
        assertEquals(newHistory, taskManager.getHistory(), "Дубликат не удалён");
    }

    @Test
    void noMoreIrrelevantIdLeftInTheEpic() {
        Epic epic = taskManager.addEpic(new Epic("Эпик1", "Описание"));
        Subtask subtask = taskManager.addSubtask(new Subtask("Сабтаск1", "Статус NEW"), epic.getId());
        Subtask subtask1 = taskManager.getSubtaskByID(subtask.getId());
        assertEquals(subtask1, taskManager.getSubtaskByEpic(epic.getId()).getFirst());
        taskManager.removeSubtaskById(subtask.getId());
        Map<Integer, Subtask> emptyMap = new HashMap<>();
        assertEquals(emptyMap, epic.getMapSubtasksEpic());
    }

    @Test
    void statusEpicWhenUpdateStatusSubtask() {
        Epic epic = taskManager.addEpic(new Epic("Эпик1", "Описание"));
        Subtask subtask = taskManager.addSubtask(new Subtask("Сабтаск1", "Статус NEW"), epic.getId());
        assertEquals(StatusTask.NEW, epic.getStatus(), "Неверный статус");

        taskManager.updateSubtask(new Subtask("Тот же первый сабтаск",
                "Статус IN_PROGRESS", StatusTask.IN_PROGRESS, subtask.getId()));
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatus(), "Неверный статус эпика");
    }

    @Test
    void emptyEpicsAfterRemoveAllSubtasks() {
        Epic epic = taskManager.addEpic(new Epic("Эпик1", "Описание"));
        Subtask subtask = taskManager.addSubtask(new Subtask("Сабтаск1", "Описание"), epic.getId());
        Subtask subtask1 = taskManager.addSubtask(new Subtask("Сабтаск2", "Описание"), epic.getId());
        assertNotNull(epic.getMapSubtasksEpic());
        taskManager.removeAllSubtask();
        HashMap<Integer, Subtask> emptyMap = new HashMap<>();
        assertEquals(emptyMap, epic.getMapSubtasksEpic());
    }

    @Test
    void changeEpicStatusAfterRemoveSubtask() {
        Epic epic = taskManager.addEpic(new Epic("Эпик1", "Описание"));
        Subtask subtask = taskManager.addSubtask(new Subtask("Сабтаск1",
                "Сабтаск со статусом NEW"), epic.getId());
        assertEquals(StatusTask.NEW, epic.getStatus());
        taskManager.updateSubtask(new Subtask("Тот же сабтаск", "Со статусом IN_PROGRESS",
                StatusTask.IN_PROGRESS, subtask.getId()));
        assertEquals(StatusTask.IN_PROGRESS, epic.getStatus());
        taskManager.removeSubtaskById(subtask.getId());
        assertEquals(StatusTask.NEW, epic.getStatus());
    }
}