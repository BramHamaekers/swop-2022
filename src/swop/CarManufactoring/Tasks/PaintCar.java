package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class PaintCar extends Task {

    public PaintCar(String chosenOption) {
        this.name = "Paint Car";
        this.description = "Paint the body in colour: ";
        this.chosenOption = chosenOption;
    }
}
