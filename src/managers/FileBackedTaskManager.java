package managers;

import tasks.Epic;
import tasks.StatusTask;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;

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

    private String generateCSVData() {
        StringBuilder csvData = new StringBuilder();
        csvData.append("id,type,name,status,description,epic,startTime,duration,endTime").append(System.lineSeparator());

        getAllTask().forEach(task -> csvData.append(toString(task)).append(System.lineSeparator()));

        return csvData.toString();
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

        String taskTypes = "";
        String epic = "";
        String startTime = "";
        String duration = "";
        String endTime = "";
        if (Optional.ofNullable(task.getStartTime()).isPresent()) {
            startTime = task.getStartTime().toString();
        }
        if (Optional.ofNullable(task.getDuration()).isPresent()) {
            duration = String.valueOf(task.getDuration().toMinutes());
        }
        if (Optional.ofNullable(task.getEndTime()).isPresent()) {
            endTime = task.getEndTime().toString();
        }

        if (task.getClass() == Task.class) {
            taskTypes = TaskType.TASK.toString();
        } else if (task.getClass() == Epic.class) {
            taskTypes = TaskType.EPIC.toString();
        } else if (task.getClass() == Subtask.class) {
            taskTypes = TaskType.SUBTASK.toString();
            epic = Integer.toString(((Subtask) task).getEpicid());
        }

        return task.getId() + "," +
                taskTypes + "," +
                task.getName() + "," +
                task.getStatus() + "," +
                task.getDescription() + "," +
                epic + "," +
                startTime + "," +
                duration + "," +
                endTime;
    }


    public static Task fromString(String value) {
        String[] temp = value.split(",");
        int taskId = Integer.parseInt(temp[0]);
        TaskType taskType = TaskType.valueOf(temp[1]);
        String taskName = temp[2];
        StatusTask taskStatus = StatusTask.valueOf(temp[3]);
        String taskDescription = piecesOfLineValidate(temp, 4) ? temp[4] : "";
        ZonedDateTime startTime = piecesOfLineValidate(temp, 5)
                ? ZonedDateTime.parse(temp[5]) : null;
        Duration duration = piecesOfLineValidate(temp, 6)
                ? Duration.ofMinutes(Integer.parseInt(temp[6].trim())) : null;
        ZonedDateTime endTime = piecesOfLineValidate(temp, 7)
                ? ZonedDateTime.parse(temp[7]) : null;
        
        switch (taskType) {
            case TASK:
                Task task = new Task(taskName, taskDescription, taskStatus, taskId);
                task.setStartTime(startTime);
                task.setDuration(duration);
                return task;

            case SUBTASK:
                int subEpicId = Integer.parseInt(temp[5]);
                Subtask subtask = new Subtask(taskName, taskDescription, taskStatus, subEpicId, taskId);
                return subtask;

            case EPIC:
                Epic epic = new Epic(taskName, taskDescription, taskStatus, taskId);
                epic.setEndTime(endTime);
                return epic;

            default:
                throw new IllegalArgumentException("Ошибка! Недопустимое значение: " + taskType);
        }
    }

    private static boolean piecesOfLineValidate(String[] temp, int i) {
        return temp.length > i && !temp[i].trim().isEmpty();
    }


}
