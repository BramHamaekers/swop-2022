package swop.Users;

import swop.Car.*;
import swop.Car.CarModel.CarModel;
import swop.Car.CarModel.ModelA;
import swop.Car.CarModel.ModelB;
import swop.Car.CarModel.ModelC;
import swop.Main.AssemAssist;

import java.util.*;

/**
 * A garage holder user
 */
public class GarageHolder extends User{
    private final Set<CarOrder> carOrders;

    /**
     * initializes a garage holder user
     * @param id a given id for the garage holder
     */
    public GarageHolder(String id, AssemAssist assemAssist) {
        super(id, assemAssist);
        this.carOrders = new HashSet<>();
    }

    /**
     * Converts Map of string to integer to Map of string to string
     * @param carConfig given a map from part to integer selection
     * @param validOptions the valid options for the carOptions
     * @return map string part to selection string
     */
	private Map<String,String> mapConfigToOptions(Map<String, Integer> carConfig, Map<String, List<String>> validOptions) {
        if (carConfig == null) throw new IllegalArgumentException("carConfig is null");
        if (validOptions == null) throw new IllegalArgumentException("given validOptions map is null");
        Map<String,String> carOpts = new HashMap<>();

        for (String component: carConfig.keySet()) {
            String option = validOptions.get(component).get(carConfig.get(component));
            if (!option.equals("None")) carOpts.put(component, option);
        }

        return carOpts;
    }

    public List<String> getModels(){
        return CarModel.types;
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
     * @param carModel given carmodel
     * @return completed carorder
     */
    private CarOrder placeOrderOnAssem(CarModel carModel){
        if (carModel == null) throw new IllegalArgumentException("carModel is null");
        if (this.assemAssist == null) throw new IllegalStateException("assemAssist not instantiated");
        CarOrder carOrder = new CarOrder(carModel);
        this.addOrder(carOrder);
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
        if (carOrder == null)
            throw new IllegalArgumentException("no valid carorder was specified");
        this.carOrders.add(carOrder);
    }

    /**
     * returns the valid options for a specified carmodel
     * @param model a specified model
     * @return the valid options for a specified carmodel
     */
    public SortedMap<String, List<String>> getValidOptions(CarModel model) {
        if (model == null)
            throw new IllegalArgumentException("no valid carmodel was specified");
        return model.getValidOptions();
    }

    /**
     * Place a valid order on the {@code CarManuFacturingController}
     * @param carConfig the carconfig the garageholder ordered
     * @param model the model the garageholder ordered
     * @return returns the order if it was valid, otherwise return null
     */
    public CarOrder placeOrder(Map<String, Integer> carConfig, CarModel model) {
        if (carConfig == null)
            throw new IllegalArgumentException("invalid config file specified");
        if (model == null)
            throw new IllegalArgumentException("no valid carmodel was specified");

        Map<String, String> carOptions = this.mapConfigToOptions(carConfig, model.getValidOptions());

        CarModelSpecification spec = new CarModelSpecification(carOptions);
        model.setCarModelSpecification(spec);
        return this.placeOrderOnAssem(model);
    }
}
