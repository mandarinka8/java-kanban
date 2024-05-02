package test;

import static org.junit.jupiter.api.Assertions.*;
import tasks.*;
import managers.*;
import managers.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestTaskManager {

    InMemoryTaskManager taskManager = new InMemoryTaskManager(Managers.getDefaultHistory());
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();


    @Test
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
    }

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

}