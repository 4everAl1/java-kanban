package program;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{

    private final static int MAX_SIZE_HISTORY_LIST = 10;
    private final ArrayList<AbstractTask> history;

    public InMemoryHistoryManager() {
        this.history = new ArrayList<>();
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
