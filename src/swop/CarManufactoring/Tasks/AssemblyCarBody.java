package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class AssemblyCarBody extends Task {

    public AssemblyCarBody(String chosenOption) {
        super("Assembly Car Body", "Mount a body on the chassis of type: ", chosenOption);
    }
}
