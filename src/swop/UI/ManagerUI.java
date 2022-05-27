package swop.UI;

import swop.Exceptions.CancelException;
import swop.Records.AllStats;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.ManagerGenerator;
import swop.Users.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static swop.UI.UI.scanner;

/**
 * A cli class used to implement manager's UI
 */
public class ManagerUI {

    private final static ManagerGenerator managerGenerator = new ManagerGenerator();

    private final static List<String> actions = List.of("Check Production Statistics", "Adapt Scheduling Algorithm", "Exit");

    private Manager manager;

    /**
     * Set this.manager to the give Manager
     * @param manager the given Manager
     */
    public void setManager(Manager manager){
        this.manager = manager;
    }

    /**
     * Start the main loop that interacts with the Manager
     * @param manager the Manager user to interact with the AssemAssist
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    public void run(Manager manager) throws CancelException {
        if (manager == null)
            throw new IllegalArgumentException("manager is null");
        this.setManager(manager);
        System.out.println("Welcome Manager, (You can cancel any action by typing: CANCEL)");
        this.selectAction();
    }

    /**
     * Function that handles selecting an action for Manager
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void selectAction() throws CancelException {
        if (this.manager == null) throw new IllegalStateException("no manager accessible");
        int action = UI.selectAction(managerGenerator, actions, "What would you like to do?");

        switch (action) {
            case 0 -> this.checkProductionStatistics();
            case 1 -> this.AdaptSchedulingAlgorithm();
            case 2 -> {
                // Do Nothing
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    /**
     * Ask the user which algorithm it would like to use and change the scheduling algorithm
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void AdaptSchedulingAlgorithm() throws CancelException {
        if (this.manager == null) throw new IllegalStateException("no manager accessible");
        List<String> algorithms = this.manager.getValidAlgorithms();
        List<String> options = new ArrayList<>(algorithms);
        options.add("Cancel selection");
        String active = this.manager.getActiveAlgorithm();
        int option = selectAction(options, active);

        switch (option) {
            case 0 -> this.changeAlgorithmToBatch();
            case 1 -> this.manager.setSchedulingAlgorithm("FIFO", null);
            case 2 -> {}
            default -> throw new IllegalArgumentException("Unexpected value: " + option);
        }
    }

    /**
     * Change the scheduling algorithm to batch
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void changeAlgorithmToBatch() throws CancelException {
        if (this.manager == null) throw new IllegalStateException("no manager accessible");
        List<Map<String, String>> possibleBatch = this.manager.getBatchOptions();
        if (!possibleBatch.isEmpty()) {
            Map<String, String> selection = getBatchSelection(possibleBatch);
            this.manager.setSchedulingAlgorithm("BATCH", selection);
        }
        else {
            printError("No batchoptions available -> Algorithm will stay FIFO");
        }
    }

    /**
     * check the production statistics on a given assemAssist
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private void checkProductionStatistics() throws CancelException {
        if (this.manager.getStats()==null) throw new IllegalStateException("manager not instantiated");
        showProductionStatistics(this.manager.getStats());
    }

    /**
     * Generates to production statistics
     * @param stats AllStats record with all statistics
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private static void showProductionStatistics(AllStats stats) throws CancelException {
        if (stats == null)
            throw new IllegalArgumentException("invalid statistics Allstats provided");
        DisplayStatus builder = new DisplayStatus();
        managerGenerator.generateProductionStatistics(builder, stats);
        System.out.println(builder.getDisplay());
        scanner.scanNextLineOfTypeString();
    }

    /**
     * Asks which action the user wants to take
     * @return int indicating the chosen action
     * @param actions available actions for the user
     * @param currentAlgo the current algorithm in use
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private static int selectAction(List<String> actions, String currentAlgo) throws CancelException {
        if (actions == null)
            throw new IllegalArgumentException("provided list of actions is null");
        if (currentAlgo == null)
            throw new IllegalArgumentException("null string provided");
        DisplayStatus builder = new DisplayStatus();
        managerGenerator.generateAlgorithmSelection(builder, actions, currentAlgo);
        System.out.println(builder.getDisplay());
        return scanner.scanNextLineOfTypeInt(0, actions.size());
    }

    /**
     * Display all batch options and get the selection
     * @param possibleBatch a list of all possible batches to display
     * @return the selected batchoption
     * @throws CancelException when a user wants to cancel his operation by typing "cancel"
     */
    private static Map<String,String> getBatchSelection(List<Map<String, String>> possibleBatch) throws CancelException {
        if (possibleBatch == null)
            throw new IllegalArgumentException("list of batchoptions is null");
        DisplayStatus builder = new DisplayStatus();
        managerGenerator.generateBatchSelection(builder, possibleBatch);
        System.out.println(builder.getDisplay());
        int selection = scanner.scanNextLineOfTypeInt(0, possibleBatch.size());
        System.out.println("Batch changed to: " + selection);
        return possibleBatch.get(selection);
    }

    /**
     * Prints an error message
     * @param e the error message to print
     */
    private static void printError(String e) {
        UI.printError(e);
    }
}
