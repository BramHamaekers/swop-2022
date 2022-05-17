package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class MountWheels extends Task {

    public MountWheels(String chosenOption) {
        this.name = "Mount Wheels";
        this.description = "Mount wheels of type: ";
        this.chosenOption = chosenOption;
    }
}
