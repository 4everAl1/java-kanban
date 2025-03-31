import program.InMemoryTaskManager;
import program.Managers;
import program.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");
        Managers.getDefault();

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = inMemoryTaskManager.addTask(new Task("Таска1", "Описание1"));
        Task task2 = inMemoryTaskManager.addTask(new Task("Таска2", "Описание2"));
        Task task3 = inMemoryTaskManager.addTask(new Task("Таска3", "Описание3"));
        Task task4 = inMemoryTaskManager.addTask(new Task("Таска4", "Описание4"));
        inMemoryTaskManager.getTaskByID(task1.getId());
        inMemoryTaskManager.getTaskByID(task2.getId());
        inMemoryTaskManager.getTaskByID(task3.getId());
        inMemoryTaskManager.getTaskByID(task3.getId());
        inMemoryTaskManager.getTaskByID(task4.getId());
        inMemoryTaskManager.getTaskByID(task4.getId());
        System.out.println(inMemoryTaskManager.getHistory());


    }
}
