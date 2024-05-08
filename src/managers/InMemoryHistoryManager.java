package managers;
import tasks.*;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    private final CustLinkedList historyOfView = new CustLinkedList();


    @Override
    public void addHistory(Task task) {
        historyOfView.linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyOfView.getTasks();
    }

    @Override
    public void remove(int id) {
        historyOfView.removeNode(id);
    }
}
