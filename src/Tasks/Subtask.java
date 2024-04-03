package Tasks;

import Tasks.StatusTask;

public class Subtask extends Task {
    private int epicid;

    public Subtask(String name, String description, StatusTask status, int id, int epicid) {
        super(name,description,status,id);
        this.epicid = epicid;
    }

    public Integer getEpicid() {
        return epicid;
    }
}
