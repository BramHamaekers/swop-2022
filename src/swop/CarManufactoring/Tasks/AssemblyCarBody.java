package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

/**
 * The AssemblyCarBody Task corresponds to the assembly of the different chassis parts on a car
 */
public class AssemblyCarBody extends Task {

    /**
     * Initializes a AssemblyCarBody task
     * @param chosenOption the option for the carbody
     */
    public AssemblyCarBody(String chosenOption) {
        super("Assembly Car Body", "Mount a body on the chassis of type: ", chosenOption);
    }
}
