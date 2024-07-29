package test;

import static org.junit.jupiter.api.Assertions.*;
import tasks.*;
import managers.*;
import managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;

import java.util.List;
import java.util.ArrayList;

public class TestTaskManager {

    InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    FileBackedTaskManager filebackedTaskManager = Managers.getFileBacked();

    Task task1;
    Task task2;
    Task task3;

    /*@Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", StatusTask.NEW, taskManager.counterId());
        taskManager.createTask(task);

        final Task savedTask = taskManager.gettingTask(task.getId());

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTask();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }*/

    @Test
    void add() {
        Task task = new Task("Test addNewTask34", "Test addNewTask description", StatusTask.NEW, taskManager.counterId());
        historyManager.addHistory(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }



    @Test
    void testEqualsEpicsShouldBeEqualsIfIdEquals() {

        Epic epic = new Epic("a", "b", StatusTask.NEW, 4);
        Epic epic1 = new Epic("c", "d", StatusTask.NEW,4);

        Assertions.assertEquals(epic, epic1);
    }

    @Test
    void testEqualsSubtasksShouldBeEqualsIfIdEquals() {

        Subtask subtask = new Subtask("a", "b",  StatusTask.IN_PROGRESS, 5,4);
        Subtask subtask1 = new Subtask("c","d", StatusTask.NEW,5,4);

        Assertions.assertEquals(subtask, subtask1);
    }

    @Test
    void testGetDefaultTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        Assertions.assertTrue(taskManager instanceof InMemoryTaskManager);
    }

    @Test
    void testid() {
        Task task1 = new Task("a", "b", StatusTask.NEW, taskManager.counterId());
        Task task2 = new Task("a", "b", StatusTask.NEW, taskManager.counterId());
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task1);
        Assertions.assertTrue(task1.getId() == 100);
    }

    @Test
    void addAndGetHistory() {
        task1 = new Task("1", "1", StatusTask.NEW,taskManager.counterId());
        taskManager.createTask(task1);
        task2 = new Task("2", "2", StatusTask.NEW,taskManager.counterId());
        taskManager.createTask(task2);
        task3 = new Task("3", "3", StatusTask.NEW,taskManager.counterId());
        taskManager.createTask(task3);


        historyManager.addHistory(task1);
        assertFalse(historyManager.getHistory().isEmpty(), "История пустая.");
        assertEquals(task1, historyManager.getHistory().get(0), "Задача не совпадает.");
    }

    @Test
    void testBackedManager() throws IOException {
        Task t1 = new Task("1", "1", StatusTask.NEW,taskManager.counterId());
        Task t2 = new Task("2", "2", StatusTask.NEW,taskManager.counterId());

        filebackedTaskManager.createTask(t1);
        filebackedTaskManager.createTask(t2);

        File tempFile = File.createTempFile("Tasks", null);
        FileWriter fw = new FileWriter(tempFile);
        fw.write(FileBackedTaskManager.toString(t1));
        fw.write(FileBackedTaskManager.toString(t2));
        fw.close();

        List<String> currentFileList = new ArrayList<>();
        List<String> tempFileList = new ArrayList<>();

        FileReader currentReader = new FileReader("SavedTasks.csv");
        BufferedReader br = new BufferedReader(currentReader);
        while (br.ready()) {
            String line = br.readLine();
            currentFileList.add(line);
        }
        br.close();

        FileReader tempReader = new FileReader(tempFile);
        BufferedReader brT = new BufferedReader(tempReader);
        while (brT.ready()) {
            String line = brT.readLine();
            tempFileList.add(line);
        }
        brT.close();

        Assertions.assertArrayEquals(new List[]{tempFileList}, new List[]{currentFileList});
    }

    @Test
    public void intervalsNotOverlap() {
        ZonedDateTime start1 = ZonedDateTime.now();
        ZonedDateTime end1 = start1.plusHours(1);
        ZonedDateTime start2 = ZonedDateTime.now().plusDays(1);
        ZonedDateTime end2 = start2.plusHours(1);

        Task task1 = new Task("1", "1", StatusTask.NEW,taskManager.counterId());
        task1.setStartTime(start1);
        task1.setDuration(Duration.between(start1, end1));
        taskManager.createTask(task1);

        Task task2 = new Task("2", "2", StatusTask.NEW,taskManager.counterId());
        task2.setStartTime(start2);
        task2.setDuration(Duration.between(start2, end2));
        assertDoesNotThrow(() -> taskManager.createTask(task2));
    }

}
