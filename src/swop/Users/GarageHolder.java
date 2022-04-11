package swop.Users;
import swop.CarManufactoring.CarModel;
import swop.CarManufactoring.CarOrder;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.UI.GarageHolderUI;

import java.util.*;

public class GarageHolder extends User{
    private final LinkedHashMap<String, List<String>> optionsMap = new LinkedHashMap<>(){{
		put("Airco", Arrays.asList("manual", "automatic climate control"));
		put("Body",Arrays.asList("sedan", "break"));
		put("Color",Arrays.asList("red", "blue", "black", "white"));
		put("Engine",Arrays.asList("standard 2l 4 cilinders", "performance 2.5l 6 cilinders"));
		put("GearBox",Arrays.asList("6 speed manual", "5 speed automatic"));
		put("Seats",Arrays.asList("leather black", "leather white", "vinyl grey"));
		put("Wheels",Arrays.asList("comfort", "sports (low profile)"));
	}};
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
			GarageHolderUI.displayOrderingForm(this.getOptionsMap());
			Map<String,Integer> carConfig = getFilledOrder();
			Map<String, String> carOptions = this.mapConfigToOptions(carConfig);
			CarModel carModel = createCarModel(model,carOptions);
//            System.out.println(carOptions);
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
     */
    private Map<String,Integer> getFilledOrder() throws CancelException{
    	Map<String,Integer> carConfig = new HashMap<>();
		for (var entry : this.getOptionsMap().entrySet()) {
			int option = GarageHolderUI.askOption(0, entry.getValue().size(), entry.getKey());
			carConfig.put(entry.getKey(), option);
		}
		return carConfig;
    }

    /**
     * Converts Map<String, Integer> to Map<String,String>
     * @param carConfig given a map from part to integer selection
     * @return map string part to selection string
     */
	private Map<String,String> mapConfigToOptions(Map<String, Integer> carConfig) {
        if (carConfig == null) throw new IllegalArgumentException("carConfig is null");
        Map<String,String> carOpts = new HashMap<>();

        for (String component: carConfig.keySet()) {
            String option = this.getOptionsMap().get(component).get(carConfig.get(component));
            carOpts.put(component, option);
        }

        return carOpts;
    }

	/**
	 * Creates new CarModel given the model/optionsMap
	 * @param model given model number
	 * @param carOptions map from part to actual selection as a string
	 * @return created CarModel
	 */
    private CarModel createCarModel(int model, Map<String, String> carOptions) {
            if (carOptions == null) throw new IllegalArgumentException("carOptions is null");
            return new CarModel(model,carOptions);
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

    public LinkedHashMap<String, List<String>> getOptionsMap() {
        return optionsMap;
    }

}
