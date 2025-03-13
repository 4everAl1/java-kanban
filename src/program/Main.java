package program;

public class Main {

    public static void main(String[] args) {

//        program.InMemoryTaskManager inMemoryTaskManager = new program.InMemoryTaskManager();
//        program.Task task1 = inMemoryTaskManager.addTask(new program.Task("Задача 1","Первая задача"));
//        program.Task task2 = inMemoryTaskManager.addTask(new program.Task("Задача 2","Вторая"));
//        program.Task task3 = inMemoryTaskManager.addTask(new program.Task("Задача 3","Третья"));
//        System.out.println(inMemoryTaskManager.getAllTasks());
//        inMemoryTaskManager.removeAllTask();
//        System.out.println(inMemoryTaskManager.getAllTasks());
//        task1 = inMemoryTaskManager.addTask(new program.Task("Задача 1","Первая задача"));
//        task2 = inMemoryTaskManager.addTask(new program.Task("Задача 2","Вторая"));
//        task3 = inMemoryTaskManager.addTask(new program.Task("Задача 3","Третья"));
//        inMemoryTaskManager.removeTaskById(task1.getId());
//        inMemoryTaskManager.removeTaskById(task2.getId());
//        inMemoryTaskManager.updateTask(new program.Task("Всё ещё задача 3", "Но уже в процессе",program.StatusTask.IN_PROGRESS,task3.getId()));
//        System.out.println(inMemoryTaskManager.getAllTasks());
//        System.out.println(inMemoryTaskManager.getTaskByID(task3.getId()));
//        System.out.println();
//        System.out.println();
//
//        System.out.println(inMemoryTaskManager.getHistory());
//
//
//        program.Epic epic1 = inMemoryTaskManager.addEpic(new program.Epic("Эпик 1","Вот такой эпик"));
//        program.Epic epic2 = inMemoryTaskManager.addEpic(new program.Epic("Эпик 2","Ещё эпик"));
//        program.Epic epic3 = inMemoryTaskManager.addEpic(new program.Epic("Эпик 3","Ну и третий"));
//        System.out.println(inMemoryTaskManager.getAllEpics());
//        inMemoryTaskManager.removeAllEpic();
//        System.out.println(inMemoryTaskManager.getAllEpics());
//        epic1 = inMemoryTaskManager.addEpic(new program.Epic("Эпик 1","Вот такой эпик"));
//        epic2 = inMemoryTaskManager.addEpic(new program.Epic("Эпик 2","Ещё эпик"));
//        epic3 = inMemoryTaskManager.addEpic(new program.Epic("Эпик 3","Ну и третий"));
//        System.out.println(inMemoryTaskManager.getEpicByID(epic2.getId()));
//        inMemoryTaskManager.removeEpicById(epic1.getId());
//        System.out.println(inMemoryTaskManager.getAllEpics());
//        inMemoryTaskManager.updateEpic(new program.Epic("Так же 3 эпик", "Только обновлённый", epic3.getId()));
//        System.out.println(inMemoryTaskManager.getAllEpics());
//        System.out.println();
//        System.out.println();
//
//        System.out.println(inMemoryTaskManager.getHistory());
//
//        program.Subtask subtask1 = inMemoryTaskManager.addSubtask(new program.Subtask("Подзадача 1", "Что делать!"), epic2.getId());
//        program.Subtask subtask2 = inMemoryTaskManager.addSubtask(new program.Subtask("Подзадача 2", "Что делать!"), epic2.getId());
//        program.Subtask subtask3 = inMemoryTaskManager.addSubtask(new program.Subtask("Подзадача 3", "Что делать!"), epic3.getId());
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        inMemoryTaskManager.removeAllSubtask();
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        System.out.println();
//        System.out.println(inMemoryTaskManager.getSubtaskByEpic(epic3.getId()));
//        subtask1 = inMemoryTaskManager.addSubtask(new program.Subtask("Подзадача 1", "Что делать!"), epic2.getId());
//        subtask2 = inMemoryTaskManager.addSubtask(new program.Subtask("Подзадача 2", "Что делать!"), epic2.getId());
//        subtask3 = inMemoryTaskManager.addSubtask(new program.Subtask("Подзадача 3", "Что делать!"), epic3.getId());
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        System.out.println(inMemoryTaskManager.getSubtaskByEpic(epic3.getId()));
//        System.out.println();
//        inMemoryTaskManager.updateSubtask(new program.Subtask("Задача 1", "Что-то делать...",program.StatusTask.IN_PROGRESS, subtask1.getId()));
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        System.out.println(inMemoryTaskManager.getAllEpics());
//        System.out.println(inMemoryTaskManager.getSubtaskByID(subtask1.getId()));
//        System.out.println();
//        inMemoryTaskManager.removeSubtaskById(subtask1.getId());
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        System.out.println(inMemoryTaskManager.getSubtaskByEpic(epic2.getId()));
//        System.out.println(inMemoryTaskManager.getAllEpics());
//        System.out.println();
//
//        inMemoryTaskManager.removeEpicById(epic2.getId());
//        System.out.println(inMemoryTaskManager.getAllEpics());
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        program.Managers managers = new program.Managers();
        TaskManager taskManager = Managers.getDefaultTaskManager();

//        program.TaskManager taskManager1 = new program.InMemoryTaskManager();
        Task task1 = taskManager.addTask(new Task("1", "2"));
        Epic epic1 = taskManager.addEpic(new Epic("epic 1", "description 1"));
        Subtask subtask1 = taskManager.addSubtask(new Subtask("Sub1", "des1"), epic1.getId());
        taskManager.getSubtaskByID(subtask1.getId());
        taskManager.getSubtaskByID(subtask1.getId());
        taskManager.getSubtaskByID(subtask1.getId());
        taskManager.getEpicByID(epic1.getId());
        taskManager.getTaskByID(task1.getId());
        System.out.println(taskManager.getHistory());
        System.out.println();
        System.out.println();
        taskManager.updateTask(new Task("12","22", StatusTask.IN_PROGRESS, task1.getId()));
        taskManager.getTaskByID(task1.getId());
        System.out.println(taskManager.getHistory());


    }
}
