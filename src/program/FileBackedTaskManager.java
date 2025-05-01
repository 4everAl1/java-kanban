package program;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enums.StatusTask;
import exceptions.ManagerSaveException;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import static java.lang.Integer.parseInt;


public class FileBackedTaskManager extends InMemoryTaskManager {


    private static final String TASK_FIELDS = "id,type,name,status,description,epic";
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public FileBackedTaskManager(File file, HashMap<Integer, Task> taskList, HashMap<Integer, Epic> epicList, HashMap<Integer, Subtask> subtaskList, int countId) {
        super(taskList, epicList, subtaskList, countId);
        this.file = file;
    }


    public static FileBackedTaskManager loadFromFile(File file) {
        List<String[]> allTasks = getAllTaskFromFile(file);
        HashMap<Integer, Task> taskMap = new HashMap<>();
        HashMap<Integer, Epic> epicMap = new HashMap<>();
        HashMap<Integer, Subtask> subtaskMap = new HashMap<>();
        StatusTask statusTask;
        int epicById = 0;
        int id = 0;

        for (String[] el : allTasks) {
            String title = el[2];
            String description = el[4];
            id = parseInt(el[0]);
            if (el[1].equals("SUBTASK")) {
                epicById = parseInt(el[5]);
            }
            switch (el[3]) {
                case "NEW" -> statusTask = StatusTask.NEW;
                case "DONE" -> statusTask = StatusTask.DONE;
                case "IN_PROGRESS" -> statusTask = StatusTask.IN_PROGRESS;
                default -> throw new IllegalStateException("Unexpected value: " + el[3]);
            }
            switch (el[1]) {
                case "TASK" -> taskMap.put(id, new Task(title, description, statusTask, id));
                case "EPIC" -> epicMap.put(id, new Epic(title, description, statusTask, id));
                case "SUBTASK" -> {
                    subtaskMap.put(id, new Subtask(title, description, statusTask, id, epicById));
                    epicMap.get(parseInt(el[5])).addSubtask(id, subtaskMap.get(id));
                }
                default -> throw new ManagerSaveException("Unexpected value: " + el[1]);
            }
        }
        return new FileBackedTaskManager(file, taskMap, epicMap, subtaskMap, id + 1);
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
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

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
        List<AbstractTask> allTasks = getAllTasksToList();
        String id;
        String type;
        String title;
        String status;
        String description;
        String epicId = "";
        for (AbstractTask el : allTasks) {
            if (el instanceof Task) {
                type = "TASK";
            } else if (el instanceof Epic) {
                type = "EPIC";
            } else if (el instanceof Subtask) {
                type = "SUBTASK";
                epicId = String.valueOf(((Subtask) el).getEpicId());
            } else {
                type = "";
            }
            title = el.getTitle();
            id = String.valueOf(el.getId());
            status = String.valueOf(el.getStatus());
            description = el.getDescription();

            String[] resArray = {id, type, title, status, description, epicId};
            stringsTasksList.add(resArray);
        }
        return stringsTasksList;
    }

    private List<AbstractTask> getAllTasksToList() {
        List<AbstractTask> allTasks = new ArrayList<>();
        List<Task> tasks = getAllTasks();
        List<Subtask> subtasks = getAllSubtask();
        List<Epic> epics = getAllEpics();

        if (!tasks.isEmpty()) {
            allTasks.addAll(tasks);
        }

        if (!epics.isEmpty()) {
            allTasks.addAll(epics);
        }

        if (!subtasks.isEmpty()) {
            allTasks.addAll(subtasks);
        }
        return allTasks;
    }

    private static List<String[]> getAllTaskFromFile(File file) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            List<String[]> tasks = new ArrayList<>();
            String line = bufferedReader.readLine();

            if (!line.equals(TASK_FIELDS)) {
                throw new ManagerSaveException("Invalid start line");
            }

            while ((line = bufferedReader.readLine()) != null) {
                tasks.add(line.split(","));
            }
            return tasks;
        } catch (IOException e) {
            throw new ManagerSaveException("Error reading");
        }
    }
}
