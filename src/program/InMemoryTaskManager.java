package program;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import enums.StatusTask;
import exceptions.ManagerSaveException;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> taskList;
    private final Map<Integer, Epic> epicList;
    private final Map<Integer, Subtask> subtaskList;
    private final HistoryManager historyManager;
    private int countID;
    private final Set<AbstractTask> prioritizedTasksList;

    public InMemoryTaskManager() {
        this.taskList = new HashMap<>();
        this.epicList = new HashMap<>();
        this.subtaskList = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
        this.countID = 1;
        this.prioritizedTasksList = new TreeSet<>((a, b) -> {
            if (a.getStartTime() == null || b.getStartTime() == null) {
                throw new IllegalStateException("The value is null");
            }
            return a.getStartTime().compareTo(b.getStartTime());
        });
    }

    public InMemoryTaskManager(HashMap<Integer, Task> taskList,
                               HashMap<Integer, Epic> epicList,
                               HashMap<Integer, Subtask> subtaskList,
                               int countId,
                               TreeSet<AbstractTask> prioritizedTasksList) {
        this.taskList = taskList;
        this.epicList = epicList;
        this.subtaskList = subtaskList;
        this.historyManager = Managers.getDefaultHistory();
        this.countID = countId;
        this.prioritizedTasksList = prioritizedTasksList;
    }


    @Override
    public List<Task> getAllTasks() {
        return List.copyOf(taskList.values());
    }

    @Override
    public void removeAllTask() {
        prioritizedTasksList.removeIf(t -> t instanceof Task);
        taskList.clear();
    }

    @Override
    public Task getTaskByID(int id) {
        historyManager.add(taskList.get(id));
        return taskList.get(id);
    }

    @Override
    public Task addTask(Task task) {
        if (checkTimeFrame(task)) {
            throw new ManagerSaveException("Invalid time");
        }
        task.setId(generateCountId());
        taskList.put(task.getId(), task);
        addInPrioritizedList(task);
        return task;
    }

    @Override
    public void updateTask(Task task) {
        if (!taskList.containsKey(task.getId())) {
            throw new NoSuchElementException("Key " + task.getId() + " not found.");
        }
        if (checkTimeFrame(task)) {
            throw new ManagerSaveException("Invalid time");
        }
        removeFromPrioritizedList(task);
        addInPrioritizedList(task);
        taskList.put(task.getId(), task);
    }

    @Override
    public void removeTaskById(int id) {
        removeFromPrioritizedList(taskList.get(id));
        taskList.remove(id);
    }

    @Override
    public List<Epic> getAllEpics() {
        return List.copyOf(epicList.values());
    }

    @Override
    public void removeAllEpic() {
        prioritizedTasksList.removeIf(t -> t instanceof Subtask);
        epicList.clear();
        subtaskList.clear();
    }

    @Override
    public Epic getEpicById(int id) {
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
        subtaskList.entrySet().removeIf(s -> s.getValue().getEpicId() == id);
        prioritizedTasksList.removeIf(s -> s instanceof Subtask && ((Subtask) s).getEpicId() == id);
        epicList.get(id).getMapSubtasksEpic().clear();
        epicList.remove(id);
    }

    @Override
    public List<Subtask> getAllSubtask() {
        return List.copyOf(subtaskList.values());
    }

    @Override
    public List<Subtask> getSubtaskByEpic(int epicId) {
        return List.copyOf(epicList.get(epicId).getMapSubtasksEpic().values());
    }

    @Override
    public void removeAllSubtask() {
        prioritizedTasksList.removeIf(t -> t instanceof Subtask);
        subtaskList.clear();
        epicList.values().forEach(e -> {
            e.getMapSubtasksEpic().clear();
            startTimeEpic(e);
            endTimeEpic(e);
            checkEpicTotalTime(e);
            checkEpicStatus(e);
        });
    }

    @Override
    public Subtask getSubtaskByID(int id) {
        historyManager.add(subtaskList.get(id));
        return subtaskList.get(id);
    }

    @Override
    public Subtask addSubtask(Subtask subtask, int epicId) {
        if (!epicList.containsKey(epicId)) {
            throw new NoSuchElementException("Key " + epicId + " not found.");
        }
        if (checkTimeFrame(subtask)) {
            throw new ManagerSaveException("Invalid time");
        }
        subtask.setId(generateCountId());
        subtaskList.put(subtask.getId(), subtask);
        subtask.setEpicId(epicId);
        epicList.get(epicId).addSubtask(subtask.getId(), subtask);
        addInPrioritizedList(subtask);
        startTimeEpic(epicList.get(epicId));
        endTimeEpic(epicList.get(epicId));
        checkEpicTotalTime(epicList.get(epicId));
        checkEpicStatus(epicList.get(epicId));
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (!epicList.containsKey(subtask.getEpicId())) {
            throw new NoSuchElementException("Key " + subtask.getEpicId() + " not found.");
        }
        subtaskList.put(subtask.getId(), subtask);
        epicList.get(subtask.getEpicId()).addSubtask(subtask.getId(), subtask);
        removeFromPrioritizedList(subtask);
        addInPrioritizedList(subtask);
        startTimeEpic(epicList.get(subtask.getEpicId()));
        endTimeEpic(epicList.get(subtask.getEpicId()));
        checkEpicTotalTime(epicList.get(subtask.getEpicId()));
        checkEpicStatus(epicList.get(subtask.getEpicId()));
    }

    @Override
    public void removeSubtaskById(int id) {
        Subtask removeSubtask = subtaskList.get(id);
        removeFromPrioritizedList(subtaskList.get(id));
        epicList.get(removeSubtask.getEpicId()).getMapSubtasksEpic().remove(id);
        subtaskList.remove(id);
        startTimeEpic(epicList.get(removeSubtask.getEpicId()));
        endTimeEpic(epicList.get(removeSubtask.getEpicId()));
        checkEpicTotalTime(epicList.get(removeSubtask.getEpicId()));
        checkEpicStatus(epicList.get(removeSubtask.getEpicId()));
    }

    public List<AbstractTask> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<AbstractTask> getPrioritizedTasks() {
        return List.copyOf(prioritizedTasksList);
    }

    private void addInPrioritizedList(AbstractTask task) {
        if (task.getStartTime() != null) {
            prioritizedTasksList.add(task);
        }
    }

    private void removeFromPrioritizedList(AbstractTask task) {
        if (task.getStartTime() != null) {
            prioritizedTasksList.removeIf(t -> t.getId() == task.getId());
        }
    }

    private void checkEpicTotalTime(Epic epic) {
        Duration duration = prioritizedTasksList
                .stream()
                .filter(s -> s instanceof Subtask)
                .filter(s -> ((Subtask) s).getEpicId() == epic.getId())
                .map(s -> Duration.ofMinutes(s.getDuration()))
                .reduce(Duration.ZERO, Duration::plus);
        epic.setDuration((int) duration.toMinutes());
    }

    private void startTimeEpic(Epic epic) {
        Optional<LocalDateTime> startTime = prioritizedTasksList.stream()
                .filter(s -> s instanceof Subtask)
                .map(s -> (Subtask) s)
                .filter(s -> s.getEpicId() == epic.getId())
                .map(Subtask::getStartTime)
                .min(LocalDateTime::compareTo);
        epic.setStartTime(startTime.orElse(null));
    }

    private void endTimeEpic(Epic epic) {
        Optional<LocalDateTime> endTime = prioritizedTasksList.stream()
                .filter(s -> s instanceof Subtask)
                .map(s -> (Subtask) s)
                .filter(s -> s.getEpicId() == epic.getId())
                .map(Subtask::getGetEndTime)
                .max(LocalDateTime::compareTo);
        epic.setEndTime(endTime.orElse(null));
    }

    private boolean checkTimeFrame(AbstractTask abstractTask) {
        if (abstractTask.getStartTime() == null || abstractTask.getGetEndTime() == null) {
            return false;
        }

        Optional<AbstractTask> checkTime = prioritizedTasksList.stream()
                .filter(s -> !(abstractTask.getStartTime().isAfter(s.getGetEndTime()) ||
                        abstractTask.getGetEndTime().isBefore(s.getStartTime())))
                .findAny();

        return checkTime.isPresent();
    }

    private void checkEpicStatus(Epic epic) {
        Collection<Subtask> subtasks = epic.getMapSubtasksEpic().values();

        if (epic.getMapSubtasksEpic().isEmpty()) {
            epic.setStatus(StatusTask.NEW);
            return;
        }

        boolean allDone = subtasks.stream().allMatch(s -> s.getStatus() == StatusTask.DONE);
        boolean allNew = subtasks.stream().allMatch(s -> s.getStatus() == StatusTask.NEW);

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
