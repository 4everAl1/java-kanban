package program;

import tasks.AbstractTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Task> getAllTasks();

    void removeAllTask();

    Task getTaskByID(int id);

    Task addTask(Task task);

    void updateTask(Task task);

    void removeTaskById(int id);

    ArrayList<Epic> getAllEpics();

    void removeAllEpic();

    Epic getEpicByID(int id);

    Epic addEpic(Epic epic);

    void updateEpic(Epic epic);

    void removeEpicById(int id);

    ArrayList<Subtask> getAllSubtask();

    ArrayList<Subtask> getSubtaskByEpic(int epicId);

    void removeAllSubtask();

    Subtask getSubtaskByID(int id);

    Subtask addSubtask(Subtask subtask, int epicId);

    void updateSubtask(Subtask subtask);

    void removeSubtaskById(int id);

    List<AbstractTask> getHistory();

}
