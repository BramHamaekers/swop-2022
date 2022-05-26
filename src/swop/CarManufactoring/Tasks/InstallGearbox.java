package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallGearbox extends Task {

    public InstallGearbox(String chosenOption) {
        if (chosenOption == null)
            throw new IllegalArgumentException("Not a valid option for gearbox");
        this.name = "Install Gearbox";
        this.description = "Insert gearbox of type: ";
        this.chosenOption = chosenOption;
    }
}

