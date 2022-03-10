package swop.Exceptions;

public class NotAllTasksCompleteException extends Exception {
    private final String workstation;
    public NotAllTasksCompleteException(String errorMessage, String workstation) {
        super(errorMessage);
        this.workstation = workstation;
    }

    public String getWorkstation() {
        return workstation;
    }
}
