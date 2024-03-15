public class Task {
    final private String name;
    final private String description;
    final private StatusTask status;
    final private int id;


    public Task(String name,String description,StatusTask status,int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
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
}

