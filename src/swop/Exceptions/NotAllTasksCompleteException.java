package swop.Exceptions;

import java.util.List;

/**
 * If the controller tries to advance but not all tasks are completed
 */
public class NotAllTasksCompleteException extends Exception {
    /**
     * The workstation that not all tasks were completed at when this exception was thrown
     */
    private final List<String> workstations;

    /**
     * If the controller tries to advance but not all tasks are completed
     * @param errorMessage a string containing the message
     * @param w a list of workstation names
     */
    public NotAllTasksCompleteException(String errorMessage, List<String> w) {
        super(errorMessage);
        this.workstations = w;
    }
}
