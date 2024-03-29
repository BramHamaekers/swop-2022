package swop.UI;

import swop.CarManufactoring.Task;
import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;
import swop.Users.CarMechanic;

import java.util.Arrays;
import java.util.List;

import static swop.UI.UI.scanner;

/**
 * A cli class used to implement car mechanic's UI
 */
public class CarMechanicUI {
    /**
     * The generator used in displaying information to the user
     */
    private final static CarMechanicGenerator cmGenerator = new CarMechanicGenerator();
    /**
     * A list of all actions a CarMechanic can perform
     */
    private final static List<String> actions = Arrays.asList("performAssemblyTask", "checkAssemblyLineStatus", "Exit");

    /**
     * A CarMechanic associated with this CarMechanicUI
     */
    private CarMechanic carmechanic;

    /**
     * Set this.carmechanic to the give CarMechanic
     * @param carmechanic the given CarMechanic
     */
    public void setCarMechanic(CarMechanic carmechanic){
        this.carmechanic = carmechanic;
    }

    /**
     * Start the main loop that interacts with the CarMechanic
     * @param carmechanic the carMechanic user to interact with the AssemAssist
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    public void run(CarMechanic carmechanic) throws CancelException {
        if (carmechanic == null)
            throw new IllegalArgumentException("carmechanic is null");

        System.out.println("Welcome Car Mechanic: (You can cancel any action by typing: CANCEL)");
        this.setCarMechanic(carmechanic);
        this.selectAction();
    }

    /**
     * Function that handles selecting an action for CarMechanic
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void selectAction() throws CancelException {
        int action = UI.selectAction(cmGenerator, actions, "What would you like to do?");

        switch (action) {
            case 0 -> this.performAssemblyTask();
            case 1 -> this.checkAssemblyLineStatus();
            case 2 -> {
                // Do Nothing
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    /**
     * Helper function to perform a task for this mechanic
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void performAssemblyTask() throws CancelException {
        String workstationName = this.selectStation();
        if (workstationName == null) throw new IllegalArgumentException("workstation is invalid");
        this.performTaskAtWorkStation(workstationName);
    }

    /**
     * Helper function to display all tasks for each station
     */
    private void checkAssemblyLineStatus() {
        List<String> workStations = this.carmechanic.getStationNames();

        workStations.forEach(station -> {
            List<Task> pendingTasks = this.carmechanic.getUncompletedTasks(station);
            List<Task> finishedTasks = this.carmechanic.getCompletedTasks(station);
            displayStationStatus(station, pendingTasks, finishedTasks);
        });
    }

    /**
     * Display the status of a station including pending tasks and finished tasks
     * @param stationName the given workstation to print the status of
     * @param pendingTasks the pending tasks on the given workstation
     * @param finishedTasks the finished tasks on the given workstation
     */
    private static void displayStationStatus(String stationName, List<Task> pendingTasks, List<Task> finishedTasks) {
        if (stationName == null)
            throw new IllegalArgumentException("null string provided");
        if (pendingTasks == null)
            throw new IllegalArgumentException("list of pending tasks provided is null");
        if (finishedTasks == null)
            throw new IllegalArgumentException("list of finished tasks provided is null");

        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateWorkStationStatus(builder, stationName, pendingTasks, finishedTasks);
        System.out.print(builder.getDisplay());
    }

    /**
     * Select a station for the car mechanic to work at.
     * @return return a station selected from the available stations
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private String selectStation() throws CancelException {
        List<String> workStations = this.carmechanic.getStationNames();
        //asks user for workstation
        displayAvailableStations(workStations);
        int option = askOption("Select station: ", workStations.size());
        return workStations.get(option);
    }

    /**
     * Display all the workstations of the given station list
     * @param stationNames the names of all the workstations
     */
    private static void displayAvailableStations(List<String> stationNames) {
        if (stationNames == null)
            throw new IllegalArgumentException("list of station names is null");
        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateStationList(builder, stationNames);
        System.out.print(builder.getDisplay());
    }

    /**
     * Get the input from the user
     * @param s a string to display
     * @param numberOfOptions the number of options as an integer
     * @return return the input from the user
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private static int askOption(String s, int numberOfOptions) throws CancelException {
        if (numberOfOptions < 0)
            throw new IllegalArgumentException("negative number of options");
        return scanner.scanNextLineOfTypeInt(0, numberOfOptions);
    }

    /**
     * Allows user to perform tasks at a given workstation on a given central system
     * @param stationName the name of the workstation the user wants to perform tasks on
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void performTaskAtWorkStation(String stationName) throws CancelException {
        if (stationName == null)
            throw new IllegalArgumentException("null string provided");
        List<Task> taskList = this.carmechanic.getUncompletedTasks(stationName);
        //returns selected task by user
        Task task = this.selectTask(taskList);
        //return list of all the tasks
        if (task != null) {
            //Show the information for given task 2 user
            this.showInfo(task);
            this.completeTask(stationName, task);
            this.performTaskAtWorkStation(stationName);
        }
        else {
            noTasks();
        }
    }
    /**
     * Selects a task from available task list.
     * @param taskList List of all available tasks
     * @return return a task selected from the available tasks
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private Task selectTask(List<Task> taskList) throws CancelException {
        if (taskList == null || taskList.isEmpty()) return null;
        displayAvailableTasks(taskList);
        int option = askOption("Select task: ", taskList.size());
        return taskList.get(option);
    }

    /**
     * Display all tasks of the given tasklist
     * @param taskList the given taskList
     */
    private static void displayAvailableTasks(List<Task> taskList) {
        if (taskList == null)
            throw new IllegalArgumentException("list of tasks is null");
        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateAvailableTasks(builder, taskList);
        System.out.print(builder.getDisplay());
    }

    /**
     * Shows info of given task
     * 	this consists of instruction of each part + value concerning current task.
     * @param task task from the tasklist
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void showInfo(Task task) throws CancelException {
        if (task == null) throw new IllegalArgumentException("provided task is null");
        String info = task.getDescription();
        displayTaskInfo(info);
    }

    /**
     * display taskInfo
     * @param info the given info
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private static void displayTaskInfo(String info) throws CancelException {
        if (info == null)
            throw new IllegalArgumentException("null string provided");
        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateTaskInfo(builder, info);
        System.out.println(builder.getDisplay());
        scanner.scanNextLineOfTypeString();
    }

    /**
     * Helper function to complete a task in assemAssist
     * @param workstationName Name of the workstation where the task is to be performed
     * @param task task from the tasklist
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void completeTask(String workstationName, Task task) throws CancelException {
        if (task == null) throw new IllegalArgumentException("provided task is null");
        if (workstationName == null) throw new IllegalArgumentException("workstation is invalid");
        int time = askTimeToCompleteTask();
        this.carmechanic.completeTask(workstationName ,task.getName(), time);
    }

    /**
     * Ask the CarMechanic how long it took him to complete a task
     * @return scanner.scanNextLineOfTypeInt();
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private static int askTimeToCompleteTask() throws CancelException {
        System.out.println("How much time did it take to finish the task? (in min)");
        return scanner.scanNextLineOfTypeInt();
    }

    /**
     * Display that no tasks need to be completed
     */
    private static void noTasks() {
        System.out.println("No tasks need to be completed!");

    }
}
