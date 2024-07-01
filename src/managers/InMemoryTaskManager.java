package managers;

import tasks.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    int id = 100;

    protected final Map<Integer, Task> taskMap = new HashMap<>();
    protected final Map<Integer, Epic> epicMap = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    private final List<Task> historyOfView = new ArrayList<>();

    private HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public InMemoryTaskManager() {
    }

    public int counterId() {
        return id++;
    }

    @Override
    public void createTask(Task task) {
       task.setId(counterId());
        taskMap.put(id, task);
    }

    @Override
    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public Task gettingTask(int id) {
        if (taskMap.containsKey(id)) {
            historyOfView.add(taskMap.get(id));
            return taskMap.get(id);

        }
        return null;
    }

    public List<Task> getAllTask() {
       taskMap.get(id);
     return historyOfView;
    }

    @Override
    public void deleteAllTasksId(int id) {
        taskMap.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteAllTasks() {
        taskMap.clear();
    }

    @Override
    public void createEpic(Epic epic) {
        //epic.setId(counterId());
        epicMap.put(epic.setId(counterId()), epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
    }

    @Override
    public Task gettingEpic(int id) {
        return epicMap.get(id);
    }

    @Override
    public void deleteAllEpicId(int id) {
        epicMap.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteAllEpic() {
        epicMap.clear();
    }

    @Override
    public void checkEpic(int epicId) {
        Epic thisEpic = epicMap.get(epicId);
        ArrayList<Integer> subtasksIdInThisEpic = thisEpic.getSubTaskIds();
        if (subtasksIdInThisEpic.size() == 0) {
            thisEpic.setStatus(StatusTask.NEW);
            return;
        }
            int checkDone = 0;

            for (int id : subtasksIdInThisEpic) {

                Subtask thisSubtask = subtaskMap.get(id);

                if (thisSubtask.getStatus() == StatusTask.IN_PROGRESS) {
                    thisEpic.setStatus(StatusTask.IN_PROGRESS);
                    return;
                } else if (thisSubtask.getStatus() == StatusTask.DONE) {
                    checkDone++;
                }
            }

            if (subtasksIdInThisEpic.size() == checkDone) {
                thisEpic.setStatus(StatusTask.DONE);
            }
        }



    @Override
    public void createSubTask(Subtask subtask) {
        //subtask.setId(counterId());
        subtaskMap.put(subtask.setId(counterId()), subtask);

        if (subtask != null) {
            Epic thisEpic = epicMap.get(subtask.getEpicid());
            if (thisEpic != null) {
                subtask.setId(id);
                subtaskMap.put(id, subtask);
                thisEpic.getSubTaskIds().add(id);
                checkEpic(subtask.getEpicid());
            }
        }
    }

    @Override
    public void updateSubtask(Subtask updateSubtask) {
        subtaskMap.put(updateSubtask.getId(), updateSubtask);
        if (updateSubtask != null) {
            int epicId = updateSubtask.getEpicid();
            if (subtaskMap.containsKey(updateSubtask.getId())) {
                subtaskMap.put(updateSubtask.getId(), updateSubtask);
                checkEpic(epicId);
            }
        }
    }

    @Override
    public Task gettingSubtask(int id) {

            return subtaskMap.get(id);
    }

    public ArrayList<Subtask> getSubtasksFromEpic(int epicId) {
        ArrayList<Subtask> subtasksInEpic = new ArrayList();
        Epic thisEpic = epicMap.get(epicId);
        ArrayList<Integer> subtasksIdInEpic = thisEpic.getSubTaskIds();

        for (int subtaskId : subtasksIdInEpic) {

            subtasksInEpic.add(subtaskMap.get(subtaskId));
        }

        return subtasksInEpic;
    }

    @Override
    public void deleteAllSubtaskId(int id) {
        subtaskMap.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteAllSubtask() {
        subtaskMap.clear();

    }

    @Override
   public List<Task> getHistory() {
       for (Task history : historyOfView) {
           System.out.println(history);

       }
        return  historyManager.getHistory();
    }

 public  List<Task> removeHistory(int id) {
        if (!historyOfView.contains(id)) {
            historyOfView.remove(id);
        }
        return historyOfView;
    }





}
