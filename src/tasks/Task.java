package tasks;

import enums.StatusTask;

public class Task extends AbstractTask {

    public Task(String title, String description) {
        super(title, description);
        this.setStatus(StatusTask.NEW);

    }

    //для обновления статуса
    public Task(String title, String description, StatusTask status, int id) {
        super(title, description, status, id);

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
