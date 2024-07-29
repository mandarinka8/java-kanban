package tasks;

import managers.TaskType;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Epic extends Task {

   private  ArrayList<Integer> subTaskIds = new ArrayList<>();
    private ZonedDateTime endTime;

    public Epic(String name, String description, StatusTask status, int id) {
        super(name,description,status,id);
        this.taskType = TaskType.EPIC;

    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }


    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }
}
