package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallAirco extends Task {

    public InstallAirco(String chosenOption) {
        if (chosenOption == null)
            throw new IllegalArgumentException("Not a valid option for airco");
        this.name = "Install Airco";
        this.description = "Install airco of type: ";
        this.chosenOption = chosenOption;
    }
}
