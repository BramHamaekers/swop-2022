package swop.Tests;

import org.junit.jupiter.api.Test;
import swop.CarManufactoring.Car;
import swop.CarManufactoring.CarModel;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Map<String, String> options = Map.of("body", "sedan", "color", "red", "engine", "standard 2l 4 cilinders",
            "gearBox", "6 speed manual", "seats", "leather black", "airco", "manual", "wheels", "comfort");
    CarModel model1 = new CarModel(options);
    CarModel model2 = new CarModel(options);
    Car car = new Car(model1);

    @Test
    void isValidTask() {
        assert car.isValidTask("Assembly car body");
        assert car.isValidTask("Paint car");
        assert car.isValidTask("Insert engine");
        assert car.isValidTask("Insert gearbox");
        assert car.isValidTask("InSeRt GEaRbOx");
        assert car.isValidTask("Install seats");
        assert !car.isValidTask("install boosterrockets");
    }

    @Test
    void completeTask() {
        assertEquals(car.getUncompletedTasks(), Set.copyOf(Car.tasks));
        car.completeTask("Assembly car body");
        assertEquals(car.getUncompletedTasks(), Set.of("paint car", "insert engine", "insert gearbox",
                "install seats", "install airco", "mount wheels"));
        assertThrows(IllegalArgumentException.class, () -> car.completeTask("Assembly car body"));
    }

    @Test
    void isCompleted() {
        assertFalse(car.isCompleted());
        car.setUncompletedTasks(Set.of("paint car"));
        car.completeTask("paint car");
        assertTrue(car.isCompleted());
    }

    @Test
    void getUncompletedTasks() {
        assertEquals(car.getUncompletedTasks(), Set.copyOf(Car.tasks));
        car.completeTask("assembly car body");
        assertEquals(car.getUncompletedTasks(), Set.of("paint car", "insert engine", "insert gearbox",
                "install seats", "install airco", "mount wheels"));
        assertThrows(IllegalArgumentException.class, () -> car.setUncompletedTasks(Set.of("install boosterrockets")));
    }

    @Test
    void setUncompletedTasks() {
        assertEquals(car.getUncompletedTasks(), Set.copyOf(Car.tasks));
        car.completeTask("assembly car body");
        assertEquals(car.getUncompletedTasks(), Set.of("paint car", "insert engine", "insert gearbox",
                "install seats", "install airco", "mount wheels"));
        assertThrows(IllegalArgumentException.class, () -> car.setUncompletedTasks(Set.of("install boosterrockets")));
    }

    @Test
    void getCarModel() {
        assertEquals(car.getCarModel(),model1);
        assertNotEquals(car.getCarModel(),model2);
    }

    @Test
    void setCarModel() {
        assertEquals(car.getCarModel(),model1);
        car.setCarModel(model2);
        assertEquals(car.getCarModel(),model2);
    }
}