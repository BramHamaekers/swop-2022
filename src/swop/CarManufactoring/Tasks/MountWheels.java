package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class MountWheels extends Task {

    public MountWheels(String chosenOption) {
        if (chosenOption == null)
            throw new IllegalArgumentException("Not a valid option for wheels");
        this.name = "Mount Wheels";
        this.description = "Mount wheels of type: ";
        this.chosenOption = chosenOption;
    }
}
