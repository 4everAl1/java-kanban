package program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> history;
    private Node head;
    private Node tail;


    public InMemoryHistoryManager() {
        this.history = new HashMap<>();
    }

    @Override
    public void remove(int id) {
        Node node = history.remove(id);

        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public void add(AbstractTask task) {
        int id = task.getId();

        Node node = new Node(null, task, null);
        linkLast(node);

        remove(id);

        history.put(id, node);
    }

    @Override
    public List<AbstractTask> getHistory() {
        List<AbstractTask> taskList = new ArrayList<>();
        Node item = head;

        while (item != null) {
            taskList.add(item.task);
            item = item.next;
        }
        return taskList;

    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        if (head == node) {
            head = next;
        }

        if (tail == node) {
            tail = prev;
        }

        if (next != null) {
            next.prev = prev;
        }

        if (prev != null) {
            prev.next = next;
        }
    }

    private void linkLast(Node node) {
        if (head == null) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
    }

    private static class Node {
        private final AbstractTask task;
        private Node next;
        private Node prev;

        public Node(Node prev, AbstractTask task, Node next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }
    }
}
