package tasks;

import managers.TaskType;
import java.util.Objects;


public class Task {
     private String name;
     private String description;
     private StatusTask status;
     private int id;
    protected TaskType taskType;


    public Task(String name,String description,StatusTask status,int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
        this.taskType = TaskType.TASK;
    }



    public  String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public StatusTask getStatus() {
        return status;
    }


    public int getId() {
        return id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int setId(int i) {
        return id;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

