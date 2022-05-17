
package swop.Tests.UnitTests.Car;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.CarManufactoring.Task;
import swop.CarManufactoring.Tasks.AssemblyCarBody;
import swop.CarManufactoring.Tasks.MountWheels;
import swop.Miscellaneous.TimeStamp;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

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
    void isCompleted_False() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertFalse(car.isCompleted());
    }

    @Test
    void getUncompletedTasks() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertEquals(7, car.getUncompletedTasks().size());
    }

    @Test
    void getUncompletedTasks_OneTaskCompleted() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        MountWheels mountWheels = (MountWheels) findTask(car, MountWheels.class);
        mountWheels.complete();
        assertEquals(6, car.getUncompletedTasks().size());
    }

    @Test
    void getTasks() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertEquals(7, car.getTasks().size());
    }

    @Test
    void getCarModelName() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertEquals("ModelA", car.getCarModelName());
    }

    @Test
    void setCarModel_Null() {
        assertThrows(IllegalArgumentException.class, () -> new Car(null));
    }

    @Test
    void getValueOfPart() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertEquals("sedan", car.getValueOfPart("Body"));
    }

    @Test
    void getValueOfPart_InvalidPart() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertThrows(IllegalArgumentException.class, () ->  car.getValueOfPart("Invalid"));
    }

    @Test
    void getValueOfPart_Null() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertThrows(IllegalArgumentException.class, () ->  car.getValueOfPart(null));
    }

    @Test
    void getPartsMap() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertEquals(validOptions,car.getPartsMap());
    }

    @Test
    void estimatedCompletionTime() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertNull(car.getInitialCompletionTime());
        car.setEstimatedCompletionTime(new TimeStamp(0, 500));
        assertEquals(new TimeStamp(0, 500),car.getEstimatedCompletionTime());
        assertEquals(new TimeStamp(0, 500),car.getInitialCompletionTime());
    }

    @Test
    void estimatedCompletionTime_InvalidTime() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertNull(car.getInitialCompletionTime());
        assertThrows(IllegalArgumentException.class, () -> car.setEstimatedCompletionTime(new TimeStamp(-1, 500)));
        assertNull(car.getInitialCompletionTime());
        assertNull(car.getEstimatedCompletionTime());
    }

    @Test
    void deliveryTime() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertNull(car.getCompletionTime());
        car.setDeliveryTime(new TimeStamp(0, 500));
        assertEquals(new TimeStamp(0, 500),car.getCompletionTime());
    }

    @Test
    void deliveryTime_InvalidTime() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertNull(car.getCompletionTime());
        assertThrows(IllegalArgumentException.class, () -> car.setDeliveryTime(new TimeStamp(0, -200)));
        assertNull(car.getCompletionTime());
    }

    Task findTask(Car car, Class<? extends Task> taskClass) {
        Set<Task> tasks = car.getTasks();
        for (Task task : tasks) {
            if (taskClass.isInstance(task)) {
                return task;
            }
        }
        return null;
    }

}
