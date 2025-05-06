package tasks;

import enums.StatusTask;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends AbstractTask {

    private int epicId;

    //Для создания новой Subtask без учета времени
    public Subtask(String title, String description) {
        super(title, description);
        super.setStatus(StatusTask.NEW);
    }

    //С учётом времени
    public Subtask(String title, String description,
                   LocalDateTime startTime, Duration duration) {
        super(title, description, startTime, duration);
        super.setStatus(StatusTask.NEW);
    }


    public Subtask(String title, String description,
                   StatusTask status, int id) {
        super(title, description, status, id);
    }

    //Для обновления без учёта времени
    public Subtask(String title, String description,
                   StatusTask status, int id, int epicId) {
        super(title, description, status, id);
        this.epicId = epicId;
    }

    //С учётом времени
    public Subtask(String title, String description, StatusTask status,
                   int id, int epicId, LocalDateTime startTime, Duration duration) {
        super(title, description, status, id, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "tasks.Subtask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() + '\'' +
                ", epicId=" + epicId + '\'' + ", id=" + getId() +
                '}';
    }
}
