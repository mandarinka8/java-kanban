package managers;

import tasks.Epic;
import tasks.StatusTask;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTaskManager  extends InMemoryTaskManager {

    private  Path path = Paths.get("SavedTasks.csv");

    public FileBackedTaskManager(String path) {
        this.path = Paths.get(path);
    }


    public FileBackedTaskManager() {
    }


    @Override
    public void createTask(Task task) {
    super.createTask(task);
    save();
    }


    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public Task gettingTask(int id) {
        super.gettingTask(id);
        save();
        return null;
    }


    @Override
    public void deleteAllTasksId(int id) {
        super.deleteAllTasksId(id);
        save();
    }


    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }


    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }


    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public Task gettingEpic(int id) {
        super.gettingEpic(id);
        save();
        return null;
    }

    @Override
    public void deleteAllEpicId(int id) {
        super.deleteAllEpicId(id);
        save();
    }


    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }


    @Override
    public void checkEpic(int epicId) {
        super.checkEpic(epicId);
        save();
    }


    @Override
    public void createSubTask(Subtask subtask) {
        super.createSubTask(subtask);
        save();
    }


    @Override
    public void updateSubtask(Subtask updateSubtask) {
        super.updateSubtask(updateSubtask);
        save();
    }


    @Override
    public Task gettingSubtask(int id) {
        super.gettingSubtask(id);
        save();
        return null;
    }


    @Override
    public void deleteAllSubtaskId(int id) {
        super.deleteAllSubtaskId(id);
        save();
    }


    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }


    private void save() {
        try (FileWriter writer = new FileWriter(String.valueOf(path), StandardCharsets.UTF_8)) {
            for (Task task : taskMap.values()) {
                writer.write(toString(task));
            }
            for (Epic epic : epicMap.values()) {
                writer.write(toString(epic));
            }
            for (Subtask subtask : subtaskMap.values()) {
                writer.write(toString(subtask));
            }
        } catch (IOException exception) {
            throw new ManagerSaveException(exception);
        }
    }


    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager();
        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                String[] splitLine = line.split(",");
                TaskType taskType = TaskType.valueOf(splitLine[1]);
                switch (taskType) {
                    case TASK:
                        manager.createTask(fromString(line));
                        break;
                    case SUBTASK:
                        manager.createSubTask((Subtask)fromString(line));
                        break;
                    case EPIC:
                        manager.createEpic((Epic)fromString(line));
                        break;
                    default:
                        throw new IllegalArgumentException("Ошибка! Недопустимое значение: " + taskType);
                }
            }
        } catch (IOException except) {
            throw new ManagerSaveException(except);
        }
        return manager;
    }


    public static String toString(Task task) {
        String r;
        r = String.format("%d," + task.getTaskType() + ",%s," + task.getStatus() + ",%s", task.getId(), task.getName(), task.getDescription());
        if (task.getTaskType() == TaskType.SUBTASK) {
            StringBuilder sb = new StringBuilder(r);
            sb.append(",");
            sb.append(((Subtask) task).getEpicid());
            r = sb.toString();
        }
        return r + "\n";
    }


    public static Task fromString(String value) {
        String[] temp = value.split(",");
        int taskId = Integer.parseInt(temp[0]);
        TaskType taskType = TaskType.valueOf(temp[1]);
        String taskName = temp[2];
        StatusTask taskStatus = StatusTask.valueOf(temp[3]);
        String taskDescription = temp[4];

        switch (taskType) {
            case TASK:
                Task task = new Task(taskName, taskDescription, taskStatus, taskId);
                return task;

            case SUBTASK:
                int subEpicId = Integer.parseInt(temp[5]);
                Subtask subtask = new Subtask(taskName, taskDescription, taskStatus, subEpicId, taskId);
                return subtask;

            case EPIC:
                Epic epic = new Epic(taskName, taskDescription, taskStatus, taskId);
                return epic;

            default:
                throw new IllegalArgumentException("Ошибка! Недопустимое значение: " + taskType);
        }
    }
}
