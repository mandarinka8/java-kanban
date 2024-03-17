import java.util.HashMap;

public class TaskManager {

    int id = 100;

    private final HashMap<Integer, Task> taskMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    public int counterId() {
        return id++;
    }

    public void createTask(Task task) {
        counterId();
        taskMap.put(id,task);

    }
    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void gettingTask(int id) {
        taskMap.get(id);
    }

    public void deleteAllTasksId(int id) {
        taskMap.remove(id);
    }

    public void deleteAllTasks() {
        taskMap.clear();
    }

    public void createEpic(Epic epic) {
        counterId();
        epicMap.put(id,epic);

    }

    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
    }

    public void gettingEpic(int id) {
        epicMap.get(id);
    }

    public void deleteAllEpicId(int id) {
        epicMap.remove(id);
    }

    public void deleteAllEpic() {
        epicMap.clear();
    }


    public void createSubTask(Subtask subtask) {
        counterId();
        subtaskMap.put(id, subtask);
    }

    public void updateSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
    }

    public void gettingSubtask(int id) {
        subtaskMap.get(id);
    }

    public void deleteAllSubtaskId(int id) {
        subtaskMap.remove(id);
    }

    public void deleteAllSubtask() {
        subtaskMap.clear();
    }


}

