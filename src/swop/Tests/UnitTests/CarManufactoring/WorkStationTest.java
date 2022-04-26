package swop.Tests.UnitTests.CarManufactoring;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        assertEquals(List.of(Task.AssemblyCarBody, Task.PaintCar), station.getUncompletedTasks());
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
        car.completeTask(Task.AssemblyCarBody);
        assertEquals(List.of(Task.AssemblyCarBody), station.getCompletedTasks());
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

        assertEquals(List.of(Task.AssemblyCarBody, Task.PaintCar),station1.getTasks());
        assertEquals(List.of(Task.InsertEngine, Task.InstallGearbox),station2.getTasks());
        assertEquals(List.of(Task.InstallSeats, Task.InstallAirco, Task.MountWheels, Task.InstallSpoiler),station3.getTasks());
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
        station.completeTask(Task.PaintCar, 20);
        assertEquals(20, station.getCurrentWorkingTime());

    }

    @Test
    void stationTasksCompleted() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);

        assertFalse(station.stationTasksCompleted());
        station.completeTask(Task.PaintCar, 20);
        assertFalse(station.stationTasksCompleted());
        station.completeTask(Task.AssemblyCarBody, 20);
        assertTrue(station.stationTasksCompleted());
    }

    @Test
    void stationTasksCompleted_NoCar() {
        WorkStation station = new WorkStation("Car Body Post");
        assertTrue(station.stationTasksCompleted());

    }
}