package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class PaintCar extends Task {

    /**
     * Initializes a PaintCar task
     * @param chosenOption
     */
    public PaintCar(String chosenOption) {
        super("Paint Car", "Paint the body in colour: ", chosenOption);
    }
}
