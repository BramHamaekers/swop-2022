package swop.Users;
import swop.CarManufactoring.CarModel;
import swop.CarManufactoring.CarOrder;
import swop.Database.Database;
import swop.Main.AssemAssist;
import swop.UI.GarageHolderUI;

import java.util.*;

public class GarageHolder extends User{
    private LinkedHashMap<String, List<String>> optionsMap;
    private Set<CarOrder> carOrders;

    public GarageHolder(String id) {
        super(id);
        this.setOptionsMap(Database.openDatabase("carOptions.json", "component", "options"));
        // TODO: move to different location? -> everytime new Garageholder loads optionsMap. Should not be the case
        // -> create static database somehow
        this.carOrders = new HashSet<>();
    }

    /**
     * Called when logging in as GarageHolder
     */
    @Override
    public void load(AssemAssist assemAssist) { //tODO split up in helper functions
        GarageHolderUI.init(this.getId());
        GarageHolderUI.displayOrders(this.getOrders());

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
        Map<String,Integer> carConfig = GarageHolderUI.fillOrderingForm(this.getOptionsMap());
        Map<String, String> carOptions = this.mapConfigToOptions(carConfig);
        CarModel carModel = createCarModel(carOptions);

        this.placeOrder(assemAssist, carModel);

        //TODO: update production schedule
        //TODO: present an estimated completion date

        this.logout();
    }

    private Map<String,String> mapConfigToOptions(Map<String, Integer> carConfig) {
        Map<String,String> carOpts = new HashMap<>();

        for (String component: carConfig.keySet()) {
            String option = this.getOptionsMap().get(component).get(carConfig.get(component));
            carOpts.put(component, option);
        }

        return carOpts;
    }

    private CarModel createCarModel(Map<String, String> carOptions) {
            return new CarModel(carOptions);
        }

    private void placeOrder(AssemAssist assemAssist, CarModel carModel){
         CarOrder carOrder = new CarOrder(carModel);
         this.addOrder(carOrder);
         assemAssist.addOrder(carOrder);
    }

    /************************ Checkers *************************/

    private boolean isValidModel(String model) {
        return Objects.equals(model, "0");
    }

    /************************ Orders *************************/
    public Set<CarOrder> getOrders() {
        return this.carOrders;
    }

    public void addOrder(CarOrder carOrder) {
        this.carOrders.add(carOrder);
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
