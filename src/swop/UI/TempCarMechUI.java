package swop.UI;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.CarMechanicGenerator;
import swop.Users.CarMechanic;

import java.util.Arrays;
import java.util.List;

import static swop.UI.UI.scanner;

public class TempCarMechUI {
    private final static CarMechanicGenerator cmGenerator = new CarMechanicGenerator();

    private final static List<String> actions = Arrays.asList("performAssemblyTask", "checkAssemblyLineStatus", "Exit");

    private CarMechanic carmechanic;

    public void setCarMechanic(CarMechanic carmechanic){
        this.carmechanic = carmechanic;
    }

    public void run(CarMechanic carmechanic) throws CancelException {
        if (carmechanic == null)
            throw new IllegalArgumentException("carmechanic is null");

        System.out.println("Welcome Car Mechanic: (You can cancel any action by typing: CANCEL)");
        this.setCarMechanic(carmechanic);
        this.selectAction();
    }

    /**
     * Function that handles selecting an action for CarMechanic
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
     * @throws CancelException when "CANCEL" is the input
     */
    private void performAssemblyTask() throws CancelException {
        WorkStation workStation = this.selectStation();
        if (workStation == null) throw new IllegalArgumentException("workstation is invalid");
        this.performTaskAtWorkStation(workStation);
    }

    /**
     * Helper function to display all tasks for each station
     * @param assemAssist given the main program
     */
    private void checkAssemblyLineStatus() {
        List<WorkStation> workStations = this.carmechanic.getStations();

        workStations.forEach(station -> {
            List<Task> pendingTasks = station.getUncompletedTasks();
            List<Task> finishedTasks = station.getCompletedTasks();
            displayStationStatus(station, pendingTasks, finishedTasks);
        });
    }

    /**
     * Display the status of a station including pending tasks and finished tasks
     * @param workStation the given workstation to print the status of
     * @param pendingTasks the pending tasks on the given workstation
     * @param finishedTasks the finished tasks on the given workstation
     */
    public static void displayStationStatus(WorkStation workStation, List<Task> pendingTasks, List<Task> finishedTasks) {
        //TODO defensive
        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateWorkStationStatus(builder, workStation.getName(), pendingTasks, finishedTasks);
        System.out.print(builder.getDisplay());
    }

    /**
     * Select a station for the car mechanic to work at.
     * @return return a station selected from the available stations
     * @throws CancelException when "CANCEL" is the input
     */
    private WorkStation selectStation() throws CancelException {
        List<WorkStation> workStations = this.carmechanic.getStations();
        //asks user for workstation
        displayAvailableStations(workStations);
        int option = askOption("Select station: ", workStations.size());
        return workStations.get(option);
    }

    /**
     * Display all the workstations of the given station list
     * @param stations the given list
     */
    private static void displayAvailableStations(List<WorkStation> stations) {
        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateStationList(builder, stations);
        System.out.print(builder.getDisplay());
    }

    /**
     * Get the input from the user
     * @param s a string to display
     * @param numberOfOptions the number of options as an integer
     * @return return the input from the user
     * @throws CancelException if the user types "cancel"
     */
    private static int askOption(String s, int numberOfOptions) throws CancelException {
        return scanner.scanNextLineOfTypeInt(0, numberOfOptions);
    }

    /**
     * Allows user to perform tasks at a given workstation on a given central system
     * @param workStation the workstation the user wants to perform tasks on
     * @throws CancelException when the user types "CANCEL"
     */
    private void performTaskAtWorkStation(WorkStation workStation) throws CancelException {
        //TODO: check if coupling
        List<Task> taskList = workStation.getUncompletedTasks();
        //returns selected task by user
        Task task = this.selectTask(taskList);
        //return list of all the tasks
        if (task != null) {
            //Show the information for given task 2 user
            this.showInfo(task);
            this.completeTask(workStation, task);
            this.performTaskAtWorkStation(workStation);
        }
        else {
            CarMechanicUI.noTasks();
        }
    }
    /**
     * Selects a task from available task list.
     * @param taskList List of all available tasks
     * @return return a task selected from the available tasks
     * @throws CancelException when "CANCEL" is the input
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
        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateAvailableTasks(builder, taskList);
        System.out.print(builder.getDisplay());
    }

    /**
     * Shows info of given task
     * 	this consists of instruction of each part + value concerning current task.
     * @param task task from the tasklist
     * @throws CancelException when "CANCEL" is the input
     */
    private void showInfo(Task task) throws CancelException {
        if (task == null) throw new IllegalArgumentException("task is null");
        String info = task.getDescription();
        displayTaskInfo(info);
    }

    /**
     * display taskInfo
     * @param info the given info
     * @throws CancelException when user types "CANCEL"
     */
    private static void displayTaskInfo(String info) throws CancelException {
        DisplayStatus builder = new DisplayStatus();
        cmGenerator.generateTaskInfo(builder, info);
        System.out.println(builder.getDisplay());
        scanner.scanNextLineOfTypeString();
    }

    /**
     * Helper function to complete a task in assemAssist
     * @param workStation to perform the task at
     * @param task task from the tasklist
     * @throws CancelException when "CANCEL" is the input
     */
    private void completeTask(WorkStation workStation, Task task) throws CancelException {
        if (task == null) throw new IllegalArgumentException("task is null");
        if (workStation == null) throw new IllegalArgumentException("workstation is null");
        int time = CarMechanicUI.askTimeToCompleteTask();
        workStation.completeTask(task, time);
    }
}
