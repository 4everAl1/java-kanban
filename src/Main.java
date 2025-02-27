public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Task task1 = taskManager.addTask(new Task("Задача 1","Первая задача"));
        Task task2 = taskManager.addTask(new Task("Задача 2","Вторая"));
        Task task3 = taskManager.addTask(new Task("Задача 3","Третья"));
        System.out.println(taskManager.getAllTasks());
        taskManager.removeAllTask();
        System.out.println(taskManager.getAllTasks());
        task1 = taskManager.addTask(new Task("Задача 1","Первая задача"));
        task2 = taskManager.addTask(new Task("Задача 2","Вторая"));
        task3 = taskManager.addTask(new Task("Задача 3","Третья"));
        taskManager.removeTaskById(task1.getTaskID());
        taskManager.removeTaskById(task2.getTaskID());
        taskManager.updateTask(new Task("Всё ещё задача 3", "Но уже в процессе",StatusTask.IN_PROGRESS,task3.getTaskID()));
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getTaskByID(task3.getTaskID()));
        System.out.println();
        System.out.println();

        Epic epic1 = taskManager.addEpic(new Epic("Эпик 1","Вот такой эпик"));
        Epic epic2 = taskManager.addEpic(new Epic("Эпик 2","Ещё эпик"));
        Epic epic3 = taskManager.addEpic(new Epic("Эпик 3","Ну и третий"));
        System.out.println(taskManager.getAllEpics());
        taskManager.removeAllEpic();
        System.out.println(taskManager.getAllEpics());
        epic1 = taskManager.addEpic(new Epic("Эпик 1","Вот такой эпик"));
        epic2 = taskManager.addEpic(new Epic("Эпик 2","Ещё эпик"));
        epic3 = taskManager.addEpic(new Epic("Эпик 3","Ну и третий"));
        System.out.println(taskManager.getEpicByID(epic2.getTaskID()));
        taskManager.removeEpicById(epic1.getTaskID());
        System.out.println(taskManager.getAllEpics());
//        taskManager.updateEpic(new Epic("Так же 3 эпик", "Только обновлённый", epic3.getTaskID()));
        System.out.println(taskManager.getAllEpics());
        System.out.println();
        System.out.println();

        Subtask subtask1 = taskManager.addSubtask(new Subtask("Подзадача 1", "Что делать!"), epic2.getEpicId());
        Subtask subtask2 = taskManager.addSubtask(new Subtask("Подзадача 2", "Что делать!"), epic2.getEpicId());
        Subtask subtask3 = taskManager.addSubtask(new Subtask("Подзадача 3", "Что делать!"), epic3.getEpicId());
        System.out.println(taskManager.getAllSubtask());
        taskManager.removeAllSubtask();
        System.out.println(taskManager.getAllSubtask());
        System.out.println();
        System.out.println(taskManager.getSubtaskByEpic(epic3.getEpicId()));
        subtask1 = taskManager.addSubtask(new Subtask("Подзадача 1", "Что делать!"), epic2.getEpicId());
        subtask2 = taskManager.addSubtask(new Subtask("Подзадача 2", "Что делать!"), epic2.getEpicId());
        subtask3 = taskManager.addSubtask(new Subtask("Подзадача 3", "Что делать!"), epic3.getEpicId());
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getSubtaskByEpic(epic3.getEpicId()));
        System.out.println();
        taskManager.updateSubtask(new Subtask("Задача 1", "Что-то делать...",StatusTask.IN_PROGRESS, subtask1.getSubtaskId()));
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getSubtaskByID(subtask1.getSubtaskId()));
        System.out.println();
        taskManager.removeSubtaskById(subtask1.getSubtaskId());
        System.out.println(taskManager.getAllSubtask());
        System.out.println(taskManager.getSubtaskByEpic(epic2.getEpicId()));
        System.out.println(taskManager.getAllEpics());
        System.out.println();

        taskManager.removeEpicById(epic2.getEpicId());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtask());

    }
}
