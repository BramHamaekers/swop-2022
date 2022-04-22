package swop.Users;

import swop.Car.*;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModel.ModelB;
import swop.Car.CarModel.ModelC;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.GarageHolderUI;

import java.util.*;

public class GarageHolder extends User{
    private final Set<CarOrder> carOrders;

    public GarageHolder(String id) {
        super(id);
        this.carOrders = new HashSet<>();
    }

    /**
     * Called when logging in as GarageHolder
     * @param assemAssist given the main program
     */
    @Override
    public void load(AssemAssist assemAssist) {
        if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
        try {
    		GarageHolderUI.init(this.getId());
    		GarageHolderUI.displayOrders(this.getOrders());
            this.selectAction(assemAssist);
        } catch (CancelException e) {
			e.printMessage();
			}
    }

    /**
     * Function that handles selecting an action for GarageHolder
     * @param assemAssist the central system the action is performed on
     */
    @Override
    public void selectAction(AssemAssist assemAssist) throws CancelException {
        List<String> actions = Arrays.asList("Place new order", "Check order details", "Exit");
        int action = GarageHolderUI.selectAction(actions, "What would you like to do?");

        switch (action) {
            case 0 -> this.generateOrder(assemAssist);
            case 1 -> this.checkOrderDetails();
            case 2 -> {
                // Do Nothing
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    private void checkOrderDetails() throws CancelException {
        String orderID =  GarageHolderUI.selectOrderID();
        while (!isValidOrderID(orderID)) {
            GarageHolderUI.printError("Please provide a valid orderID");
            orderID =  GarageHolderUI.selectOrderID();
        }
        CarOrder carOrder = getOrderFromID(orderID);

        //TODO: use garaholderUI
        System.out.println(carOrder.toString());

        String answer = GarageHolderUI.indicateYesNo("Would you like to view another order?");
        if (answer.equals("y")) checkOrderDetails();


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
     * Will handle all the steps for generating a new valid order.
     * @param assemAssist assemAssist given the main program
     */
    private void generateOrder(AssemAssist assemAssist) {
        if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
        try {
            // Select Model
            int model = GarageHolderUI.indicateCarModel(CarModel.types);
            CarModel carModel = createCarModel(model);

            // Ordering From
            this.fillOrderingForm(carModel);

            // Create & Place Order
			CarOrder order = this.placeOrder(assemAssist, carModel);
            GarageHolderUI.displayEstimatedTime(order);
    	} catch (CancelException e) {
    		e.printMessage();
		}
	}

    /**
     * Access the GarageHolderUI to fill in the ordering form and create a carModelSpecification
     * @param carModel the carModel to create a carModelSpecification for
     * @throws CancelException when user types 'cancel'
     */
    private void fillOrderingForm(CarModel carModel) throws CancelException {
        while(true) {
            GarageHolderUI.displayOrderingForm(carModel.getValidOptions(),carModel.getName());
            Map<String,Integer> carConfig = getFilledOrder(carModel.getValidOptions());
            Map<String, String> carOptions = this.mapConfigToOptions(carConfig, carModel.getValidOptions());

            try {
                CarModelSpecification spec = new CarModelSpecification(carOptions);
                carModel.setCarModelSpecification(spec);
                break;
            }
            catch (IllegalArgumentException e){
                GarageHolderUI.printError(e.getMessage());
            }
        }
    }

    /**
     * Will return a map with options of car chosen by user.
     * @param validOptions valid options for this carConfiguration
     * @return carConfig as a map from part to integer
     * @throws CancelException when "CANCEL" is the input

     */
    private Map<String,Integer> getFilledOrder(Map<String, List<String>> validOptions) throws CancelException{
    	Map<String,Integer> carConfig = new HashMap<>();
		for (var entry : validOptions.entrySet()) {
			int option = GarageHolderUI.askOption(0, entry.getValue().size(), entry.getKey());
			carConfig.put(entry.getKey(), option);
		}
		return carConfig;
    }

    /**
     * Converts Map<String, Integer> to Map<String,String>
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

	/**
	 * Creates new CarModel given the model/optionsMap
//	 * @param carOptions map from part to actual selection as a string
	 * @return created CarModel
	 */
    private CarModel createCarModel(int choice) {
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
    private CarOrder placeOrder(AssemAssist assemAssist, CarModel carModel){
        if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
        if (carModel == null) throw new IllegalArgumentException("carModel is null");
        CarOrder carOrder = new CarOrder(carModel);
        this.addOrder(carOrder);
        assemAssist.addOrder(carOrder);
        return carOrder;
    }

    
    

    /************************ Orders *************************/
    public Set<CarOrder> getOrders() {
        return this.carOrders;
    }

    public void addOrder(CarOrder carOrder) {
        this.carOrders.add(carOrder);
    }

}
