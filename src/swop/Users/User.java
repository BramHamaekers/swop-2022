package swop.Users;


public abstract class User {

    private String id;

    public User(String id) {
        this.id = id;
    }

    public void load(){}

    public String getId() {
        return id;
    }
}
