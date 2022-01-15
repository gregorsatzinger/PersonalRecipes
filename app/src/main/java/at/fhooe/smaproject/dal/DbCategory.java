package at.fhooe.smaproject.dal;

public class DbCategory {
    private int id;
    private String name;

    public DbCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
