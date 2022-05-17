package swop.Tests.UnitTests.CarManufactoring;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.CarManufactoring.Task;
import swop.CarManufactoring.Tasks.*;
import swop.CarManufactoring.WorkStation;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class WorkStationTest {

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
    void Init_WorkStation_InvalidName() {
        assertThrows(IllegalArgumentException.class, () -> new WorkStation("Invalid"));
    }

    @Test
    void getUncompletedTasks_NoCar() {
        WorkStation station = new WorkStation("Car Body Post");
        assertNull(station.getUncompletedTasks());
    }

    @Test
    void getUncompletedTasks() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);
        AssemblyCarBody assemblyCarBody = (AssemblyCarBody) findTask(car, AssemblyCarBody.class);
        PaintCar paintCar = (PaintCar) findTask(car, PaintCar.class);
        assertEquals(Set.of(assemblyCarBody, paintCar), new HashSet<>(station.getUncompletedTasks()));
    }

    @Test
    void getCompletedTasks_NoCar() {
        WorkStation station = new WorkStation("Car Body Post");
        assertNull(station.getCompletedTasks());
    }

    @Test
    void getCompletedTasks_NoTaskCompleted() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);
        assertEquals(new LinkedList<>(), station.getCompletedTasks());
    }

    @Test
    void getCompletedTasks_OneCompleted() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);
        AssemblyCarBody assemblyCarBody = (AssemblyCarBody) findTask(car, AssemblyCarBody.class);
        assemblyCarBody.complete();
        assertTrue(assemblyCarBody.isComplete());
        assertEquals(List.of(assemblyCarBody), station.getCompletedTasks());
    }

    @Test
    void isPartOfCurrentCarInWorkStation() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);

        assertTrue(station.isPartOfCurrentCarInWorkStation("Body"));
        assertFalse(station.isPartOfCurrentCarInWorkStation("Spoiler"));

    }



    @Test
    void getTasks() {
        WorkStation station1 = new WorkStation("Car Body Post");
        WorkStation station2 = new WorkStation("Drivetrain Post");
        WorkStation station3 = new WorkStation("Accessories Post");

        assertEquals(List.of(AssemblyCarBody.class, PaintCar.class),station1.getTasks());
        assertEquals(List.of(InsertEngine.class, InstallGearbox.class),station2.getTasks());
        assertEquals(List.of(InstallSeats.class, InstallAirco.class, MountWheels.class, InstallSpoiler.class),station3.getTasks());
    }

    @Test
    void getName() {
        WorkStation station = new WorkStation("Car Body Post");
        assertEquals("Car Body Post", station.getName());
    }

    @Test
    void getValueOfPart() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);

        assertEquals("sedan", station.getValueOfPart("Body"));
    }

    @Test
    void getValueOfPart_Null() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);

        assertThrows(IllegalArgumentException.class, () -> station.getValueOfPart(null));
    }

    @Test
    void getCurrentWorkingTime() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);

        assertEquals(0, station.getCurrentWorkingTime());
        PaintCar paintCar = (PaintCar) findTask(car, PaintCar.class);
        station.completeTask(paintCar, 20);
        assertEquals(20, station.getCurrentWorkingTime());

    }

    @Test
    void stationTasksCompleted() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);

        PaintCar paintCar = (PaintCar) findTask(car, PaintCar.class);
        assertFalse(station.stationTasksCompleted());
        station.completeTask(paintCar, 20);

        AssemblyCarBody assemblyCarBody = (AssemblyCarBody) findTask(car, AssemblyCarBody.class);
        assertFalse(station.stationTasksCompleted());
        station.completeTask(assemblyCarBody, 20);
        assertTrue(station.stationTasksCompleted());
    }

    @Test
    void stationTasksCompleted_NoCar() {
        WorkStation station = new WorkStation("Car Body Post");
        assertTrue(station.stationTasksCompleted());

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