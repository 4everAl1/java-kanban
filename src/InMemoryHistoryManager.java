import java.util.ArrayList;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager{

    private final static int MAX_SIZE_HISTORY_LIST = 10;
    private final ArrayList<AbstractTask> history;

    public InMemoryHistoryManager() {
        this.history = new ArrayList<AbstractTask>();
    }

    @Override
    public void add(AbstractTask task) {
        if (history.size() == MAX_SIZE_HISTORY_LIST) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public ArrayList<AbstractTask> getDefaultHistory() {
        return this.history;
    }
}
