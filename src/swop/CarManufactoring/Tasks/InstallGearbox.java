package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallGearbox extends Task {

    public InstallGearbox(String chosenOption) {
        super("Install Gearbox", "Insert gearbox of type: ",chosenOption);
    }
}

