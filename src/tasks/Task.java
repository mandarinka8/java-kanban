package tasks;

import managers.TaskType;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

public class Task {
     private String name;
     private String description;
     private StatusTask status;
     private int id;
     private Duration duration;
     private ZonedDateTime startTime;
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

    public Duration getDuration() {
        return duration;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public int setId(int i) {
        return id;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public  ZonedDateTime getEndTime() {
        if (Optional.ofNullable(startTime).isPresent()) {
            if (Optional.ofNullable(duration).isPresent()) {
                return startTime.plus(duration);
            } else {
                return ZonedDateTime.of(startTime.toLocalDateTime(), startTime.getZone());
            }
        } else {
            return null;
        }
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

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", taskType=" + taskType +
                '}';
    }
}

