package swop.CarManufactoring;

public abstract class Task {
    protected String name;
    protected String description;
    protected String chosenOption;
    protected boolean isComplete = false;

    /**
     * Set this.isComplete equal to true.
     */
    public void complete() {
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
        return this.description;
    }

    /**
     * Returns the chosenOption for the part associated with this task
     * @return this.chosenOption
     */
    public String getChosenOption() {
        return this.chosenOption;
    }
}
