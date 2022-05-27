package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * The InstallSeats Task corresponds to the installation of the seats in a car
 */
public class InstallSeats extends Task {

    /**
     * Initializes a InstallSeats task
     * @param chosenOption the option for the seats
     */
    public InstallSeats(String chosenOption) {
        super("Install Seats", "Install seats of type: ", chosenOption);
    }
}
