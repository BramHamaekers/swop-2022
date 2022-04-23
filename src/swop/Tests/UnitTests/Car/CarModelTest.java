package swop.Tests.UnitTests.Car;

import org.junit.jupiter.api.Test;
import swop.Car.CarModel.*;
import swop.Car.CarModelSpecification;

import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import static org.junit.jupiter.api.Assertions.*;

public class CarModelTest {

    @Test
    void getName_AllModels() {
        // Create CarModels
        CarModel modelA = new ModelA();
        CarModel modelB = new ModelB();
        CarModel modelC = new ModelC();

        // Test getName()
        assertEquals("ModelA", modelA.getName());
        assertEquals( "ModelB", modelB.getName());
        assertEquals( "ModelC", modelC.getName());
    }

    @Test
    void missingMandatoryPart_Wheels_AllModels() {
        // Create CarModels
        CarModel modelA = new ModelA();
        CarModel modelB = new ModelB();
        CarModel modelC = new ModelC();

        // Create CarModelSpecification with 'Wheels' missing
        Map<String, String> options = Map.ofEntries(
                entry("Body", "sedan"),
                entry("Color", "red"),
                entry("Engine", "standard 2l v4"),
                entry("Gearbox", "6 speed manual"),
                entry("Seats", "leather white"),
                entry("Airco", "manual"));
        CarModelSpecification specification = new CarModelSpecification(options);

        // Test setCarModelSpecification
        assertThrows(IllegalArgumentException.class, () ->modelA.setCarModelSpecification(specification));
        assertNull(modelA.getCarModelSpecification());
        assertThrows(IllegalArgumentException.class, () ->modelB.setCarModelSpecification(specification));
        assertNull(modelB.getCarModelSpecification());
        assertThrows(IllegalArgumentException.class, () ->modelC.setCarModelSpecification(specification));
        assertNull(modelC.getCarModelSpecification());
    }

    @Test
    void getValidOptions_ModelA() {
        CarModel modelA = new ModelA();
        Map<String, List<String>> modelAOptions = Map.of(
                "Body", List.of("sedan", "break"),
                "Color", List.of("red", "blue", "black", "white"),
                "Engine", List.of("standard 2l v4", "performance 2.5l v6"),
                "Gearbox", List.of("6 speed manual", "5 speed manual", "5 speed automatic"),
                "Seats", List.of("leather white", "leather black", "vinyl grey"),
                "Airco", List.of("manual","automatic", "None"),
                "Wheels", List.of("winter", "comfort", "sports")
        );
        assertEquals(modelAOptions, modelA.getValidOptions());

    }

    @Test
    void setCarModelSpecification_ValidSpecification_ModelA() {
        // Create CarModel and valid Specification
        CarModel modelA = new ModelA();
        Map<String, String> options = Map.ofEntries(
                entry("Body", "sedan"),
                entry("Color", "red"),
                entry("Engine", "standard 2l v4"),
                entry("Gearbox", "6 speed manual"),
                entry("Seats", "leather white"),
                entry("Airco", "manual"),
                entry("Wheels", "winter")
        );
        CarModelSpecification specification = new CarModelSpecification(options);
        //Test setCarModelSpecification
        modelA.setCarModelSpecification(specification);
        assertEquals(specification, modelA.getCarModelSpecification());
    }

    @Test
    void setCarModelSpecification_InvalidCarOptionCategory_ModelA() {
        // Create CarModel and Specification
        CarModel modelA = new ModelA();
        Map<String, String> options = Map.ofEntries(
                entry("Body", "sedan"),
                entry("Color", "red"),
                entry("Engine", "standard 2l v4"),
                entry("Gearbox", "6 speed manual"),
                entry("Seats", "leather white"),
                entry("Airco", "manual"),
                entry("Wheels", "winter"),
                entry("InvalidCategory", "Invalid"));
        CarModelSpecification specification = new CarModelSpecification(options);

        //Test setCarModelSpecification
        assertThrows(IllegalArgumentException.class, () ->modelA.setCarModelSpecification(specification));
        assertNull(modelA.getCarModelSpecification());
    }

    @Test
    void setCarModelSpecification_InvalidCarOption_ModelA() {
        // Create CarModel and Specification
        CarModel modelA = new ModelA();
        Map<String, String> options = Map.ofEntries(
                entry("Body", "InvalidOption"),
                entry("Color", "red"),
                entry("Engine", "standard 2l v4"),
                entry("Gearbox", "6 speed manual"),
                entry("Seats", "leather white"),
                entry("Airco", "manual"),
                entry("Wheels", "winter"));
        CarModelSpecification specification = new CarModelSpecification(options);

        //Test setCarModelSpecification
        assertThrows(IllegalArgumentException.class, () ->modelA.setCarModelSpecification(specification));
        assertNull(modelA.getCarModelSpecification());
    }
}
