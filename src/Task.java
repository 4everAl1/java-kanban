import java.util.Objects;

public class Task extends AbstractTask {

//    private String title;
//    private String description;
//    private StatusTask status;
//    protected int id;
//    protected int epicIdBySubtask;

//    public Task(String title, String description) {
//        this.title = title;
//        this.description = description;
//        this.status = StatusTask.NEW;
//    }
    public Task(String title, String description) {
        super(title, description);
        this.setStatus(StatusTask.NEW);

    }
    //для обновления статуса
    public Task(String title, String description, StatusTask status,int id) {
        super(title, description, status, id);
        //        this.title = title;
//        this.description = description;
//        this.status = status;
//        this.id = id;
    }

//    public String getTitle() {
//        return title;
//    }

//    public int getTaskID() {
//        return id;
//    }
//
//    public StatusTask getStatus() {
//        return status;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setTaskID(int taskID) {
//        this.id = taskID;
//    }
//
//    public void setStatus(StatusTask status) {
//        this.status = status;
//    }


    @Override
    public String toString() {
        return "Task{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                '}';
    }
}
