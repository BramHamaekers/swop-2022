package swop.Users;


import swop.Main.AssemAssist;

import java.util.Objects;

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

    protected boolean isValidYesNo(String action) {
        return Objects.equals(action, "y") || Objects.equals(action, "n");
    }
}
