package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class MountWheels extends Task {

    public MountWheels(String chosenOption) {
        super("Mount Wheels", "Mount wheels of type: ", chosenOption);
    }
}
