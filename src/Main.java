import program.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");
        Managers.getDefault();

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task1 = inMemoryTaskManager.addTask(new Task("Таска1", "Описание1"));
        Task task2 = inMemoryTaskManager.addTask(new Task("Таска2", "Описание2"));
        Task task3 = inMemoryTaskManager.addTask(new Task("Таска3", "Описание3"));
        Task task4 = inMemoryTaskManager.addTask(new Task("Таска4", "Описание4"));

        Epic epic1 = inMemoryTaskManager.addEpic(new Epic("Epic 1","Description 1"));
        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Subtask1", "Description1"), epic1.getId());

        inMemoryTaskManager.getTaskByID(task1.getId());
        inMemoryTaskManager.getEpicByID(epic1.getId());
        inMemoryTaskManager.getSubtaskByID(subtask1.getId());
        inMemoryTaskManager.getTaskByID(task1.getId());
        
//        inMemoryTaskManager.getTaskByID(task1.getId());
//        inMemoryTaskManager.getTaskByID(task2.getId());
//        inMemoryTaskManager.getTaskByID(task3.getId());
//        inMemoryTaskManager.getTaskByID(task3.getId());
//        inMemoryTaskManager.getTaskByID(task4.getId());
//        inMemoryTaskManager.getTaskByID(task4.getId());
//        inMemoryTaskManager.getTaskByID(task3.getId());
//
//        inMemoryTaskManager.getTaskByID(task1.getId());
//        inMemoryTaskManager.getTaskByID(task1.getId());
//        inMemoryTaskManager.getTaskByID(task1.getId());
//        inMemoryTaskManager.getTaskByID(task1.getId());
        System.out.println(inMemoryTaskManager.getHistory());


    }
}
