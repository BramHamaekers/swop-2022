package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InsertEngine extends Task {

    public InsertEngine(String chosenOption) {
        super("Insert Engine", "Insert engine of type: ", chosenOption);
    }
}
