package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InsertEngine extends Task {

    public InsertEngine(String chosenOption) {
        this.name = "Insert Engine";
        this.description = "Insert engine of type: ";
        this.chosenOption = chosenOption;
    }
}
