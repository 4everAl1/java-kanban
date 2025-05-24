package tasks;


import enums.StatusTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractTask {

    private String title;
    private String description;
    private StatusTask status;
    private int id;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime getEndTime;

    //Для создания задач без временных рамок
    public AbstractTask(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = StatusTask.NEW;
    }

    //Для обновления задач без временных рамок
    public AbstractTask(String title, String description, StatusTask status, int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    //Для создания задач с временными рамками
    public AbstractTask(String title, String description, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.status = StatusTask.NEW;
        this.startTime = startTime;
        this.duration = duration;
        this.getEndTime = startTime.plus(duration);
    }

    //Для обновления задач с временными рамками
    public AbstractTask(String title, String description, StatusTask status, int id,
                        LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
        this.getEndTime = startTime != null ? startTime.plus(duration) : null;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public StatusTask getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getGetEndTime() {
        return getEndTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractTask that = (AbstractTask) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
