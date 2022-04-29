package swop.Tests.UnitTests.CarManufactoring;

import org.junit.jupiter.api.Test;
import swop.Car.Car;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;
import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;

import java.util.List;
import java.util.Map;

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
        Task assemblyCarBody = Task.AssemblyCarBody;

        assertTrue(car.getUncompletedTasks().contains(assemblyCarBody));
        assemblyCarBody.completeTask(0);
        assertFalse(car.getUncompletedTasks().contains(assemblyCarBody));

    }

    @Test
    void setWorkStation() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);

        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);
        Task assemblyCarBody = Task.AssemblyCarBody;

        assemblyCarBody.setWorkStation(station);
        assertEquals(station, assemblyCarBody.getWorkStation());
    }

    @Test
    void getTaskDescription() {
        modelA.setCarModelSpecification(specification);
        Car car = new Car(modelA);

        WorkStation station = new WorkStation("Car Body Post");
        station.setCar(car);
        Task assemblyCarBody = Task.AssemblyCarBody;

        assemblyCarBody.setWorkStation(station);
        assertEquals(List.of("Mount a body on the chassis of type: sedan"), assemblyCarBody.getTaskDescription());
    }
}