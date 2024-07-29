package managers;

import tasks.*;

import java.util.*;
import java.time.ZonedDateTime;
import java.time.Duration;


public class InMemoryTaskManager implements TaskManager {

    int id = 100;

    protected final Map<Integer, Task> taskMap = new HashMap<>();
    protected final Map<Integer, Epic> epicMap = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    private final List<Task> historyOfView = new ArrayList<>();
    private final Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));


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
        if (subtasksIdInThisEpic.isEmpty()) {
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

    public void EpicDateTime(Epic epic) {
        if (!epic.getSubTaskIds().isEmpty()) {
            final Subtask minSubtask = (Subtask) epic.getSubTaskIds().stream()
                    .map(this::gettingTask)
                    .min(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(ZonedDateTime::compareTo)))
                    .orElseThrow();
            epic.setStartTime(minSubtask.getStartTime());

            final Subtask maxSubtask = (Subtask) epic.getSubTaskIds().stream()
                    .map(this::gettingTask)
                    .max(Comparator.comparing(Task::getEndTime, Comparator.nullsFirst(ZonedDateTime::compareTo)))
                    .orElseThrow();
            epic.setEndTime(maxSubtask.getStartTime());

            if (Optional.ofNullable(epic.getStartTime()).isPresent()
                    && Optional.ofNullable(epic.getEndTime()).isPresent()) {
                epic.setDuration(Duration.between(epic.getStartTime(), epic.getEndTime()));
            } else {
                epic.setDuration(null);
            }
        } else {
            epic.setStartTime(null);
            epic.setDuration(null);
            epic.setEndTime(null);
        }
    }

    private void DateTime(Task task) {
        if (task.getClass() == Epic.class) {
            EpicDateTime((Epic) task);
        } else if (task.getClass() == Subtask.class) {
            Subtask subtask = (Subtask) task;
            Epic epic = (Epic) taskMap.get(subtask.getEpicid());
            if (epic != null)
                EpicDateTime(epic);
        }
    }

    private void addPrioritizedTasks(Task task) {
        if (Optional.ofNullable(task.getStartTime()).isPresent()) {
            prioritizedTasks.add(task);
        }
    }

    private void validateTask(Task task) throws ManagerValidationException {
        final List<Task> list = getPrioritizedTasks();

        final boolean isCovered = list.stream()
                .anyMatch(taskFromStream -> isDateTimeCoverTwoTasks(task, taskFromStream));

        if (isCovered) {
            throw new ManagerValidationException("Ошибка. Задачи пересекаются по времени выполнения: " + task);
        }
    }

    private boolean isDateTimeCoverTwoTasks(Task task1, Task task2) {
        if (Optional.ofNullable(task1.getStartTime()).isEmpty()
                || Optional.ofNullable(task1.getEndTime()).isEmpty()
                || Optional.ofNullable(task2.getStartTime()).isEmpty()
                || Optional.ofNullable(task2.getEndTime()).isEmpty()) {
            return false;
        }

        long start1 = task1.getStartTime().toInstant().toEpochMilli();
        long end1 = task1.getEndTime().toInstant().toEpochMilli();
        long start2 = task2.getStartTime().toInstant().toEpochMilli();
        long end2 = task2.getEndTime().toInstant().toEpochMilli();

        return (start1 - end2) * (start2 - end1) > 0;
    }


    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

}
