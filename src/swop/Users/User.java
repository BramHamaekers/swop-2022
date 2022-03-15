package swop.Users;


import swop.Main.AssemAssist;

public abstract class User {

    private String id;

    public User(String id) {
        this.id = id;
    }

    public abstract void load(AssemAssist assemAssist);

    public String getId() {
        return id;
    }

    protected void logout() {}
}
