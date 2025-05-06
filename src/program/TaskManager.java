package program;

import tasks.AbstractTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getAllTasks();

    void removeAllTask();

    Task getTaskByID(int id);

    Task addTask(Task task);

    void updateTask(Task task);

    void removeTaskById(int id);

    List<Epic> getAllEpics();

    void removeAllEpic();

    Epic getEpicById(int id);

    Epic addEpic(Epic epic);

    void updateEpic(Epic epic);

    void removeEpicById(int id);

    List<Subtask> getAllSubtask();

    List<Subtask> getSubtaskByEpic(int epicId);

    void removeAllSubtask();

    Subtask getSubtaskByID(int id);

    Subtask addSubtask(Subtask subtask, int epicId);

    void updateSubtask(Subtask subtask);

    void removeSubtaskById(int id);

    List<AbstractTask> getHistory();

    List<AbstractTask> getPrioritizedTasks();

}
