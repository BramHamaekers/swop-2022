//package swop.Tests.UnitTests;
//
//import org.junit.jupiter.api.Test;
//import swop.CarManufactoring.*;
//import swop.Exceptions.NotAllTasksCompleteException;
//import swop.Parts.Airco;
//
//import java.util.Map;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CarTest {
//
//    Map<String,String> carOpts = Map.of("Airco", "manual",
//            "GearBox","6 speed manual",
//            "Wheels","comfort",
//            "Color", "red",
//            "Body","sedan",
//            "Engine","standard 2l 4 cilinders",
//            "Seats","leather black");
////    CarModelSpecification carModelSpecification = new CarModelSpecification(0,carOpts);
//    CarManufacturingController carM = new CarManufacturingController();
////    CarOrder carOrder = new CarOrder(carModelSpecification);
//    Car car = carOrder.getCar();
//
//    @Test
//    void completeTask() {
//        Set<Task> tasks = Task.getAllTasks();
//        assertEquals(car.getUncompletedTasks(), tasks);
//        car.completeTask(Task.InsertEngine);
//        tasks.remove(Task.InsertEngine);
//        assertEquals(car.getUncompletedTasks(),tasks);
//        assertThrows(IllegalArgumentException.class,() -> car.completeTask(null));
//        assertThrows(IllegalArgumentException.class,() -> car.completeTask(Task.InsertEngine));
//    }
//
//    @Test
//    void isCompleted() throws NotAllTasksCompleteException {
//        assertFalse(car.isCompleted());
//        carM.addOrderToQueue(carOrder);
//        this.finishCar();
//        assertTrue(car.isCompleted());
//    }
//
//    @Test
//    void getUncompletedTasks() {
//        Set<Task> tasks = Task.getAllTasks();
//        assertEquals(car.getUncompletedTasks(), tasks);
//        car.completeTask(Task.InsertEngine);
//        tasks.remove(Task.InsertEngine);
//        assertEquals(car.getUncompletedTasks(),tasks);
//    }
//
////    @Test
////    void getCarModel() {
////        assertEquals(car.getCarModel(), carModelSpecification);
////        Map<String,String> carOpts = Map.of("Airco", "manual",
////                "GearBox","6 speed manual",
////                "Wheels","comfort",
////                "Color", "red",
////                "Body","break",
////                "Engine","standard 2l 4 cilinders",
////                "Seats","leather black");
////        CarModelSpecification cm2 = new CarModelSpecification(0,carOpts);
////        car.setCarModel(cm2);
////        assertEquals(car.getCarModel(),cm2);
////    }
//
////    @Test
////    void setCarModel() {
////        this.getCarModel();
////        assertThrows(IllegalArgumentException.class,() -> car.setCarModel(null));
////    }
//
//    @Test
//    void getValueOfPart() {
//        assertEquals(car.getValueOfPart(new Airco()),new Airco("manual").getValue());
//        assertThrows(IllegalArgumentException.class,() -> car.getValueOfPart(null));
//    }
//
//    @Test
//    void getPartsMap() {
//        assertEquals(car.getPartsMap(),Map.of("Airco", "manual",
//                "GearBox","6 speed manual",
//                "Wheels","comfort",
//                "Color", "red",
//                "Body","sedan",
//                "Engine","standard 2l 4 cilinders",
//                "Seats","leather black"));
//        assertThrows(IllegalArgumentException.class,() -> new Car(null).getPartsMap());
//    }
//
//    @Test
//    void setEstimatedCompletionTime() {
//        assertNull(car.getEstimatedCompletionTime());
//        String compl = String.format("day: %s, time: %02d:%02d%n", 0, 9, 0);
//        car.setEstimatedCompletionTime(compl);
//        assertEquals(car.getEstimatedCompletionTime(),compl);
//    }
//
//    @Test
//    void getEstimatedCompletionTime() {
//        assertNull(car.getEstimatedCompletionTime());
//        String compl = String.format("day: %s, time: %02d:%02d%n", 0, 9, 0);
//        car.setEstimatedCompletionTime(compl);
//        assertEquals(car.getEstimatedCompletionTime(),compl);
//    }
//
//    @Test
//    void getDeliveryTime() throws NotAllTasksCompleteException {
//        carM.addOrderToQueue(carOrder);
//        this.finishCar();
//        assertEquals(car.getDeliveryTime(),240);
//    }
//
//    //TODO:fix
////    @Test
////    void setCompletionTime() {
////        assertEquals(car.getDeliveryTime(),0);
////        car.setDeliveryTime(50);
////        assertEquals(car.getDeliveryTime(), 50);
////    }
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