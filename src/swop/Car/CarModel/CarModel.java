package swop.Car.CarModel;

import swop.Car.CarModelSpecification;
import java.util.*;

/**
 * The super class for all car models
 */
public abstract class CarModel {
    private CarModelSpecification carModelSpecification = null;
    protected String name;
    protected Map<String, List<String>> validOptions;
    protected List<String> mandatoryParts;
    public static final List<String> types = List.of("ModelA", "ModelB", "ModelC");

    /**
     * set a selection specification for this carModel if it is valid
     * @param selected a selected {@code CarModelSpecification}
     */
    public void setCarModelSpecification(CarModelSpecification selected){
        if (!this.isValidSpecification(selected))
            throw new IllegalArgumentException("invalid car specification for this model");

        this.carModelSpecification = selected;
    }

    /**
     * returns the carModelSpecifiaction of this model.
     * @return carModelSpecification
     */
    public CarModelSpecification getCarModelSpecification() {
        return this.carModelSpecification;
    }

    /**
     * Get all valid carOptionCategories and their options as sorted Map
     * @return Map of the valid options for a certain car model
     */
    public SortedMap<String, List<String>> getValidOptions(){
        return new TreeMap<>(this.validOptions);
    }

    /**
     * Will check if the generated list of parts contains all the parts.
     * @param specification the specification for the carModel
     * @return true if all parts are valid
     */
    private boolean isValidSpecification(CarModelSpecification specification){
        if (specification == null){
            return false;
        }
        for(Map.Entry<String,String> selectedMap: specification.getAllParts().entrySet()){	
            if (!this.IsValidOption(selectedMap.getKey(), selectedMap.getValue())) return false;
        }
        return this.satisfiesConstraints(specification);
    }

    /**
     * Checks if a given option/value combination is valid
     * @param option that needs to be checked
     * @param value that needs to be checked
     * @return false if !validOptions.contains(option/value) else true
     */
	private boolean IsValidOption(String option, String value) {
        return (option != null) && (value != null) && 
        		validOptions.containsKey(option) && validOptions.get(option).contains(value);
    }

    /**
     * Check if the given CarModelSpecification satisfies the constraints for a carModel
     * @param specification the CarModelSpecification to check
     * @return True if the specification satisfies all constraints
     */
    private boolean satisfiesConstraints(CarModelSpecification specification){
        if (specification == null)
            throw new IllegalArgumentException("Provided CarModelSpecification is null");
        for(String part : this.mandatoryParts){
            if (!specification.getAllParts().containsKey(part))
                return false;
        }
        return true;
    }

    /**
     * Get the name of this model
     * @return this.name
     */
    public String getName() {
        return this.name;
    }
}
