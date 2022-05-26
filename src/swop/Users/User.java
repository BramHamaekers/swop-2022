package swop.Users;

import swop.Main.AssemAssist;

/**
 * This is an abstract class: garage holder, manager and car mechanic are subclasses of user
 */
public abstract class User {
    private final String id;
    protected final AssemAssist assemAssist;

    /**
     * Create a new user with given id name
     * @param id is string of name
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
