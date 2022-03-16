package swop.Tests;
import swop.CarManufactoring.Car;
import swop.CarManufactoring.CarModel;
import swop.CarManufactoring.CarOrder;
import swop.CarManufactoring.Task;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.GarageHolderUI;

import org.junit.jupiter.api.Test;
import swop.Users.GarageHolder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class OrderNewCarMSSTest {

    Map<String, String> options1 = Map.of("body", "sedan", "color", "red", "engine", "standard 2l 4 cilinders", "gearBox", "6 speed manual", "seats", "leather black", "airco", "manual", "wheels", "comfort");
    Map<String, String> options2 = Map.of("body", "break", "color", "blue", "engine", "performance 2.5l 6 cilinders", "gearBox", "6 speed manual", "seats", "leather white", "airco", "manual", "wheels", "sports");
    Map<String, String> options3 = Map.of("body", "break", "color", "black", "engine", "standard 2l 4 cilinders", "gearBox", "5 speed automatic", "seats", "vinyl grey", "airco", "automatic climate control", "wheels", "comfort");
    Map<String, String> options4 = Map.of("body", "sedan", "color", "white", "engine", "performance 2.5l 6 cilinders", "gearBox", "6 speed manual", "seats", "leather black", "airco", "automatic climate control", "wheels", "sports");

    LinkedHashMap<String, List<String>> optionsMap = new LinkedHashMap<>(){{
        put("Airco", Arrays.asList("manual", "automatic climate control"));
        put("Body",Arrays.asList("sedan", "break"));
        put("Color",Arrays.asList("red", "blue", "black", "white"));
        put("Engine",Arrays.asList("standard 2l 4 cilinders", "performance 2.5l 6 cilinders"));
        put("GearBox",Arrays.asList("6 speed manual", "5 speed automatic"));
        put("Seats",Arrays.asList("leather black", "leather white", "vinyl grey"));
        put("Wheels",Arrays.asList("comfort", "sports (low profile)"));
    }};

    GarageHolder garageHolder = new GarageHolder("ahMi8phe");

    @Test
    void overviewEmptyTest(){
        swop.UI.GarageHolderUI.displayOrders(null);
    }

    @Test
    void overviewRegularTest(){
        Set<CarOrder> carOrders = new HashSet<>();
        CarOrder order1 = new CarOrder(new CarModel(0,options1));
        carOrders.add(order1);
        CarOrder order2 = new CarOrder(new CarModel(0,options2));
        carOrders.add(order2);
        CarOrder order3 = new CarOrder(new CarModel(0,options3));
        carOrders.add(order3);
        Car car3 = order3.getCar();
        Set<Task> tasks3 = Set.copyOf(car3.getUncompletedTasks());
        for (Task task : tasks3){
            car3.completeTask(task);
        }
        CarOrder order4 = new CarOrder(new CarModel(0,options4));
        carOrders.add(order4);
        Car car4 = order4.getCar();
        Set<Task> tasks4 = Set.copyOf(car4.getUncompletedTasks());
        for (Task task : tasks4){
            car4.completeTask(task);
        }
        swop.UI.GarageHolderUI.displayOrders(carOrders);
    }

    @Test
    void overviewNoneCompletedTest(){
        Set<CarOrder> carOrders = new HashSet<>();
        CarOrder order1 = new CarOrder(new CarModel(0,options1));
        carOrders.add(order1);
        CarOrder order2 = new CarOrder(new CarModel(0,options2));
        carOrders.add(order2);
        CarOrder order3 = new CarOrder(new CarModel(0,options3));
        carOrders.add(order3);
        CarOrder order4 = new CarOrder(new CarModel(0,options4));
        carOrders.add(order4);
        swop.UI.GarageHolderUI.displayOrders(carOrders);
    }

    @Test
    void overviewAllCompletedTest(){
        Set<CarOrder> carOrders = new HashSet<>();
        CarOrder order1 = new CarOrder(new CarModel(0,options1));
        carOrders.add(order1);
        Car car1 = order1.getCar();
        Set<Task> tasks1 = Set.copyOf(car1.getUncompletedTasks());
        for (Task task : tasks1){
            car1.completeTask(task);
        }
        CarOrder order2 = new CarOrder(new CarModel(0,options2));
        carOrders.add(order2);
        Car car2 = order2.getCar();
        Set<Task> tasks2 = Set.copyOf(car2.getUncompletedTasks());
        for (Task task : tasks2){
            car2.completeTask(task);
        }
        CarOrder order3 = new CarOrder(new CarModel(0,options3));
        carOrders.add(order3);
        Car car3 = order3.getCar();
        Set<Task> tasks3 = Set.copyOf(car3.getUncompletedTasks());
        for (Task task : tasks3){
            car3.completeTask(task);
        }
        CarOrder order4 = new CarOrder(new CarModel(0,options4));
        carOrders.add(order4);
        Car car4 = order4.getCar();
        Set<Task> tasks4 = Set.copyOf(car4.getUncompletedTasks());
        for (Task task : tasks4){
            car4.completeTask(task);
        }
        swop.UI.GarageHolderUI.displayOrders(carOrders);
    }

    @Test
    void indicatePlaceOrderTest() throws CancelException {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("y".getBytes());
        System.setIn(in);
        swop.UI.GarageHolderUI.indicatePlaceOrder();
        System.setIn(sysInBackup);
    }

    @Test
    void indicateCarModelTest() throws CancelException {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        swop.UI.GarageHolderUI.indicateCarModel();
        System.setIn(sysInBackup);
    }

    @Test
    void displayOrderFormTest() {
        swop.UI.GarageHolderUI.displayOrderingForm(optionsMap);
    }

    @Test
    void fillOrderFormTest() throws CancelException {
        String[] inputs = {"0", "1", "3", "0", "2", "1"};
        int inputIndex = 0;
        for (var entry : optionsMap.entrySet()) {
            InputStream sysInBackup = System.in;
            ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
            System.setIn(in);
            int option = GarageHolderUI.askOption(0, entry.getValue().size(), entry.getKey());
            inputIndex++;
            System.setIn(sysInBackup);
        }
    }

    @Test
    void storeOrderTest(){
        AssemAssist assemAssist = new AssemAssist();
        garageHolder.load(assemAssist);
    }
}
