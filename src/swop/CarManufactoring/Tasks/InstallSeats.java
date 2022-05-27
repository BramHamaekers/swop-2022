package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallSeats extends Task {

    public InstallSeats(String chosenOption) {
        super("Install Seats", "Install seats of type: ", chosenOption);
    }
}
