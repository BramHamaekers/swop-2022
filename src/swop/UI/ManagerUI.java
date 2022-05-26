package swop.UI;

import swop.Exceptions.CancelException;
import swop.Records.AllStats;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.ManagerGenerator;
import swop.Users.Manager;

import java.util.List;
import java.util.Map;

import static swop.UI.UI.scanner;

public class ManagerUI {

    private final static ManagerGenerator managerGenerator = new ManagerGenerator();

    private final static List<String> actions = List.of("Check Production Statistics", "Adapt Scheduling Algorithm", "Exit");

    private Manager manager;

    public void setManager(Manager manager){
        this.manager = manager;
    }

    public void run(Manager manager) throws CancelException {
        if (manager == null)
            throw new IllegalArgumentException("manager is null");
        this.setManager(manager);
        System.out.println("Welcome Manager, (You can cancel any action by typing: CANCEL)");
        this.selectAction();
    }

    /**
     * Function that handles selecting an action for Manager
     * @throws CancelException
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
     * @throws CancelException when the user types 'cancel'
     */
    private void AdaptSchedulingAlgorithm() throws CancelException {
        if (this.manager == null) throw new IllegalStateException("no manager accessible");
        List<String> algorithms = this.manager.getValidAlgorithms();
        String active = this.manager.getActiveAlgorithm();
        int option = selectAction(algorithms, active);

        switch (option) {
            case 0 -> this.changeAlgorithmToBatch();
            case 1 -> this.manager.setSchedulingAlgorithm("FIFO", null);
            case 2 -> {}
            default -> throw new IllegalArgumentException("Unexpected value: " + option);
        }
    }

    /**
     * Change the scheduling algorithm to batch
     * @throws CancelException when the user types "cancel"
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
     * @throws CancelException when the user types "CANCEL"
     */
    private void checkProductionStatistics() throws CancelException {
        if (this.manager.getStats()==null) throw new IllegalStateException("manager not instantiated");
        showProductionStatistics(this.manager.getStats());
    }

    /**
     * Generates to production statistics
     * @param stats AllStats record with all statistics
     * @throws CancelException when the user types 'Cancel'
     */
    private static void showProductionStatistics(AllStats stats) throws CancelException {
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
     * @throws CancelException when the user types 'Cancel'
     */
    private static int selectAction(List<String> actions, String currentAlgo) throws CancelException {
        DisplayStatus builder = new DisplayStatus();
        managerGenerator.generateAlgorithmSelection(builder, actions, currentAlgo);
        System.out.println(builder.getDisplay());
        //TODO: check if we cant move these to generator aswell
        return scanner.scanNextLineOfTypeInt(0, actions.size());
    }

    /**
     * Display all batch options and get the selection
     * @param possibleBatch a list of all possible batches to display
     * @return the selected batchoption
     * @throws CancelException when the user types 'Cancel'
     */
    private static Map<String,String> getBatchSelection(List<Map<String, String>> possibleBatch) throws CancelException {
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
