package swop.Tests.UnitTests.CarManufactoring;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.CarManufactoring.*;
import swop.Exceptions.NotAllTasksCompleteException;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class AssemblyLineTest {
    WorkStation ws1 = new WorkStation("Car Body Post");
    WorkStation ws2 = new WorkStation("Drivetrain Post");
    WorkStation ws3 = new WorkStation("Accessories Post");
    LinkedList<WorkStation> list = new LinkedList<>(Arrays.asList(ws1,ws2, ws3));
    AssemblyLine assemblyLine = new AssemblyLine(list);

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
    void advance() throws NotAllTasksCompleteException {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assertNull(assemblyLine.advance(car));
        assertEquals(car, assemblyLine.getWorkStations().get(0).getCar());
    }

    @Test
    void advance_LastCarFinished() throws NotAllTasksCompleteException {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        car.getUncompletedTasks().forEach(car::completeTask);
        assemblyLine.getWorkStations().get(2).setCar(car);
        assertEquals(car, assemblyLine.advance(null));
        assertNull(assemblyLine.getWorkStations().get(0).getCar());
    }

    @Test
    void advance_NotAllTasksCompleted() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assemblyLine.getWorkStations().get(2).setCar(car);
        assertThrows(NotAllTasksCompleteException.class, () -> assemblyLine.advance(null));
    }

    @Test
    void getWorkstationNames() {
        assertEquals(List.of("Car Body Post","Drivetrain Post","Accessories Post"), assemblyLine.getWorkstationNames());
    }

    @Test
    void getUncompletedTasks_NoCarInStation() {
        assertNull(assemblyLine.getUncompletedTasks(new WorkStation("Accessories Post")));
    }

    @Test
    void getUncompletedTasks_InvalidStationName() {
        assertThrows(IllegalArgumentException.class, () -> assemblyLine.getUncompletedTasks(new WorkStation("Invalid Station")));
    }

    @Test
    void getUncompletedTasks() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assemblyLine.getWorkStations().get(0).setCar(car);
        assertEquals(new LinkedList<>(List.of(Task.AssemblyCarBody, Task.PaintCar)) ,assemblyLine.getUncompletedTasks(assemblyLine.getWorkStations().get(0)));
    }

    @Test
    void getUncompletedTasks_CompletedTasks() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        car.getUncompletedTasks().forEach(car::completeTask);
        assemblyLine.getWorkStations().get(2).setCar(car);
        assertEquals(new LinkedList<>() ,assemblyLine.getUncompletedTasks(assemblyLine.getWorkStations().get(2)));
    }

    @Test
    void isEmptyAssemblyLine_True() {
        assertTrue(assemblyLine.isEmptyAssemblyLine());
    }

    @Test
    void isEmptyAssemblyLine_False() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assemblyLine.getWorkStations().get(1).setCar(car);
        assertFalse(assemblyLine.isEmptyAssemblyLine());
    }

    @Test
    void getUnfinishedCars() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        assemblyLine.getWorkStations().get(1).setCar(car);
        assertEquals(List.of(car), assemblyLine.getUnfinishedCars());
    }

    @Test
    void getUnfinishedCars_noCars() {
        assertEquals(new LinkedList<>(), assemblyLine.getUnfinishedCars());
    }

    @Test
    void getUnfinishedCars_onlyFinishedCars() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);
        car.getUncompletedTasks().forEach(car::completeTask);
        assemblyLine.getWorkStations().get(2).setCar(car);
        assertEquals(new LinkedList<>(), assemblyLine.getUnfinishedCars());
    }
}