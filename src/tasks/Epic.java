package tasks;

import enums.StatusTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Epic extends AbstractTask {

    private LocalDateTime endTime;

    private HashMap<Integer, Subtask> subtasksEpic = new HashMap<>();

    public void setSubtasksEpic(HashMap<Integer, Subtask> subtasksEpic) {
        this.subtasksEpic = subtasksEpic;
    }

    public Epic(String title, String description) {
        super(title, description);
        this.setStatus(StatusTask.NEW);
    }

    public Epic(String title, String description, int id) {
        super(title, description);
        this.setId(id);
        this.setStatus(StatusTask.NEW);
    }

    public Epic(String title, String description, StatusTask statusTask, int id) {
        super(title, description, statusTask, id);

    }

    //Для создания объекта в FileBackedTaskManager
    public Epic(String title,
                String description,
                StatusTask statusTask,
                int id,
                LocalDateTime startTime,
                Duration duration,
                LocalDateTime endTime) {
        super(title, description, statusTask, id, startTime, duration);
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public HashMap<Integer, Subtask> getMapSubtasksEpic() {
        return subtasksEpic;
    }

    public void addSubtask(int id, Subtask subtask) {
        subtasksEpic.put(id, subtask);
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }
}
