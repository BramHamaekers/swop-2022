package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallSeats extends Task {

    public InstallSeats(String chosenOption) {
        this.name = "Install Seats";
        this.description = "Install seats of type: ";
        this.chosenOption = chosenOption;
    }
}
