public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = inMemoryTaskManager.addTask(new Task("Задача 1","Первая задача"));
        Task task2 = inMemoryTaskManager.addTask(new Task("Задача 2","Вторая"));
        Task task3 = inMemoryTaskManager.addTask(new Task("Задача 3","Третья"));
        System.out.println(inMemoryTaskManager.getAllTasks());
        inMemoryTaskManager.removeAllTask();
        System.out.println(inMemoryTaskManager.getAllTasks());
        task1 = inMemoryTaskManager.addTask(new Task("Задача 1","Первая задача"));
        task2 = inMemoryTaskManager.addTask(new Task("Задача 2","Вторая"));
        task3 = inMemoryTaskManager.addTask(new Task("Задача 3","Третья"));
        inMemoryTaskManager.removeTaskById(task1.getId());
        inMemoryTaskManager.removeTaskById(task2.getId());
        inMemoryTaskManager.updateTask(new Task("Всё ещё задача 3", "Но уже в процессе",StatusTask.IN_PROGRESS,task3.getId()));
        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getTaskByID(task3.getId()));
        System.out.println();
        System.out.println();

        System.out.println(inMemoryTaskManager.getHistory());


        Epic epic1 = inMemoryTaskManager.addEpic(new Epic("Эпик 1","Вот такой эпик"));
        Epic epic2 = inMemoryTaskManager.addEpic(new Epic("Эпик 2","Ещё эпик"));
        Epic epic3 = inMemoryTaskManager.addEpic(new Epic("Эпик 3","Ну и третий"));
        System.out.println(inMemoryTaskManager.getAllEpics());
        inMemoryTaskManager.removeAllEpic();
        System.out.println(inMemoryTaskManager.getAllEpics());
        epic1 = inMemoryTaskManager.addEpic(new Epic("Эпик 1","Вот такой эпик"));
        epic2 = inMemoryTaskManager.addEpic(new Epic("Эпик 2","Ещё эпик"));
        epic3 = inMemoryTaskManager.addEpic(new Epic("Эпик 3","Ну и третий"));
        System.out.println(inMemoryTaskManager.getEpicByID(epic2.getId()));
        inMemoryTaskManager.removeEpicById(epic1.getId());
        System.out.println(inMemoryTaskManager.getAllEpics());
        inMemoryTaskManager.updateEpic(new Epic("Так же 3 эпик", "Только обновлённый", epic3.getId()));
        System.out.println(inMemoryTaskManager.getAllEpics());
        System.out.println();
        System.out.println();

        System.out.println(inMemoryTaskManager.getHistory());
//
//        Subtask subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Подзадача 1", "Что делать!"), epic2.getId());
//        Subtask subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Подзадача 2", "Что делать!"), epic2.getId());
//        Subtask subtask3 = inMemoryTaskManager.addSubtask(new Subtask("Подзадача 3", "Что делать!"), epic3.getId());
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        inMemoryTaskManager.removeAllSubtask();
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        System.out.println();
//        System.out.println(inMemoryTaskManager.getSubtaskByEpic(epic3.getId()));
//        subtask1 = inMemoryTaskManager.addSubtask(new Subtask("Подзадача 1", "Что делать!"), epic2.getId());
//        subtask2 = inMemoryTaskManager.addSubtask(new Subtask("Подзадача 2", "Что делать!"), epic2.getId());
//        subtask3 = inMemoryTaskManager.addSubtask(new Subtask("Подзадача 3", "Что делать!"), epic3.getId());
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//        System.out.println(inMemoryTaskManager.getSubtaskByEpic(epic3.getId()));
//        System.out.println();
//        inMemoryTaskManager.updateSubtask(new Subtask("Задача 1", "Что-то делать...",StatusTask.IN_PROGRESS, subtask1.getId()));
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


    }
}
