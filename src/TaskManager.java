import java.util.HashMap;

public class TaskManager {


    private final HashMap<Integer, Task> taskMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    public void createTask(Task task) {
        taskMap.put(task.getId(), task);
    }


    public void deleteAllTasks() {
        taskMap.clear();
    }

    public void createEpic(Epic epic) {


        epicMap.put(epic.getId(), epic);
    }



    public void CreateEpic2() {
        taskMap.clear();
    }

    public void createSubTask(Subtask subtask) {

        subtaskMap.put(subtask.getId(), subtask);
    }



    public void CreateSubTask2() {
        subtaskMap.clear();
    }
}

