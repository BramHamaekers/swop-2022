package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * The InstallSpoiler Task corresponds to the installation spoiler of a car
 */
public class InstallSpoiler extends Task {

    /**
     * Initializes a InstallSpoiler task
     * @param chosenOption the option for the spoiler
     */
    public InstallSpoiler(String chosenOption) {
        super("Install Spoiler", "Install spoiler of type: ", chosenOption);
    }
}
