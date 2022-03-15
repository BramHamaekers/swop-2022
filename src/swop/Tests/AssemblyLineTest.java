package swop.Tests;

import org.junit.jupiter.api.Test;
import swop.CarManufactoring.AssemblyLine;
import swop.CarManufactoring.CarModel;
import swop.CarManufactoring.CarOrder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssemblyLineTest {

    AssemblyLine assemblyLine = new AssemblyLine();

    @Test
    void addToAssemblyTest() {
    }

    @Test
    void addToAssemblyTimeTest() {
        for (int i = 0; i < 160; i++) {
            Map<String, String> options = Map.of("body", "sedan", "color", "red", "engine", "standard 2l 4 cilinders",
                    "gearBox", "6 speed manual", "seats", "leather black", "airco", "manual", "wheels", "comfort");
            CarModel model = new CarModel(0,options);
            CarOrder order = new CarOrder(model);
            assemblyLine.addToAssembly(order);
        }

    }


}
