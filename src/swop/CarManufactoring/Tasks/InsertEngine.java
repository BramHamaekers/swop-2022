package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InsertEngine extends Task {

    public InsertEngine(String chosenOption) {
        if (chosenOption == null)
            throw new IllegalArgumentException("Not a valid option for engine");
        this.name = "Insert Engine";
        this.description = "Insert engine of type: ";
        this.chosenOption = chosenOption;
    }
}
