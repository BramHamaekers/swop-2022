package swop.Users;
import swop.CarManufactoring.Car;
import swop.CarManufactoring.CarModel;
import swop.CarManufactoring.Order;
import swop.Database.Database;
import swop.UI.GarageHolderUI;

import java.util.*;

public class GarageHolder extends User{
    private LinkedHashMap<String, List<String>> optionsMap;
    private Set<Order> orders;

    public GarageHolder(String id) {
        super(id);
        this.setOptionsMap(Database.openDatabase("carOptions.json", "component", "options"));
        // TODO: move to different location? -> everytime new Garageholder loads optionsMap. Should not be the case
        // -> create static database somehow
        this.orders = new HashSet<>();

        /////////// TEST ///////////
        List<Car> cars = new ArrayList<>();
        Map<String, String> parts = new HashMap<>();
        parts.put("body","sedan");
        parts.put("color","red");
        parts.put("engine","standard 2l 4 cilinders");
        parts.put("gearBox","6 speed manual");
        parts.put("seats","leather black");
        parts.put("airco","manual");
        parts.put("wheels","comfort");
        try {
            Car testCar = new Car(new CarModel(parts));
        }
        catch (IllegalAccessException e){
            System.out.println("Just testing");
        }
//        parts.add("wheeeeel");
//        Order a = new Order(parts);
//        this.addOrder(a);
//
//        Order b = new Order(parts);
//        b.upBuildState();
//        b.upBuildState();
//        b.upBuildState();
//        this.addOrder(b);
    }

    /**
     * Called when logging in as GarageHolder
     */
    public void load() {
        GarageHolderUI.init(this.getId());
        GarageHolderUI.displayOrders(this.orders);

        String action = GarageHolderUI.confirmAction();
        while (!isValidAction(action)) {
            action = GarageHolderUI.confirmAction();
        }
        if (Objects.equals(action, "n")) return;

        String model = GarageHolderUI.indicateCarModels();
        while (!isValidModel(model)) {
            model = GarageHolderUI.indicateCarModels();
        }

        GarageHolderUI.displayOrderingForm(this.getOptionsMap());
        //TODO alternate flow: cancel placing order
        List<Integer> order = GarageHolderUI.fillOrderingForm(this.getOptionsMap());

        //TODO: update production schedule
        //TODO: present an estimated completion date

        this.logout();
    }

    /************************ Checkers *************************/

    private boolean isValidAction(String action) {
        return Objects.equals(action, "y") || Objects.equals(action, "n");
    }

    private boolean isValidModel(String model) {
        return Objects.equals(model, "0");
    }

    /************************ Orders *************************/
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public LinkedHashMap<String, List<String>> getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap(LinkedHashMap<String, List<String>> optionsMap) {
        if (!isValidOptionsMap(optionsMap)) {
            System.out.println("Fail! optionsMap not valid"); //TODO: should throw error
        }
        this.optionsMap = optionsMap;
    }

    private boolean isValidOptionsMap(LinkedHashMap<String, List<String>> optionsMap) {
        return optionsMap != null; //TODO: better check if optionsmap is a valid map
    }
}
