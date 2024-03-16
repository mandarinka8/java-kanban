import java.util.ArrayList;
public class Epic extends Task {

    final ArrayList<Long> subTaskIds = new ArrayList<>();
    public Epic(String name,String description,StatusTask status,int id) {
        super(name,description,status,id );
    }
}
