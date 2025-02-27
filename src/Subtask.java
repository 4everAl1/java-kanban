public class Subtask extends Task{

    private int epicId;

    public Subtask(String title, String description) {
        super(title, description);
        super.setStatus(StatusTask.NEW);
    }

    //для обновления статуса
    public Subtask(String title, String description, StatusTask status, int id) {
        super(title, description, status, id);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getSubtaskId() {
        return id;
    }

    public void setSubtaskId(int taskId) {
        this.id = taskId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicID=" + epicId +
                '}';
    }
}
