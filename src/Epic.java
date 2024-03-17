import java.util.ArrayList;
public class Epic extends Task {

   private  ArrayList<Long> subTaskIds = new ArrayList<>();
    public Epic(String name,String description,StatusTask status,int id,ArrayList<Long> subTaskIds) {
        super(name,description,status,id );
        this.subTaskIds = subTaskIds;
    }
    public ArrayList<Long> getSubTaskIds() {
        return subTaskIds;
    }
}
