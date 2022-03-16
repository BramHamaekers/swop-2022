package swop.Users;
import swop.CarManufactoring.CarModel;
import swop.CarManufactoring.CarOrder;
import swop.Database.Database;
import swop.Exceptions.CancelException;
import swop.Main.AssemAssist;
import swop.Parts.Body;
import swop.UI.GarageHolderUI;

import java.util.*;

public class GarageHolder extends User{
    private LinkedHashMap<String, List<String>> optionsMap = new LinkedHashMap<String, List<String>>(){{ 
    	//TODO dit nog aanpassen in toekomst, kga nu niet doen, te tam
		put("Airco", Arrays.asList(new String[] {"manual", "automatic climate control"}));
		put("Body",Arrays.asList(new String[] {"sedan", "break"}));
		put("Color",Arrays.asList(new String[] {"red", "blue", "black", "white"}));
		put("Engine",Arrays.asList(new String[] {"standard 2l 4 cilinders", "performance 2.5l 6 cilinders"}));
		put("GearBox",Arrays.asList(new String[] {"6 speed manual", "5 speed automatic"}));
		put("Seats",Arrays.asList(new String[] {"leather black", "leather white", "vinyl grey"}));
		put("Wheels",Arrays.asList(new String[] {"comfort", "sports (low profile)"}));
	}};
    private Set<CarOrder> carOrders;

    public GarageHolder(String id) {
        super(id);
        this.carOrders = new HashSet<>();
        
        //optionMap genereren adhv JSON (Laat het er maar bijstaan verlopig)
        //this.setOptionsMap(Database.openDatabase("carOptions.json", "component", "options"));
    }

    /**
     * Called when logging in as GarageHolder
     */
    @Override
    public void load(AssemAssist assemAssist) { 
    	try {
    		GarageHolderUI.init(this.getId());
    		GarageHolderUI.displayOrders(this.getOrders());  
    		String action;
			action = GarageHolderUI.indicatePlaceOrder();
			if (Objects.equals(action, "n")) return;
			
			if(!this.generateOrder(assemAssist)) return;
  
    	} catch (CancelException e) {
			e.printMessage();
			}
    }
    
    /**
     * Will handle all the steps for generating a new valid order.
     * @param assemAssist
     * @return
     */
    private boolean generateOrder(AssemAssist assemAssist) {
    	try {
			int model = GarageHolderUI.indicateCarModel();
			GarageHolderUI.displayOrderingForm(this.getOptionsMap());
			Map<String,Integer> carConfig = getFilledOrder();
			Map<String, String> carOptions = this.mapConfigToOptions(carConfig);
			CarModel carModel = createCarModel(model,carOptions);
			CarOrder order = this.placeOrder(assemAssist, carModel);
            GarageHolderUI.displayEstimatedTime(order);
			return true;
    	} catch (CancelException e) {
    		e.printMessage();
			return false;
		}
	}
    
    /**
     * Will return a map with options of car chosen by user.
     * @return carConfig
     * @throws CancelException when user wants 2 cancel his order
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
     * @param carConfig
     * @return
     */
	private Map<String,String> mapConfigToOptions(Map<String, Integer> carConfig) {
        Map<String,String> carOpts = new HashMap<>();

        for (String component: carConfig.keySet()) {
            String option = this.getOptionsMap().get(component).get(carConfig.get(component));
            carOpts.put(component, option);
        }

        return carOpts;
    }

	/**
	 * Creates new CarModel given the model/optionsMap
	 * @param model
	 * @param carOptions
	 * @return
	 */
    private CarModel createCarModel(int model, Map<String, String> carOptions) {
            return new CarModel(model,carOptions);
        }
    
    /**
     * Creates and stores a new CarOrder given a carModel
     * @param assemAssist
     * @param carModel
     * @return
     */
    private CarOrder placeOrder(AssemAssist assemAssist, CarModel carModel){
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
