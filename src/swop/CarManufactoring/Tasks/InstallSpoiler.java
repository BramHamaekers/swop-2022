package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallSpoiler extends Task {

    public InstallSpoiler(String chosenOption) {
        if (chosenOption == null)
            throw new IllegalArgumentException("not a valid option for spoiler");
        this.name = "Install Spoiler";
        this.description = "Install spoiler of type: ";
        this.chosenOption = chosenOption;
    }
}
