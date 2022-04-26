package swop.Exceptions;

import java.util.List;


public class NotAllTasksCompleteException extends Exception {
    private final List<String> workstations;
    public NotAllTasksCompleteException(String errorMessage, List<String> w) {
        super(errorMessage);
        this.workstations = w;
    }
}
