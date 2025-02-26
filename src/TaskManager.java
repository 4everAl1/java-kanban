import java.util.HashMap;

public class TaskManager {

    HashMap<Integer, Task> taskList = new HashMap<>();
    HashMap<Integer, Epic> epicList = new HashMap<>();
    HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    private int countID = 1;

    public HashMap<Integer, Task> showAllTasks() {
        return taskList;
    }

    public void removeAllTask() {
        taskList.clear();
    }

    public Task showTaskByID(int id) {
        return taskList.get(id);
    }

    public Task newTask(Task task) {
        task.setTaskID(countID);
        taskList.put(countID, task);
        countID++;
        return task;
    }

    public void updateTask(int id, Task task) {
        task.setTaskID(id);
        taskList.put(id, task);
    }

    public void removeTaskById(int id) {
        taskList.remove(id);
    }

    public HashMap<Integer, Epic> showAllEpics() {
        for (Epic epics : epicList.values()) {
            checkEpicStatus(epics);
        }
        return epicList;
    }

    public void removeAllEpic() {
        epicList.clear();
        subtaskList.clear();
    }

    public Epic showEpicByID(int id) {
        return epicList.get(id);
    }

    public Epic newEpic(Epic epic) {
        epic.setTaskID(countID);
        epicList.put(countID, epic);
        countID++;
        return epic;
    }

    public void updateEpic(int id, Epic epic) {
        HashMap <Integer, Subtask> subtasks = epicList.get(id).subtasksEpic;
        epic.setTaskID(id);
        epicList.put(id, epic);
        epicList.get(id).subtasksEpic = subtasks;
        checkEpicStatus(epicList.get(id));
    }

    public void removeEpicById(int id) {
        epicList.get(id).subtasksEpic.clear();
        epicList.remove(id);
    }

    public HashMap<Integer, Subtask> showAllSubtask() {
        return subtaskList;
    }

    public HashMap<Integer, Subtask> showSubtaskByEpic(int epicId) {
        return epicList.get(epicId).getSubtasksEpic();
    }

    public void removeAllSubtask() {
        subtaskList.clear();

        for (Epic epics : epicList.values()) {
            epics.subtasksEpic.clear();
            checkEpicStatus(epics);
        }
    }

    public Subtask showSubtaskByID(int id) {
        return subtaskList.get(id);
    }

    public Subtask newSubtask(Subtask subtask, int epicId) {
        subtask.setTaskID(countID);
        subtaskList.put(countID, subtask);
        subtask.setEpicId(epicId);
        epicList.get(epicId).addSubtask(countID,subtask);
        checkEpicStatus(epicList.get(epicId));
        countID++;
        return subtask;
    }

    public void updateSubtask(int subId, Subtask subtask) {
        int delEpId = subtaskList.get(subId).getEpicId();
        subtask.setTaskID(subId);
        subtaskList.put(subId, subtask);
        subtask.setEpicId(delEpId);
        epicList.get(delEpId).addSubtask(subId,subtask);
        subtask.setEpicId(subtaskList.get(subId).getEpicId());
        checkEpicStatus(epicList.get(subtask.getEpicId()));
    }

    public void removeSubtaskById(int id) {
        int delId = subtaskList.get(id).getEpicId();
        epicList.get(subtaskList.get(id).getEpicId()).subtasksEpic.remove(id);
        subtaskList.remove(id);
        checkEpicStatus(epicList.get(delId));
    }

    private void checkEpicStatus(Epic epic) {
        boolean allDone = true;
        boolean allNew = true;

        if (epic.subtasksEpic.isEmpty()) {
            epic.setStatus(StatusTask.NEW);
            return;
        }

        for (Subtask subtasks : epic.subtasksEpic.values()) {
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



}
