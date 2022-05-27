package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * The MountWheels task, corresponds to the wheel choice
 */
public class MountWheels extends Task {

    /**
     * Initializes a MountWheels task
     * @param chosenOption the option for the wheels
     */
    public MountWheels(String chosenOption) {
        super("Mount Wheels", "Mount wheels of type: ", chosenOption);
    }
}
