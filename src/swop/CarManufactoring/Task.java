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
}
