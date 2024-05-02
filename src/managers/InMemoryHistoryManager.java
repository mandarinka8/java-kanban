package managers;
import tasks.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    private final CustLinkedList historyOfView = new CustLinkedList();
    //private final static int SIZE = 10;

    @Override
    public void addHistory(Task task) {
        /*Task cloneTask = new Task(task.getName(),task.getDescription(),task.getStatus(),task.getId());
        if (task != null) {
            if (historyOfView.size() <= SIZE)  {
                historyOfView.add(cloneTask);
            } else {
                historyOfView.remove(0);
                historyOfView.add(cloneTask);
            }

        }*/
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
