package swop.Tests.UnitTests;

import org.junit.jupiter.api.Test;
import swop.CarManufactoring.*;
import swop.CarModel.CarModel;
import swop.Exceptions.NotAllTasksCompleteException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarManufacturingControllerTest {

    CarManufacturingController carM = new CarManufacturingController();
    Map<String,String> carOpts = Map.of("Airco", "manual",
            "GearBox","6 speed manual",
            "Wheels","comfort",
            "Color", "red",
            "Body","sedan",
            "Engine","standard 2l 4 cilinders",
            "Seats","leather black");
    CarModel carModel = new CarModel(0,carOpts);
    CarOrder carOrder = new CarOrder(carModel);
    Car car = carOrder.getCar();

    @Test
    void getCarQueueSize() {
        assertEquals(carM.getCarQueueSize(),0);
        carM.addOrderToQueue(carOrder);
        assertEquals(carM.getCarQueueSize(),1);
    }

    @Test
    void getCarQueue() {
        assertEquals(carM.getCarQueue(), List.of());
        carM.addOrderToQueue(carOrder);
        assertEquals(carM.getCarQueue(), List.of(car));
    }

    @Test
    void advanceAssembly() throws NotAllTasksCompleteException {
        assertNull(carM.getAssembly().getWorkStations().get(0).getCar());
        carM.addOrderToQueue(carOrder);
        carM.advanceAssembly(60);
        assertEquals(carM.getAssembly().getWorkStations().get(0).getCar(), car);
        assertThrows(NotAllTasksCompleteException.class,() -> carM.advanceAssembly(60));
    }

    @Test
    void getAdvancedStatus() {
        ArrayList<String> status = new ArrayList<>();
        List<WorkStation> workstations = carM.getAssembly().getWorkStations();
        for (WorkStation w : workstations)
            status.add(String.format("%s: EMPTY", w.getName()));
        assertEquals(carM.getAdvancedStatus(),status);
        carM.addOrderToQueue(carOrder);
        status.remove(0);
        status.add(0,String.format("%s: %s (PENDING)", workstations.get(0).getName(), car.getPartsMap()));
        assertEquals(carM.getAdvancedStatus(),status);
    }

    @Test
    void addOrderToQueue() {
        assertEquals(carM.getCarQueue(), new LinkedList<>());
        carM.addOrderToQueue(carOrder);
        assertEquals(carM.getCarQueue(), List.of(car));
    }
}