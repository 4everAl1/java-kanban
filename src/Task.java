import java.util.Objects;

public class Task {

    private String title;
    private String description;
    private StatusTask status;
    protected int id;
    protected int epicIdBySubtask;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = StatusTask.NEW;
    }

    //для обновления статуса
    public Task(String title, String description, StatusTask status,int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getTaskID() {
        return id;
    }

    public StatusTask getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setTaskID(int taskID) {
        this.id = taskID;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
