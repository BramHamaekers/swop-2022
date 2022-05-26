package swop.Tests.UnitTests.CarManufactoring;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.CarManufactoring.Task;
import swop.CarManufactoring.Tasks.*;
import swop.CarManufactoring.WorkStation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

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
    void completeTask() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);

        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);
        AssemblyCarBody assemblyCarBody = (AssemblyCarBody) findTask(car, AssemblyCarBody.class);

        assertTrue(car.getUncompletedTasks().contains(assemblyCarBody));
        assemblyCarBody.complete();
        assertFalse(car.getUncompletedTasks().contains(assemblyCarBody));
    }

    @Test
    void newTasks_NullChosenOption() {
        assertThrows(IllegalArgumentException.class, () -> new AssemblyCarBody(null));
        assertThrows(IllegalArgumentException.class, () -> new InsertEngine(null));
        assertThrows(IllegalArgumentException.class, () -> new InstallAirco(null));
        assertThrows(IllegalArgumentException.class, () -> new InstallGearbox(null));
        assertThrows(IllegalArgumentException.class, () -> new InstallSeats(null));
        assertThrows(IllegalArgumentException.class, () -> new InstallSpoiler(null));
        assertThrows(IllegalArgumentException.class, () -> new MountWheels(null));
        assertThrows(IllegalArgumentException.class, () -> new PaintCar(null));
    }

    @Test
    void getDescription() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);

        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);
        InstallSeats installSeats = (InstallSeats) findTask(car, InstallSeats.class);

        assertEquals("Install seats of type: leather white", installSeats.getDescription());
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