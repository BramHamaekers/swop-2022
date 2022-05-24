package swop.Users;

import swop.Car.*;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModel.ModelB;
import swop.Car.CarModel.ModelC;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.GarageHolderUI;
import swop.UI.TempGarUI;

import java.util.*;

/**
 * A garage holder user
 */
public class GarageHolder extends User{
    private final Set<CarOrder> carOrders;
    private final AssemAssist assemAssist;

    /**
     * initializes a garage holder user
     * @param id a given id for the garage holder
     */
    public GarageHolder(String id, AssemAssist assemAssist) {
        super(id);
        this.carOrders = new HashSet<>();
        this.assemAssist = assemAssist;
    }

    /**
     * Called when logging in as GarageHolder
     * @param assemAssist given the main program
     */
    @Override
    public void load(AssemAssist assemAssist) {
    }


    /**
     * Function that handles selecting an action for GarageHolder
     * @param assemAssist the central system the action is performed on
     */
    @Override
    public void selectAction(AssemAssist assemAssist) throws CancelException {
    }

    /**
     * Handles the selection of the order to view the details
     * @throws CancelException when the user wants to cancel viewing details of orders
     */
    private void checkOrderDetails() throws CancelException {
        String question = "n";
        do {
//            if (question.equals("y")) GarageHolderUI.displayOrders(this.getOrders());
            String orderID = GarageHolderUI.selectOrderID();
            while (!isValidOrderID(orderID)) {
                GarageHolderUI.printError("Please provide a valid orderID");
                orderID = GarageHolderUI.selectOrderID();
            }
            CarOrder carOrder = getOrderFromID(orderID);
            GarageHolderUI.showOrderDetails(carOrder.toString());
            question = GarageHolderUI.indicateYesNo("Would you like to view another order?");
        } while (question.equals("y"));

    }

    /**
     * Returns a carOrder from this user given its orderID
     * @param orderID the given orderID
     * @return the CarOrder with the orderID
     */
    private CarOrder getOrderFromID(String orderID) {
        return this.getOrders().stream()
                .filter(o -> o.getID().equalsIgnoreCase(orderID))
                .findFirst().orElse(null);
    }

    /**
     * Checks whether the given orderID is a valid ID
     * @param orderID the given orderID
     * @return whether the orderID is valid
     */
    private boolean isValidOrderID(String orderID) {
        return this.getOrders().stream().anyMatch(o -> o.getID().equalsIgnoreCase(orderID));
    }


    /**
     * Converts Map of string to integer to Map of string to string
     * @param carConfig given a map from part to integer selection
     * @param validOptions the valid options for the carOptions
     * @return map string part to selection string
     */
	private Map<String,String> mapConfigToOptions(Map<String, Integer> carConfig, Map<String, List<String>> validOptions) {
        if (carConfig == null) throw new IllegalArgumentException("carConfig is null");
        Map<String,String> carOpts = new HashMap<>();

        for (String component: carConfig.keySet()) {
            String option = validOptions.get(component).get(carConfig.get(component));
            if (!option.equals("None")) carOpts.put(component, option);
        }

        return carOpts;
    }

    public List<String> getModels(){
        return List.of("ModelA","ModelB","ModelC");
    }

	/**
	 * Creates new CarModel given the model/optionsMap
     * @param choice the choice for a carmodel the user made
	 * @return created CarModel
	 */
    public CarModel createCarModel(int choice) {
            return switch (choice) {
                case 0 -> new ModelA();
                case 1 -> new ModelB();
                case 2 -> new ModelC();
                default -> throw new IllegalArgumentException("Unexpected value: " + choice);
            };
        }
    
    /**
     * Creates and stores a new CarOrder given a carModel
     * @param assemAssist given the main program
     * @param carModel given carmodel
     * @return completed carorder
     */
    private CarOrder placeOrderOnAssem(AssemAssist assemAssist, CarModel carModel){
        if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
        if (carModel == null) throw new IllegalArgumentException("carModel is null");
        CarOrder carOrder = new CarOrder(carModel);
        this.addOrder(carOrder);
        //TODO remove
        this.assemAssist.addOrder(carOrder);
        return carOrder;
    }

    /**
     * get the carOrders of this user
     * @return the car orders for the garage holder
     */
    public Set<CarOrder> getOrders() {
        return this.carOrders;
    }

    /**
     * Add an order to the CarOrders of this user
     * @param carOrder the given order to add
     */
    public void addOrder(CarOrder carOrder) {
        this.carOrders.add(carOrder);
    }

    /**
     * returns the valid options for a specified carmodel
     * @param model a specified model
     * @return the valid options for a specified carmodel
     */
    public SortedMap<String, List<String>> getValidOptions(CarModel model) {
        return model.getValidOptions();
    }

    /**
     * Place a valid order on the {@code CarManuFacturingController}
     * @param carConfig the carconfig the garageholder ordered
     * @param model the model the garageholder ordered
     * @return returns the order if it was valid, otherwise return null
     */
    public CarOrder placeOrder(Map<String, Integer> carConfig, CarModel model) {
        Map<String, String> carOptions = this.mapConfigToOptions(carConfig, model.getValidOptions());

        try {
            CarModelSpecification spec = new CarModelSpecification(carOptions);
            model.setCarModelSpecification(spec);
            return this.placeOrderOnAssem(assemAssist, model);
        }
        catch (IllegalArgumentException e){
            GarageHolderUI.printError(e.getMessage());
            return null;
        }
    }
}
