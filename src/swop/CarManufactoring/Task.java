package swop.CarManufactoring;

/**
 * The superclass Task implements common methods for the specific tasks
 */
public abstract class Task implements Cloneable {
    /**
     * The name of this task
     */
    protected String name;
    /**
     * The description for this task
     */
    protected String description;
    /**
     * The option that was chosen for the part associated with this task
     */
    protected String chosenOption;
    /**
     * A boolean indicating if this task was completed or not
     */
    protected boolean isComplete = false;

    /**
     * Initialize a Task with a name, description and the chosenOption
     * @param name The name of the task
     * @param description The description of the task, will help the mechanic in the assembly
     * @param chosenOption The chosen option for a part category
     */
    public Task(String name, String description, String chosenOption) {
        if (description == null)
            throw new IllegalArgumentException("Null string provided");
        if (name == null)
            throw new IllegalArgumentException("Null string provided");
        if (chosenOption == null)
            throw new IllegalArgumentException("Null string provided");
        this.name = name;
        this.description = description;
        this.chosenOption = chosenOption;
    }

    /**
     * Set this.isComplete equal to true.
     */
    public void complete() {
        if (this.isComplete)
            throw new IllegalStateException("Task is already finished");
        this.isComplete = true;
    }

    /**
     * Check if this task is complete or not.
     * @return boolean value of this.isComplete
     */
    public boolean isComplete() {
        return this.isComplete;
    }

    /**
     * Returns the name of this task
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the description of this task
     * @return this.description
     */
    public String getDescription() {
        return this.description + this.getChosenOption();
    }

    /**
     * Returns the chosenOption for the part associated with this task
     * @return this.chosenOption
     */
    public String getChosenOption() {
        return this.chosenOption;
    }

    @Override
    public Task clone() {
        try {
            Task clone = (Task) super.clone();
            clone.name = this.name;
            clone.description = this.description;
            clone.chosenOption = this.chosenOption;
            clone.isComplete = this.isComplete;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
