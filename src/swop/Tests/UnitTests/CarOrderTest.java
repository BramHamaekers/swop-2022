//package swop.Tests.UnitTests;
//
//import org.junit.jupiter.api.Test;
//import swop.Car.Car;
//import swop.Car.CarModelSpecification;
//import swop.Car.CarOrder;
//import swop.CarManufactoring.*;
//import swop.Exceptions.NotAllTasksCompleteException;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CarOrderTest {
//    Map<String,String> carOpts = Map.of("Airco", "manual",
//            "GearBox","6 speed manual",
//            "Wheels","comfort",
//            "Color", "red",
//            "Body","sedan",
//            "Engine","standard 2l 4 cilinders",
//            "Seats","leather black");
//    CarModelSpecification carModelSpecification = new CarModelSpecification(0,carOpts);
//    CarOrder carOrder = new CarOrder(carModelSpecification);
//    Car car = carOrder.getCar();
//    CarManufacturingController carM = new CarManufacturingController();
//
//    @Test
//    void isCompleted() throws NotAllTasksCompleteException {
//        assertFalse(carOrder.isCompleted());
//        carM.addOrderToQueue(carOrder);
//        this.finishCar();
//        assertTrue(carOrder.isCompleted());
//    }
//
//    void finishCar() throws NotAllTasksCompleteException {
//        for(int i=0;i <3; i++){
//            carM.advanceAssembly(60);
//            for(Task task : carM.getAssembly().getWorkStations().get(i).getTasks())
//                carM.getAssembly().getWorkStations().get(i).completeTask(task);
//        }
//        carM.advanceAssembly(60);
//    }
//}