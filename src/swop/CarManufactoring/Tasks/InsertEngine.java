package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * The InsertEngine Task corresponds to the engine which has to be lifted into the carbody
 */
public class InsertEngine extends Task {

    /**
     * Initializes a InsertEngine task
     * @param chosenOption the option for the engine
     */
    public InsertEngine(String chosenOption) {
        super("Insert Engine", "Insert engine of type: ", chosenOption);
    }
}
