package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class AssemblyCarBody extends Task {

    public AssemblyCarBody(String chosenOption) {
        //TODO: check if String is null, for all tasks
        this.name = "Assembly Car Body";
        this.description = "Mount a body on the chassis of type: ";
        this.chosenOption = chosenOption;
    }
}
