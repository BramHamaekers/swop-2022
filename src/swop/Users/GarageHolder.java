package swop.Users;

import swop.Car.CarModel;
import swop.Car.CarModelSpecification;
import swop.Car.CarOrder;
import swop.Car.ModelA;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.GarageHolderUI;

import java.util.*;

public class GarageHolder extends User{
    private Set<CarOrder> carOrders;

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
            String action = GarageHolderUI.indicatePlaceOrder();
			if (Objects.equals(action, "n")) return;

            this.generateOrder(assemAssist);

        } catch (CancelException e) {
			e.printMessage();
			}
    }
    
    /**
     * Will handle all the steps for generating a new valid order.
     * @param assemAssist assemAssist given the main program
     */
    private void generateOrder(AssemAssist assemAssist) {
        if (assemAssist == null) throw new IllegalArgumentException("assemAssist is null");
        try {
            int model = GarageHolderUI.indicateCarModel();
            CarModel carModel = createCarModel(model);
            GarageHolderUI.displayOrderingForm(carModel.getValidOptions());
            Map<String,Integer> carConfig = getFilledOrder(carModel.getValidOptions());
            Map<String, String> carOptions = this.mapConfigToOptions(carConfig, carModel.getValidOptions());
            CarModelSpecification spec = new CarModelSpecification(carOptions);
            carModel.setCarModelSpecification(spec);
			CarOrder order = this.placeOrder(assemAssist, carModel);
            GarageHolderUI.displayEstimatedTime(order);
    	} catch (CancelException e) {
    		e.printMessage();
		}
	}
    
    /**
     * Will return a map with options of car chosen by user.
     * @return carConfig as a map from part to integer
     * @throws CancelException CancelException when "CANCEL" is the input
     * @param validOptions
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
     * @param validOptions
     * @return map string part to selection string
     */
	private Map<String,String> mapConfigToOptions(Map<String, Integer> carConfig, Map<String, List<String>> validOptions) {
        if (carConfig == null) throw new IllegalArgumentException("carConfig is null");
        Map<String,String> carOpts = new HashMap<>();

        for (String component: carConfig.keySet()) {
            String option = validOptions.get(component).get(carConfig.get(component));
            carOpts.put(component, option);
        }

        return carOpts;
    }

	/**
	 * Creates new CarModel given the model/optionsMap
//	 * @param carOptions map from part to actual selection as a string
	 * @return created CarModel
	 */
    private CarModel createCarModel(int choice) {
            return new ModelA();
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
