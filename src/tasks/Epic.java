package tasks;

import enums.StatusTask;

import java.util.HashMap;

public class Epic extends AbstractTask {

    private HashMap<Integer, Subtask> subtasksEpic;

    public void setSubtasksEpic(HashMap<Integer, Subtask> subtasksEpic) {
        this.subtasksEpic = subtasksEpic;
    }

    public Epic(String title, String description) {
        super(title, description);
        subtasksEpic = new HashMap<>();
        this.setStatus(StatusTask.NEW);
    }

    public Epic(String title, String description, int id) {
        super(title, description);
        this.setId(id);
        subtasksEpic = new HashMap<>();
        this.setStatus(StatusTask.NEW);
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
