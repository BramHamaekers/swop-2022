
package swop.Tests.UnitTests.Car;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.Car.CarOrder;
import swop.CarManufactoring.Task;
import swop.Miscellaneous.TimeStamp;

import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class CarOrderTest {
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
    void isCompleted() {
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        Car car = carOrder.getCar();
        assertFalse(carOrder.isCompleted());
        car.getUncompletedTasks().forEach(Task::complete); // Car order is completed if car is completed
        assertTrue(carOrder.isCompleted());
    }

    @Test
    void getID() {
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        assertEquals(7,carOrder.getID().length());
    }

    @Test
    void getEstimatedCompletionTime() {
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        Car car = carOrder.getCar();

        car.setEstimatedCompletionTime(new TimeStamp(1,100)); // CarOrder should have same est completion time as car
        assertEquals(new TimeStamp(1,100),carOrder.getEstimatedCompletionTime());
    }

    @Test
    void testToString() {
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        assertEquals(carOrder.toString(),String.format("specification: %s %n" +
                        "timestamp of ordering: %s %n" +
                        "Estimated Completion Time: %s",
                carOrder.getCar().getCarModel().getCarModelSpecification().getAllParts(),
                carOrder.getOrderTime(),
                carOrder.getCar().getEstimatedCompletionTime()));
        carOrder.getCar().getUncompletedTasks().forEach(Task::complete);
        assertEquals(carOrder.toString(),String.format("specification: %s %n" +
                        "timestamp of ordering: %s %n" +
                        "Completion Time: %s",
                carOrder.getCar().getCarModel().getCarModelSpecification().getAllParts(),
                carOrder.getOrderTime(),
                carOrder.getCompletionTime()));
    }
}

