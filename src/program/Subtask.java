package program;

public class Subtask extends AbstractTask{

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

    @Override
    public String toString() {
        return "program.Subtask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", epicId=" + epicId + '\'' + ", id=" + getId() +
                '}';
    }
}
