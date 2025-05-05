package program;

import enums.StatusTask;
import exceptions.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    File tempFile;

    {
        try {
            tempFile = File.createTempFile("Temp_file", ".csv");
            tempFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    FileBackedTaskManager taskManager = new FileBackedTaskManager(tempFile);

    @BeforeEach
    void newTaskManager() {
        taskManager = new FileBackedTaskManager(tempFile);
    }

    @Test
    void savingTaskAfterAddingWithoutTime() {
        taskManager.addTask(new Task("Task", "Description"));
        String exceptText = "1,TASK,Task,NEW,Description,,,,";
        String savingText;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");
    }

    @Test
    void savingTaskAfterAddingWithTime() {
        taskManager.addTask(new Task("Task", "Description",
                LocalDateTime.of(2025, 4, 12, 14, 55), 20));
        String exceptText = "1,TASK,Task,NEW,Description,,2025-04-12T14:55,20,";
        String savingText;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");
    }

    @Test
    void savingEpicAfterAdding() {
        taskManager.addEpic(new Epic("Epic", "Description"));
        String exceptText = "1,EPIC,Epic,NEW,Description,,,,";
        String savingText;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");
    }

    @Test
    void savingSubtaskAfterAdding() {
        taskManager.addEpic(new Epic("Epic", "Description"));
        taskManager.addSubtask(new Subtask("Subtask", "Description"), 1);
        String exceptText = "2,SUBTASK,Subtask,NEW,Description,1,,,";
        String savingText;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");
    }

    @Test
    void emptyFileAfterRemoveAllTasks() {
        taskManager.addTask(new Task("Task", "Description"));
        String exceptText = "1,TASK,Task,NEW,Description,,,,";
        String savingText;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");

        taskManager.removeAllTask();
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertNull(savingText, "Задача не удалена");
    }

    @Test
    void fileUpdateOldVersionTaskAfterUpdate() {
        taskManager.addTask(new Task("Task", "Description"));
        String exceptText = "1,TASK,Task,NEW,Description,,,,";
        String savingText;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");

        taskManager.updateTask(new Task("Task", "Update Description", StatusTask.IN_PROGRESS, 1));
        exceptText = "1,TASK,Task,IN_PROGRESS,Update Description,,,,";
        String nextLine;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
            nextLine = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }

        assertEquals(exceptText, savingText, "Ошибка записи обновлённой задачи");
        assertNull(nextLine, "Неправильная перезапись обновлённой задачи");
    }

    @Test
    void theTaskIsDeletedFromTheFileAfterItIsDeletedFromTheManager() {
        taskManager.addTask(new Task("Task1", "Description1"));
        taskManager.addTask(new Task("Task2", "Description2"));
        String exceptText = "1,TASK,Task1,NEW,Description1,,,,";
        String savingText;
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");

        taskManager.removeTaskById(1);

        exceptText = "2,TASK,Task2,NEW,Description2,,,,";
        try (BufferedReader reader = Files.newBufferedReader(tempFile.toPath())) {
            reader.readLine();
            savingText = reader.readLine();
        } catch (IOException e) {
            throw new ManagerSaveException("Error");
        }
        assertEquals(exceptText, savingText, "Ошибка при сохранении в файл");
    }

    @Test
    void allTasksMustBeCorrectlyLoadedFromTheFile() {
        Task task1 = taskManager.addTask(new Task("Task1", "Description1"));
        Task task2 = taskManager.addTask(new Task("Task2", "Description2"));
        Task task3 = taskManager.addTask(new Task("Task3", "Description3",
                LocalDateTime.of(2025, 4, 12, 14, 55), 20));
        Epic epic1 = taskManager.addEpic(new Epic("Epic", "DescriptionEpic"));
        Subtask subtask1 = taskManager.addSubtask(new Subtask("Subtask", "DescriptionSubtask"),
                epic1.getId());

        FileBackedTaskManager newTaskManager = FileBackedTaskManager.loadFromFile(tempFile);
        Task newTask1 = newTaskManager.getTaskByID(1);
        Task newTask2 = newTaskManager.getTaskByID(2);
        Task newTask3 = newTaskManager.getTaskByID(3);
        Epic newEpic1 = newTaskManager.getEpicById(4);
        Subtask newSubtask1 = newTaskManager.getSubtaskByID(5);

        assertEquals(task1, newTask1, "Ошибка при загрузке задач из файла");
        assertEquals(task2, newTask2, "Ошибка при загрузке задач из файла");
        assertEquals(task3, newTask3, "Ошибка при загрузке задач из файла");
        assertEquals(epic1, newEpic1, "Ошибка при загрузке задач из файла");
        assertEquals(subtask1, newSubtask1, "Ошибка при загрузке задач из файла");
    }

    @Test
    void loadFromNonExistentFile() {
        File nonExistentFile = new File("такого_файла_нет.csv");
        assertThrows(ManagerSaveException.class, () -> FileBackedTaskManager.loadFromFile(nonExistentFile),
                "Чтение из несуществующего файла должно выбросить исключение");
    }

    @Test
    void loadFromFileWithIncorrectlyHeader() throws IOException {
        Files.writeString(tempFile.toPath(), "Заменим заголовок на данный текст", StandardCharsets.UTF_8);

        assertThrows(ManagerSaveException.class, () -> FileBackedTaskManager.loadFromFile(tempFile),
                "При неправильном заголовке должно быть выброшено исключение");
    }

    @Test
    void saveAndReloadInFile() {
        assertDoesNotThrow(() -> {
            Task task1 = taskManager.addTask(new Task("Task1", "Description1"));
            Task task2 = taskManager.addTask(new Task("Task2", "Description2",
                    LocalDateTime.of(2025, 4, 12, 14, 55), 20));
            Epic epic1 = taskManager.addEpic(new Epic("Epic", "DescriptionEpic"));
            Subtask subtask1 = taskManager.addSubtask(new Subtask("Subtask", "DescriptionSubtask"),
                    epic1.getId());

            FileBackedTaskManager newTaskManager = FileBackedTaskManager.loadFromFile(tempFile);
            assertFalse(newTaskManager.getAllTasks().isEmpty(),
                    "При чтении файла задачи должны записаться");
        });
    }
}




