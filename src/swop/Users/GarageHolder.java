package swop.Users;
import swop.CarManufactoring.Car;
import swop.CarManufactoring.CarModel;
import swop.CarManufactoring.Order;
import swop.Database.Database;
import swop.Main.AssemAssist;
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
    }

    /**
     * Called when logging in as GarageHolder
     */
    @Override
    public void load(AssemAssist assemAssist) { //tODO split up in helper functions
        GarageHolderUI.init(this.getId());
        GarageHolderUI.displayOrders(this.orders);

        String action = GarageHolderUI.indicatePlaceOrder();
        while (!isValidYesNo(action)) {
            action = GarageHolderUI.indicatePlaceOrder();
        }
        if (Objects.equals(action, "n")) return;

        String model = GarageHolderUI.indicateCarModel();
        while (!isValidModel(model)) {
            model = GarageHolderUI.indicateCarModel();
        }

        GarageHolderUI.displayOrderingForm(this.getOptionsMap());
        //TODO alternate flow: cancel placing order
        List<Map<String,Integer>> order = GarageHolderUI.fillOrderingForm(this.getOptionsMap());
        this.placeOrder(order);

        //TODO: update production schedule
        //TODO: present an estimated completion date

        this.logout();
    }

    /**
     * function which transforms the list of cars ordered from UI to a list with the actual parts
     * @param order order in selected options
     */
    private void placeOrder(List<Map<String, Integer>> order){
        List<Car> resultingOrder = new ArrayList<>();

        for(Map<String, Integer> car : order) {
            Map<String,String> carOpts = new HashMap<>();
            for (String comp: car.keySet()){
                String option = this.getOptionsMap().get(comp).get(car.get(comp));
                carOpts.put(comp,option);
            }
            try{
                Car finalcar = new Car(new CarModel(carOpts));
                resultingOrder.add(finalcar);
            }
            catch (IllegalAccessException e) {
                System.out.println(e);
            }
        }
            //TODO resultingOrder in aparte functie
            this.addOrder(new Order(resultingOrder));
    }

    /************************ Checkers *************************/

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
