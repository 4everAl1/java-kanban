import java.util.Objects;

public abstract class AbstractTask {

    private String title;
    private String description;
    private StatusTask status;
    private int id;

    public AbstractTask(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = StatusTask.NEW;
    }

    public AbstractTask(String title, String description, StatusTask status, int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
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
