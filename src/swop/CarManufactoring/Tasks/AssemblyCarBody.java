package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class AssemblyCarBody extends Task {

    public AssemblyCarBody(String chosenOption) {
        if (chosenOption == null)
            throw new IllegalArgumentException("not a valid option for car body");
        this.name = "Assembly Car Body";
        this.description = "Mount a body on the chassis of type: ";
        this.chosenOption = chosenOption;
    }
}
