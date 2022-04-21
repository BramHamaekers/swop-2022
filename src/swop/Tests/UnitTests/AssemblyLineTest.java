package swop.Tests.UnitTests;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModelSpecification;
import swop.CarManufactoring.*;
import swop.Exceptions.NotAllTasksCompleteException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AssemblyLineTest {
    WorkStation ws1 = new WorkStation("Car Body Post");
    WorkStation ws2 = new WorkStation("Drivetrain Post");
    LinkedList<WorkStation> list = new LinkedList<>(Arrays.asList(ws1,ws2));
    AssemblyLine assem = new AssemblyLine(list);

    Map<String,String> carOpts = Map.of("Airco", "manual",
            "GearBox","6 speed manual",
            "Wheels","comfort",
            "Color", "red",
            "Body","sedan",
            "Engine","standard 2l 4 cilinders",
            "Seats","leather black");
//    CarModelSpecification carModelSpecification = new CarModelSpecification(0,carOpts);
//    Car car = new Car(carModelSpecification);

    @Test
    void advance() throws NotAllTasksCompleteException {
        assertNull(assem.getWorkStations().get(0).getCar());
//        assem.advance(car,60);
//        assertEquals(assem.getWorkStations().get(0).getCar(), car);
//        assertThrows(NotAllTasksCompleteException.class,() -> assem.advance(car,60));
    }

    @Test
    void allTasksCompleted() throws NotAllTasksCompleteException {
        assertTrue(assem.allTasksCompleted());
//        assem.advance(car,60);
        assertFalse(assem.allTasksCompleted());
    }

    @Test
    void getCurrentStatus() throws NotAllTasksCompleteException {
        assertEquals(assem.getCurrentStatus(),Arrays.asList("Car Body Post: EMPTY", "Drivetrain Post: EMPTY"));
//        assem.advance(car,60);
//        assertEquals(assem.getCurrentStatus(),Arrays.asList(String.format("Car Body Post: %s (PENDING)",car.getPartsMap()),
//                "Drivetrain Post: EMPTY"));
        this.finishTasks();
//        assertEquals(assem.getCurrentStatus(), Arrays.asList(String.format("Car Body Post: %s (FINISHED)",car.getPartsMap()),
//                "Drivetrain Post: EMPTY"));
    }

    //TODO: to be deleted as a method in iteration 2
    @Test
    void getAdvancedStatus() throws NotAllTasksCompleteException {
//        assertEquals(assem.getAdvancedStatus(car),Arrays.asList(String.format("Car Body Post: %s (PENDING)",car.getPartsMap()),
//                "Drivetrain Post: EMPTY"));
//        assem.advance(car, 60);
//        assertEquals(assem.getAdvancedStatus(car), Arrays.asList(String.format("Car Body Post: %s (PENDING)",car.getPartsMap()),
//                String.format("Drivetrain Post: %s (PENDING)", car.getPartsMap())));
    }

    @Test
    void getWorkStations() {
        assertEquals(assem.getWorkStations(), list);
        assertEquals(assem.getWorkStations(), List.copyOf(list));
    }

    @Test
    void getWorkstationNames() {
        assertEquals(assem.getWorkstationNames(), List.of("Car Body Post","Drivetrain Post"));
    }

    @Test
    void getUncompletedTasks() throws NotAllTasksCompleteException {
        assertNull(assem.getUncompletedTasks("Car Body Post"));
//        assem.advance(car, 60);
        assertEquals(assem.getUncompletedTasks("Car Body Post"), new LinkedHashSet<>(Arrays.asList(Task.AssemblyCarBody,Task.PaintCar)));
        this.finishTasks();
        assertEquals(assem.getUncompletedTasks("Car Body Post"), new LinkedHashSet<>());
    }

    @Test
    void isEmptyAssemblyLine() throws NotAllTasksCompleteException {
        assertTrue(assem.isEmptyAssemblyLine());
//        assem.advance(car, 60);
        assertFalse(assem.isEmptyAssemblyLine());
    }

    void finishTasks() {
        for(Task task : assem.getWorkStations().get(0).getTasks())
            assem.getWorkStations().get(0).completeTask(task);
    }
}