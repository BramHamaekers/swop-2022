package swop.Users;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;

public abstract class User {
    private final String id;

    public User(String id) {
        this.id = id;
    }

    public abstract void load(AssemAssist assemAssist);
    public abstract void selectAction(AssemAssist assemAssist) throws CancelException;

    /**
     * get the UserID of this user
     * @return this.id
     */
    public String getId() {
        return id;
    }
}
