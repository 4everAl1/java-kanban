import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private int countID = 1;


    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskList.values());
    }

    public void removeAllTask() {
        taskList.clear();
    }

    public Task getTaskByID(int id) {
        return taskList.get(id);
    }

    public Task addTask(Task task) {
        task.setTaskID(generateCountId());
        taskList.put(task.getTaskID(), task);
        return task;
    }

    public void updateTask(Task task) {
        taskList.put(task.getTaskID(), task);
    }

    public void removeTaskById(int id) {
        taskList.remove(id);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicList.values());
    }

    public void removeAllEpic() {
        epicList.clear();
        subtaskList.clear();

    }

    public Epic getEpicByID(int id) {
        return epicList.get(id);
    }

    public Epic addEpic(Epic epic) {
        epic.setTaskID(generateCountId());
        epicList.put(epic.getTaskID(), epic);
        return epic;
    }

    public void updateEpic(Epic epic) {

        HashMap<Integer, Subtask> subtasks = epicList.get(epic.getEpicId()).getMapSubtasksEpic();
        epicList.put(epic.getEpicId(), epic);
        epicList.get(epic.getEpicId()).setSubtasksEpic(subtasks);
        checkEpicStatus(epicList.get(epic.getEpicId()));
    }

    public void removeEpicById(int id) {
        Epic epicToRemove = epicList.get(id);

        HashMap<Integer, Subtask> subtasksId = epicToRemove.getMapSubtasksEpic();

        for (Subtask subtask : subtasksId.values()) {
            int idToRemove = subtask.getTaskID();
            subtaskList.remove(idToRemove);
        }

        epicList.get(id).getMapSubtasksEpic().clear();
        epicList.remove(id);
    }

    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subtaskList.values());
    }

    public ArrayList<Subtask> getSubtaskByEpic(int epicId) {
        return new ArrayList<>(epicList.get(epicId).getMapSubtasksEpic().values());
    }

    public void removeAllSubtask() {
        subtaskList.clear();

        for (Epic epics : epicList.values()) {
            epics.getMapSubtasksEpic().clear();
            checkEpicStatus(epics);
        }
    }

    public Subtask getSubtaskByID(int id) {
        return subtaskList.get(id);
    }

    public Subtask addSubtask(Subtask subtask, int epicId) {
        subtask.setSubtaskId(generateCountId());
        subtaskList.put(subtask.getTaskID(), subtask);
        subtask.setEpicId(epicId);
        epicList.get(epicId).addSubtask(subtask.getTaskID(), subtask);
        checkEpicStatus(epicList.get(epicId));
        return subtask;
    }

    public void updateSubtask(Subtask subtask) {
        int delEpId = subtaskList.get(subtask.getTaskID()).getEpicId();
        subtaskList.put(subtask.getTaskID(), subtask);
        subtask.setEpicId(delEpId);
        epicList.get(delEpId).addSubtask(subtask.getTaskID(), subtask);
//        subtask.setEpicId(subtaskList.get(subtask.getTaskID()).getEpicId());
        checkEpicStatus(epicList.get(subtask.getEpicId()));
    }

    public void removeSubtaskById(int id) {
        int delId = subtaskList.get(id).getEpicId();
        epicList.get(subtaskList.get(id).getEpicId()).getMapSubtasksEpic().remove(id);
        subtaskList.remove(id);
        checkEpicStatus(epicList.get(delId));
    }

    private void checkEpicStatus(Epic epic) {
        boolean allDone = true;
        boolean allNew = true;

        if (epic.getMapSubtasksEpic().isEmpty()) {
            epic.setStatus(StatusTask.NEW);
            return;
        }

        for (Subtask subtasks : epic.getMapSubtasksEpic().values()) {
            if (subtasks.getStatus() != StatusTask.NEW) {
                allNew = false;
            }
            if (subtasks.getStatus() != StatusTask.DONE) {
                allDone = false;
            }
        }

        if (allDone) {
            epic.setStatus(StatusTask.DONE);
        } else if (allNew) {
            epic.setStatus(StatusTask.NEW);
        } else {
            epic.setStatus(StatusTask.IN_PROGRESS);
        }
    }

    private int generateCountId() {
        return countID++;
    }

}
