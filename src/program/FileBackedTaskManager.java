package program;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import enums.StatusTask;
import exceptions.ManagerSaveException;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


public class FileBackedTaskManager extends InMemoryTaskManager {


    private static final String TASK_FIELDS = "id,type,name,status,description,epic,startTime,duration,endTimeForEpic";
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public FileBackedTaskManager(File file,
                                 HashMap<Integer, Task> taskList,
                                 HashMap<Integer, Epic> epicList,
                                 HashMap<Integer, Subtask> subtaskList,
                                 int countId, TreeSet<AbstractTask> prioritizedTasksList) {
        super(taskList, epicList, subtaskList, countId, prioritizedTasksList);
        this.file = file;
    }


    public static FileBackedTaskManager loadFromFile(File file) {
        HashMap<Integer, Task> taskMap = new HashMap<>();
        HashMap<Integer, Epic> epicMap = new HashMap<>();
        HashMap<Integer, Subtask> subtaskMap = new HashMap<>();
        TreeSet<AbstractTask> prioritizedTasksList = new TreeSet<>(Comparator.comparing(AbstractTask::getStartTime));

        int id = 0;
        for (String[] el : getAllTaskFromFile(file)) {
            id = Integer.parseInt(el[0]);
            String type = el[1];
            String title = el[2];
            StatusTask status = StatusTask.valueOf(el[3]);
            String description = el[4];
            int epicId = el[5].isBlank() ? 0 : Integer.parseInt(el[5]);
            LocalDateTime startTime = el[6].isBlank() ? null : LocalDateTime.parse(el[6]);
            Duration duration = el[7].isBlank() ? null : Duration.parse(el[7]);
            LocalDateTime endTimeForEpic = el[8].isBlank() ? null : LocalDateTime.parse(el[8]);

            switch (type) {
                case "TASK" -> {
                    taskMap.put(id, new Task(title, description, status, id, startTime, duration));
                    if (startTime != null) {
                        prioritizedTasksList.add(taskMap.get(id));
                    }
                }
                case "EPIC" -> epicMap.put(id, new Epic(title, description, status, id,
                        startTime, duration, endTimeForEpic));
                case "SUBTASK" -> {
                    subtaskMap.put(id, new Subtask(title, description, status, id, epicId,
                            startTime, duration));
                    if (startTime != null) {
                        prioritizedTasksList.add(subtaskMap.get(id));
                    }
                    epicMap.get(epicId).addSubtask(id, subtaskMap.get(id));
                }
                default -> throw new ManagerSaveException("Unexpected value: " + el[1]);
            }
        }
        return new FileBackedTaskManager(file, taskMap, epicMap, subtaskMap, id + 1, prioritizedTasksList);
    }

    @Override
    public Task addTask(Task task) {
        Task addedTask = super.addTask(task);
        save();
        return addedTask;
    }

    @Override
    public Subtask addSubtask(Subtask subtask, int epicId) {
        Subtask addedSubtask = super.addSubtask(subtask, epicId);
        save();
        return addedSubtask;
    }

    @Override
    public Epic addEpic(Epic epic) {
        Epic addedEpic = super.addEpic(epic);
        save();
        return addedEpic;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeAllEpic() {
        super.removeAllEpic();
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeAllSubtask() {
        super.removeAllSubtask();
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    private void save() {
        List<String[]> tasks = getAllTasksToString();
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write(TASK_FIELDS);

            for (String[] el : tasks) {
                String task = String.join(",", el);
                writer.newLine();
                writer.write(task);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("ERROR");
        }
    }

    private List<String[]> getAllTasksToString() {
        List<String[]> stringsTasksList = new ArrayList<>();
        for (AbstractTask el : getAllTasksToList()) {
            String id = String.valueOf(el.getId());
            String type;
            String title = el.getTitle();
            String description = el.getDescription();
            String status = el.getStatus().toString();
            String epicId = "";
            String startTime = el.getStartTime() != null ? el.getStartTime().toString() : "";
            String duration = el.getStartTime() != null ? el.getDuration().toString() : "";
            String epicEndTime = "";

            switch (el) {
                case Task task -> type = "TASK";
                case Epic epic -> {
                    type = "EPIC";
                    epicEndTime = epic.getEndTime() != null ? epic.getEndTime().toString() : "";
                }
                case Subtask subtask -> {
                    type = "SUBTASK";
                    epicId = String.valueOf(subtask.getEpicId());
                }
                default -> throw new ManagerSaveException("Unknown type");
            }
            stringsTasksList.add(new String[]{id, type, title, status,
                    description, epicId, startTime, duration, epicEndTime});
        }
        return stringsTasksList;
    }

    private List<AbstractTask> getAllTasksToList() {
        return Stream.of(
                getAllTasks(),
                getAllEpics(),
                getAllSubtask()
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private static List<String[]> getAllTaskFromFile(File file) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            List<String[]> tasks = new ArrayList<>();
            String line = bufferedReader.readLine();

            if (!line.equals(TASK_FIELDS)) {
                throw new ManagerSaveException("Invalid start line");
            }

            while ((line = bufferedReader.readLine()) != null) {
                tasks.add(line.split(",", -1));
            }
            return tasks;
        } catch (IOException e) {
            throw new ManagerSaveException("Error reading");
        }
    }
}
