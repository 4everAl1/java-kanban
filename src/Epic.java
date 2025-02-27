import java.util.HashMap;

public class Epic extends Task{

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
        this.id = id;
        subtasksEpic = new HashMap<>();
        this.setStatus(StatusTask.NEW);
    }

    public void setEpicId(int id) {
        this.id = id;
    }

    public int getEpicId() {
        return id;
    }

    public HashMap<Integer, Subtask> getMapSubtasksEpic() {
        return subtasksEpic;
    }

    public void addSubtask(int id, Subtask subtask) {
        subtasksEpic.put(id, subtask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + id +
                '}';
    }
}
