package swop.CarManufactoring.Tasks;

import swop.CarManufactoring.Task;

public class AssemblyCarBody extends Task {

    AssemblyCarBody(String chosenOption) {
        this.name = "Assembly Car Body";
        this.description = "Mount a body on the chassis of type: ";
        this.chosenOption = chosenOption;
    }
}
