import program.FileBackedTaskManager;
import program.InMemoryTaskManager;
import program.TaskManager;
import tasks.AbstractTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello");

        TaskManager taskManager = new FileBackedTaskManager(new File("home.user"));
        Task task = taskManager.addTask(new Task("1","des"));
        Task task1 = taskManager.addTask(new Task("2","des"));
        Task task2 = taskManager.addTask(new Task("3","des"));
        Epic epic = taskManager.addEpic(new Epic("1","des"));
        Subtask subtask = taskManager.addSubtask(new Subtask("1","des"),epic.getId());

//        List<String[]> list = ((FileBackedTaskManager) taskManager).getAllTasksToString();
//        for (String[] el : list) {
//            System.out.println(Arrays.toString(el));
//        }
//        taskManager.removeTaskById(1);


//        System.out.println(((FileBackedTaskManager) taskManager).getAllTasksToString());


    }
}
