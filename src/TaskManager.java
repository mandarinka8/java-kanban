import java.util.HashMap;

public class TaskManager {


    static HashMap<Integer, Task> taskMap = new HashMap<>();
    static HashMap<Integer, Epic> epicMap = new HashMap<>();
    static HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    public void CreateTask(String name, String description, StatusTask status, int id) {
        id++;
        Task task = new Task(name, description, status, id);
        taskMap.put(id, task);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void CreateTask1() {
        for (Task task1 : taskMap.values()) {
            System.out.println(task1);

        }
    }

    public void CreateTask2() {
        taskMap.clear();
    }

    public void CreateEpic(String name, String description, StatusTask status, int id) {
        id++;
        Epic epic = new Epic(name, description, status, id);
        epicMap.put(id, epic);
    }

    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
    }

    public void CreateEpic1() {
        for (Epic epic1 : epicMap.values()) {
            System.out.println(epic1);

        }
    }

    public void CreateEpic2() {
        taskMap.clear();
    }

    public void CreateSubTask(String name, String description, StatusTask status, int id) {
        id++;
        Subtask subtask = new Subtask(name, description, status, id);
        subtaskMap.put(id, subtask);
    }

    public void updateSubTask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
    }

    public void CreateSubTask1() {
        for (Subtask subtask1 : subtaskMap.values()) {
            System.out.println(subtask1);
        }
    }

    public void CreateSubTask2() {
        subtaskMap.clear();
    }
}

