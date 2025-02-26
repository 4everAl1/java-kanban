import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Task task1 = taskManager.newTask(new Task("Задача 1","Первая задача"));
        Task task2 = taskManager.newTask(new Task("Задача 2","Вторая"));
        Task task3 = taskManager.newTask(new Task("Задача 3","Третья"));
        System.out.println(taskManager.showAllTasks());
        taskManager.removeAllTask();
        System.out.println(taskManager.showAllTasks());
        task1 = taskManager.newTask(new Task("Задача 1","Первая задача"));
        task2 = taskManager.newTask(new Task("Задача 2","Вторая"));
        task3 = taskManager.newTask(new Task("Задача 3","Третья"));
        taskManager.removeTaskById(task1.getTaskID());
        taskManager.removeTaskById(task2.getTaskID());
        taskManager.updateTask(task3.getTaskID(),new Task("Всё ещё задача 3", "Но уже в процессе",StatusTask.IN_PROGRESS));
        System.out.println(taskManager.showAllTasks());
        System.out.println(taskManager.showTaskByID(task3.getTaskID()));
        System.out.println();
        System.out.println();

        Epic epic1 = taskManager.newEpic(new Epic("Эпик 1","Вот такой эпик"));
        Epic epic2 = taskManager.newEpic(new Epic("Эпик 2","Ещё эпик"));
        Epic epic3 = taskManager.newEpic(new Epic("Эпик 3","Ну и третий"));
        System.out.println(taskManager.showAllEpics());
        taskManager.removeAllEpic();
        System.out.println(taskManager.showAllEpics());
        epic1 = taskManager.newEpic(new Epic("Эпик 1","Вот такой эпик"));
        epic2 = taskManager.newEpic(new Epic("Эпик 2","Ещё эпик"));
        epic3 = taskManager.newEpic(new Epic("Эпик 3","Ну и третий"));
        System.out.println(taskManager.showEpicByID(epic2.getTaskID()));
        taskManager.removeEpicById(epic1.getTaskID());
        System.out.println(taskManager.showAllEpics());
        taskManager.updateEpic(epic3.getTaskID(),new Epic("Так же 3 эпик", "Только обновлённый"));
        System.out.println(taskManager.showAllEpics());
        System.out.println();
        System.out.println();

        Subtask subtask1 = taskManager.newSubtask(new Subtask("Подзадача 1", "Что делать!"), epic2.getTaskID());
        Subtask subtask2 = taskManager.newSubtask(new Subtask("Подзадача 2", "Что делать!"), epic2.getTaskID());
        Subtask subtask3 = taskManager.newSubtask(new Subtask("Подзадача 3", "Что делать!"), epic3.getTaskID());
        System.out.println(taskManager.showAllSubtask());
        taskManager.removeAllSubtask();
        System.out.println(taskManager.showAllSubtask());
        System.out.println();
        System.out.println(taskManager.showSubtaskByEpic(epic3.getTaskID()));
        subtask1 = taskManager.newSubtask(new Subtask("Подзадача 1", "Что делать!"), epic2.getTaskID());
        subtask2 = taskManager.newSubtask(new Subtask("Подзадача 2", "Что делать!"), epic2.getTaskID());
        subtask3 = taskManager.newSubtask(new Subtask("Подзадача 3", "Что делать!"), epic3.getTaskID());
        System.out.println(taskManager.showAllSubtask());
        System.out.println(taskManager.showSubtaskByEpic(epic3.getTaskID()));
        System.out.println();
        taskManager.updateSubtask(subtask1.getTaskId(),new Subtask("Задача 1", "Что-то делать...",StatusTask.IN_PROGRESS));
        System.out.println(taskManager.showAllSubtask());
        System.out.println(taskManager.showAllEpics());
        System.out.println(taskManager.showSubtaskByID(subtask1.getTaskId()));
        System.out.println();
        taskManager.removeSubtaskById(subtask1.getTaskId());
        System.out.println(taskManager.showAllSubtask());
        System.out.println(taskManager.showSubtaskByEpic(epic2.getTaskID()));
        System.out.println(taskManager.showAllEpics());




    }
}
