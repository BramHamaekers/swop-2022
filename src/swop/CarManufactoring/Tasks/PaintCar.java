package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * PaintCar task corresponds to the color of a car
 */
public class PaintCar extends Task {

    /**
     * Initializes a PaintCar task
     * @param chosenOption the option for the color
     */
    public PaintCar(String chosenOption) {
        super("Paint Car", "Paint the body in colour: ", chosenOption);
    }
}
