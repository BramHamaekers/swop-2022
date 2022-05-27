package swop.Users;

import swop.Main.AssemAssist;

/**
 * This is an abstract class: garage holder, manager and car mechanic are subclasses of user
 */
public abstract class User {
    /**
     * The ID of this user, used for logging in.
     */
    private final String id;
    /**
     * The assemAssist this user interacts with
     */
    protected final AssemAssist assemAssist;

    /**
     * Create a new user with given id name
     * @param id is string of name
     * @param assemAssist the given assemassist used to communicate with the assemblyline and the controller etc.
     */
    public User(String id, AssemAssist assemAssist) {
        if (id == null)
            throw new IllegalArgumentException("invalid id provided");
        if (assemAssist == null)
            throw new IllegalArgumentException("invalid assemAssist provided");
        this.id = id;
        this.assemAssist = assemAssist;
    }

    /**
     * get the UserID of this user
     * @return this.id
     */
    public String getId() {
        return id;
    }
}
