package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * The InstallAirco Task corresponds to the installation of the airco in a car
 */
public class InstallAirco extends Task {

    /**
     * Initializes a InstallAirco task
     * @param chosenOption the option for the airco
     */
    public InstallAirco(String chosenOption) {
        super("Install Airco", "Install airco of type: ",chosenOption);
    }
}
