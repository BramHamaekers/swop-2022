package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * The InstallGearbox Task corresponds to the installation of the gearbox on a car
 */
public class InstallGearbox extends Task {

    /**
     * Initializes a InstallGearbox task
     * @param chosenOption the option for the gearbox
     */
    public InstallGearbox(String chosenOption) {
        super("Install Gearbox", "Insert gearbox of type: ",chosenOption);
    }
}

