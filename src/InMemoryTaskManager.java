import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> taskList = new HashMap<>();
    private HashMap<Integer, Epic> epicList = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private HistoryManager historyManager = new InMemoryHistoryManager();
    private int countID = 1;


    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(taskList.values());
    }

    @Override
    public void removeAllTask() {
        taskList.clear();
    }

    @Override
    public Task getTaskByID(int id) {
        historyManager.add(taskList.get(id));
        return taskList.get(id);
    }

    @Override
    public Task addTask(Task task) {
        task.setId(generateCountId());
        taskList.put(task.getId(), task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        taskList.put(task.getId(), task);
    }

    @Override
    public void removeTaskById(int id) {
        taskList.remove(id);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epicList.values());
    }

    @Override
    public void removeAllEpic() {
        epicList.clear();
        subtaskList.clear();
    }

    @Override
    public Epic getEpicByID(int id) {
        historyManager.add(epicList.get(id));
        return epicList.get(id);
    }

    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(generateCountId());
        epicList.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) {

        HashMap<Integer, Subtask> subtasks = epicList.get(epic.getId()).getMapSubtasksEpic();
        epicList.put(epic.getId(), epic);
        epicList.get(epic.getId()).setSubtasksEpic(subtasks);
        checkEpicStatus(epicList.get(epic.getId()));
    }

    @Override
    public void removeEpicById(int id) {
        Epic epicToRemove = epicList.get(id);

        HashMap<Integer, Subtask> subtasksId = epicToRemove.getMapSubtasksEpic();

        for (Subtask subtask : subtasksId.values()) {
            int idToRemove = subtask.getId();
            subtaskList.remove(idToRemove);
        }

        epicList.get(id).getMapSubtasksEpic().clear();
        epicList.remove(id);
    }

    @Override
    public ArrayList<Subtask> getAllSubtask() {
        return new ArrayList<>(subtaskList.values());
    }

    @Override
    public ArrayList<Subtask> getSubtaskByEpic(int epicId) {
        return new ArrayList<>(epicList.get(epicId).getMapSubtasksEpic().values());
    }

    @Override
    public void removeAllSubtask() {
        subtaskList.clear();

        for (Epic epics : epicList.values()) {
            epics.getMapSubtasksEpic().clear();
            checkEpicStatus(epics);
        }
    }

    @Override
    public Subtask getSubtaskByID(int id) {
        historyManager.add(subtaskList.get(id));
        return subtaskList.get(id);
    }

    @Override
    public Subtask addSubtask(Subtask subtask, int epicId) {
        subtask.setId(generateCountId());
        subtaskList.put(subtask.getId(), subtask);
        subtask.setEpicId(epicId);
        epicList.get(epicId).addSubtask(subtask.getId(), subtask);
        checkEpicStatus(epicList.get(epicId));
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        int delEpId = subtaskList.get(subtask.getId()).getEpicId();
        subtaskList.put(subtask.getId(), subtask);
        subtask.setEpicId(delEpId);
        epicList.get(delEpId).addSubtask(subtask.getId(), subtask);
//        subtask.setEpicId(subtaskList.get(subtask.getTaskID()).getEpicId());
        checkEpicStatus(epicList.get(subtask.getEpicId()));
    }

    @Override
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

    public ArrayList <AbstractTask> getHistory() {
        return historyManager.getDefaultHistory();
    }
}
