package swop.Users;

import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;

public abstract class User {
    private final String id;

    public User(String id) {
        this.id = id;
    }

    /**
     * load a user on a login
     * @param assemAssist given the main program
     */
    public abstract void load(AssemAssist assemAssist);

    /**
     * select an action for the relevant user
     * @param assemAssist given the main program
     * @throws CancelException gets thrown when user wants to cancel
     */
    public abstract void selectAction(AssemAssist assemAssist) throws CancelException;

    /**
     * get the UserID of this user
     * @return this.id
     */
    public String getId() {
        return id;
    }
}
