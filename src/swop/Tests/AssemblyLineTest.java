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
    void addToAssembly() {
        int size = assemblyLine.getCarQueue().size();

        Map<String, String> options = Map.of("body", "sedan", "color", "red", "engine", "standard 2l 4 cilinders",
                "gearBox", "6 speed manual", "seats", "leather black", "airco", "manual", "wheels", "comfort");
        CarModel model = new CarModel(options);
        CarOrder order = new CarOrder(model);
        assemblyLine.addToAssembly(order);


        assertEquals(assemblyLine.getCarQueue().getLast().getCarModel().getParts().toString(),
                "{seats=leather black, body=sedan, airco=manual, color=red, engine=standard 2l 4 cilinders, wheels=comfort, gearBox=6 speed manual}");
        assertEquals(size + 1, assemblyLine.getCarQueue().size());

    }


}
