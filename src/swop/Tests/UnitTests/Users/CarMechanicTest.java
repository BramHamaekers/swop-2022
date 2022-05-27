package swop.Tests.UnitTests.Users;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.Car.CarOrder;
import swop.CarManufactoring.CarManufacturingController;
import swop.Main.AssemAssist;
import swop.Users.CarMechanic;
import swop.Users.GarageHolder;

import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarMechanicTest {
    AssemAssist assem = new AssemAssist();
    Map<String, String> validOptions = Map.ofEntries(
            entry("Body", "sedan"),
            entry("Color", "red"),
            entry("Engine", "standard 2l v4"),
            entry("Gearbox", "6 speed manual"),
            entry("Seats", "leather white"),
            entry("Airco", "manual"),
            entry("Wheels", "winter"));
    CarModelSpecification specification = new CarModelSpecification(validOptions);
    CarModel modelA = new ModelA();

    @Test
    void getUncompletedTasks_invalidStationNames() {
        CarMechanic carMechanic = new CarMechanic("b", assem);
        assertThrows(IllegalArgumentException.class, () -> carMechanic.getUncompletedTasks(null));
        assertThrows(IllegalArgumentException.class, () -> carMechanic.getUncompletedTasks("INVALID"));
    }

    @Test
    void getCompletedTasks_invalidStationNames() {
        CarMechanic carMechanic = new CarMechanic("b", assem);
        assertThrows(IllegalArgumentException.class, () -> carMechanic.getCompletedTasks(null));
        assertThrows(IllegalArgumentException.class, () -> carMechanic.getCompletedTasks("INVALID"));
    }

    @Test
    void completeTask_NullArguments() {
        CarMechanic carMechanic = new CarMechanic("b", assem);
        assertThrows(IllegalArgumentException.class, () -> carMechanic.completeTask(null, "TASK", 0));
        assertThrows(IllegalArgumentException.class, () -> carMechanic.completeTask("STATION", null, 0));
    }

    @Test
    void completeTask_TaskDoesNotExistAtStation() {
        CarMechanic carMechanic = new CarMechanic("b", assem);
        assertThrows(IllegalArgumentException.class, () -> carMechanic.completeTask("Car Body Post", "TASK", 0));
    }

    @Test
    void completeTask_NegativeTimePast() {
        CarManufacturingController carManufacturingController = assem.getController();

        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        CarMechanic carMechanic = new CarMechanic("b", assem);
        carManufacturingController.addOrderToQueue(carOrder);
        assertThrows(IllegalArgumentException.class, () -> carMechanic.completeTask("Car Body Post", "Assembly Car Body", -1));
    }

}
