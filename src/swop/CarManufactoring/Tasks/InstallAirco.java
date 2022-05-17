package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallAirco extends Task {

    public InstallAirco(String chosenOption) {
        this.name = "Install Airco";
        this.description = "Install airco of type: ";
        this.chosenOption = chosenOption;
    }
}
