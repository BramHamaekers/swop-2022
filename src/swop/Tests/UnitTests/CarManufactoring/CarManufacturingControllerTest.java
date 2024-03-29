package swop.Tests.UnitTests.CarManufactoring;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.Car.CarOrder;
import swop.CarManufactoring.*;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.Main.AssemAssist;
import swop.Miscellaneous.TimeStamp;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class CarManufacturingControllerTest {

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
    void init_CarManufacturingControllerTest() {
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        assertEquals(List.of("Car Body Post","Drivetrain Post","Accessories Post"), carManufacturingController.getAssembly().getWorkstationNames());

    }

    @Test
    void advanceAssembly_NoCars() throws NotAllTasksCompleteException {
        // Init objects for test
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        carManufacturingController.advanceAssemblyAndUpdateSchedular();
        assertEquals("day: 0, time: 06:00",carManufacturingController.getScheduler().getTime().toString());
    }

    @Test
    void advanceAssembly_UnfinishedTasks() {
        // Init objects for test
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        Car car = carOrder.getCar();

        carManufacturingController.addOrderToQueue(carOrder);
        assertThrows(NotAllTasksCompleteException.class, carManufacturingController::advanceAssemblyAndUpdateSchedular);
        assertEquals("day: 0, time: 06:00",carManufacturingController.getScheduler().getTime().toString());
        assertEquals(car, carManufacturingController.getAssembly().getWorkStations().get(0).getCar());
        assertNull(carManufacturingController.getAssembly().getWorkStations().get(1).getCar());
    }

    @Test
    void advanceAssembly_finishedTasks_NoCarInQueue() {
        // Init objects for test
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        Car car = carOrder.getCar();

        carManufacturingController.addOrderToQueue(carOrder);
        car.getTasks().forEach(Task::complete);
        assertDoesNotThrow(carManufacturingController::advanceAssemblyAndUpdateSchedular);
        assertEquals("day: 0, time: 06:00",carManufacturingController.getScheduler().getTime().toString());
        assertEquals(car, carManufacturingController.getAssembly().getWorkStations().get(1).getCar());
        assertNull(carManufacturingController.getAssembly().getWorkStations().get(0).getCar());
    }

    @Test
    void advanceAssembly_finishedTasks_CanFinishNewCar() {
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder1 = new CarOrder(modelA);
        Car car1 = carOrder1.getCar();

        modelA.setCarModelSpecification(specification);
        CarOrder carOrder2 = new CarOrder(modelA);
        Car car2 = carOrder2.getCar();

        carManufacturingController.addOrderToQueue(carOrder1);
        carManufacturingController.addOrderToQueue(carOrder2);
        car1.getTasks().forEach(Task::complete);

        assertDoesNotThrow(carManufacturingController::advanceAssemblyAndUpdateSchedular);
        assertEquals("day: 0, time: 06:00",carManufacturingController.getScheduler().getTime().toString());
        assertEquals(car1, carManufacturingController.getAssembly().getWorkStations().get(1).getCar());
        assertEquals(car2, carManufacturingController.getAssembly().getWorkStations().get(0).getCar());
    }

    @Test
    void advanceAssembly_finishedTasks_CanNotFinishNewCar() {
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder1 = new CarOrder(modelA);
        Car car1 = carOrder1.getCar();

        modelA.setCarModelSpecification(specification);
        CarOrder carOrder2 = new CarOrder(modelA);
        Car car2 = carOrder2.getCar();

        carManufacturingController.addOrderToQueue(carOrder1);
        carManufacturingController.addOrderToQueue(carOrder2);
        car1.getTasks().forEach(Task::complete);
        carManufacturingController.getScheduler().addTime(960);

        assertDoesNotThrow(carManufacturingController::advanceAssemblyAndUpdateSchedular);
        assertEquals("day: 0, time: 22:00",carManufacturingController.getScheduler().getTime().toString());
        assertEquals(car1, carManufacturingController.getAssembly().getWorkStations().get(1).getCar());
        assertNull(carManufacturingController.getAssembly().getWorkStations().get(0).getCar());
        assertEquals(List.of(car2), carManufacturingController.getCarQueue());
    }

    @Test
    void advanceAssembly_finishedCar() {
        // Init objects for test
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        Car car = carOrder.getCar();

        carManufacturingController.addOrderToQueue(carOrder);
        car.getTasks().forEach(Task::complete);
        assertDoesNotThrow(carManufacturingController::advanceAssemblyAndUpdateSchedular);
        assertDoesNotThrow(carManufacturingController::advanceAssemblyAndUpdateSchedular);
        assertDoesNotThrow(carManufacturingController::advanceAssemblyAndUpdateSchedular);

        assertEquals("day: 0, time: 06:00",carManufacturingController.getScheduler().getTime().toString());
        assertFalse(carManufacturingController.getAssembly().getUnfinishedCars().contains(car));
        assertEquals(new TimeStamp(0, 0),car.getCompletionTime());

        carManufacturingController.getScheduler().addTime(961);
        assertDoesNotThrow(carManufacturingController::advanceAssemblyAndUpdateSchedular);
        assertEquals(new TimeStamp(1,0),carManufacturingController.getScheduler().getTime());
    }

    /**
     * First order should be added to the assembly line automatically and carQueue should be empty
     */
    @Test
    void addOrderToQueue_FirstCar() {
        // Init objects for test
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();
        modelA.setCarModelSpecification(specification);
        CarOrder carOrder = new CarOrder(modelA);
        Car car = carOrder.getCar();

        carManufacturingController.addOrderToQueue(carOrder);
        assertEquals(List.of(), carManufacturingController.getCarQueue());
        assertEquals(car, carManufacturingController.getAssembly().getWorkStations().get(0).getCar());
    }

    @Test
    void addOrderToQueue_TwoCars() {
        // Init objects for test
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();

        modelA.setCarModelSpecification(specification);
        CarOrder carOrder1 = new CarOrder(modelA);
        Car car1 = carOrder1.getCar();

        modelA.setCarModelSpecification(specification);
        CarOrder carOrder2 = new CarOrder(modelA);
        Car car2 = carOrder2.getCar();

        // First Car
        carManufacturingController.addOrderToQueue(carOrder1);
        assertEquals(List.of(), carManufacturingController.getCarQueue());
        assertEquals(car1, carManufacturingController.getAssembly().getWorkStations().get(0).getCar());

        // Second Car
        carManufacturingController.addOrderToQueue(carOrder2);
        assertEquals(List.of(car2), carManufacturingController.getCarQueue());
        assertEquals(car1, carManufacturingController.getAssembly().getWorkStations().get(0).getCar());
    }

    @Test
    void addOrderToQueue_NullOrder() {
        // Init objects for test
        AssemAssist assemAssist = new AssemAssist();
        CarManufacturingController carManufacturingController = assemAssist.getController();

        assertThrows(IllegalArgumentException.class, () -> carManufacturingController.addOrderToQueue(null));
    }
}
