package tasks;

import enums.StatusTask;

import java.time.LocalDateTime;

public class Task extends AbstractTask {

    //Для создания новой Task без учета времени
    public Task(String title, String description) {
        super(title, description);
        this.setStatus(StatusTask.NEW);

    }

    //С учётом времени
    public Task(String title, String description, LocalDateTime startTime, int duration) {
        super(title, description, startTime, duration);
        this.setStatus(StatusTask.NEW);
    }

    //для обновления статуса без учёта времени
    public Task(String title, String description, StatusTask status, int id) {
        super(title, description, status, id);
    }

    //С учётом времени
    public Task(String title, String description, StatusTask status, int id, LocalDateTime startTime, int duration) {
        super(title, description, status, id, startTime, duration);
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }
}
