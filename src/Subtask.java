public class Subtask extends Task{

    protected int taskId;
    protected int epicId;


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getTaskId() {
        return id;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Subtask(String title, String description) {
        super(title, description);
        this.status = StatusTask.NEW;
    }

    //для обновления статуса
    public Subtask(String title, String description, StatusTask status) {
        super(title, description, status);

    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epicID=" + epicId +
                '}';
    }
}
