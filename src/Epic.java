import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{

    HashMap<Integer, Subtask> subtasksEpic;
    private StatusTask epicStatus;

    public void setTaskId(int taskId) {
        this.id = id;
    }

    public Epic(String title, String description) {
        super(title, description);
        subtasksEpic = new HashMap<>();
        this.status = StatusTask.NEW;
    }

    public HashMap<Integer, Subtask> getSubtasksEpic() {
        return subtasksEpic;
    }

    public void addSubtask(int id, Subtask subtask) {
        subtasksEpic.put(id, subtask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
