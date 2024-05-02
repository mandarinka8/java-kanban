package tasks;

import java.util.ArrayList;
public class Epic extends Task {

   private  ArrayList<Integer> subTaskIds = new ArrayList<>();
    public Epic(String name, String description, StatusTask status, int id) {
        super(name,description,status,id );

    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

}
