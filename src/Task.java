public class Task {

    protected String title;
    protected String description;
    StatusTask status;
    protected int id;
    protected int epicIdBySubtask;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = StatusTask.NEW;
    }

    //для обновления статуса
    public Task(String title, String description, StatusTask status) {
        this.title = title;
        this.description = description;
        this.status = status;
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
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
