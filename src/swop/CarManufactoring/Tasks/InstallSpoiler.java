package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class InstallSpoiler extends Task {

    public InstallSpoiler(String chosenOption) {
        super("Install Spoiler", "Install spoiler of type: ", chosenOption);
    }
}
