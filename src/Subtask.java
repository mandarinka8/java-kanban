public class Subtask extends Task {

    private Long epicid;
    public Subtask(String name,String description,StatusTask status,int id, Long epicid) {
        super(name,description,status,id);
        this.epicid = epicid;
    }

    public Long getEpicid() {
        return epicid;
    }
}
