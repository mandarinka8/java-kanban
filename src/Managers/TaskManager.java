package Managers;
import Tasks.*;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    void createTask(Task task);
    void updateTask(Task task);
    Task gettingTask(int id);
    void deleteAllTasksId(int id);
    void deleteAllTasks();
    void createEpic(Epic epic);
    void updateEpic(Epic epic);
    Task gettingEpic(int id);
    void deleteAllEpicId(int id);
    void deleteAllEpic();
    void checkEpic(int epicId);
    void createSubTask(Subtask subtask);
    void updateSubtask(Subtask updateSubtask);
    Task gettingSubtask(int id);
    void deleteAllSubtaskId(int id);
    void deleteAllSubtask();
    List<Task> getHistory();

    }

