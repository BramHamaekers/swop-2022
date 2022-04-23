package swop.Tests.UnitTests;

import org.junit.jupiter.api.Test;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModelSpecification;

import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

class CarModelSpecificationTest {

    Map<String, String> validOptions = Map.ofEntries(
            entry("Body", "sedan"),
            entry("Color", "red"),
            entry("Engine", "standard 2l v4"),
            entry("Gearbox", "6 speed manual"),
            entry("Seats", "leather white"),
            entry("Airco", "manual"),
            entry("Wheels", "winter"),
            entry("Spoiler", "low")
    );

    @Test
    void getAllParts() {
        CarModelSpecification specification = new CarModelSpecification(validOptions);
        assertEquals(validOptions, specification.getAllParts());
    }

    @Test
    void getValueOfPart() {
        CarModelSpecification specification = new CarModelSpecification(validOptions);
        assertEquals("sedan", specification.getValueOfPart("Body"));
    }

    @Test
    void getValueOfPart_Null() {
        CarModelSpecification specification = new CarModelSpecification(validOptions);
        assertThrows(IllegalArgumentException.class, () -> specification.getValueOfPart(null));
    }

    @Test
    void getValueOfPart_InvalidCategory() {
        CarModelSpecification specification = new CarModelSpecification(validOptions);
        assertThrows(IllegalArgumentException.class, () -> specification.getValueOfPart("Invalid"));
    }

    @Test
    void invalidOptions_NoSpoilerOnSportBody() {
        Map<String, String> options = Map.ofEntries(
                entry("Body", "sport"),
                entry("Color", "red"),
                entry("Engine", "ultra 3l v8"),
                entry("Gearbox", "6 speed manual"),
                entry("Seats", "leather white"),
                entry("Airco", "manual"),
                entry("Wheels", "winter")
        );
        assertThrows(IllegalArgumentException.class, () -> new CarModelSpecification(options));
    }

    @Test
    void invalidOptions_InvalidEngineOnSportBody() {
        Map<String, String> options = Map.ofEntries(
                entry("Body", "sport"),
                entry("Color", "red"),
                entry("Engine", "standard 2l v4"),
                entry("Gearbox", "6 speed manual"),
                entry("Seats", "leather white"),
                entry("Airco", "manual"),
                entry("Wheels", "winter"),
                entry("Spoiler", "low")
        );
        assertThrows(IllegalArgumentException.class, () -> new CarModelSpecification(options));
    }

    @Test
    void invalidOptions_InvalidAircoOnUltraEngine() {
        Map<String, String> options = Map.ofEntries(
                entry("Body", "sport"),
                entry("Color", "red"),
                entry("Engine", "ultra 3l v8"),
                entry("Gearbox", "6 speed manual"),
                entry("Seats", "leather white"),
                entry("Airco", "automatic"),
                entry("Wheels", "winter"),
                entry("Spoiler", "low")
        );
        assertThrows(IllegalArgumentException.class, () -> new CarModelSpecification(options));
    }
}