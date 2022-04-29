package swop.Car.CarModel;

import swop.Car.CarModelSpecification;
import java.util.*;

public abstract class CarModel {
    private CarModelSpecification carModelSpecification = null;
    protected String name;
    protected Map<String, List<String>> validOptions;
    protected List<String> mandatoryParts;
    public static final SortedSet<String> types = new TreeSet<>(List.of("ModelA", "ModelB", "ModelC"));

    public void setCarModelSpecification(CarModelSpecification selected){
        if (!this.isValidSpecification(selected)){
            throw new IllegalArgumentException("invalid car specification for this model");
        }
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
     * @return Map of the valid options for a certain carmodel
     */
    public SortedMap<String, List<String>> getValidOptions(){
        return new TreeMap<>(this.validOptions);
    }

    /**
     * Will check if the generated list of parts contains all the parts.
     * @return true if all parts are valid
     */
    private boolean isValidSpecification(CarModelSpecification specification){
        for(Map.Entry<String,String> selectedMap: specification.getAllParts().entrySet()){
            String option = selectedMap.getKey();
            String selected = selectedMap.getValue();
            if (!validOptions.containsKey(option))
                return false;
            if (!validOptions.get(option).contains(selected))
                return false;
        }
        return this.satisfiesConstraints(specification);
    }

    /**
     * Check if the given CarModelSpecification satisfies the constraints for a carModel
     * @param specification the CarModelSpecification to check
     * @return True if the specification satisfies all constraints
     */
    private boolean satisfiesConstraints(CarModelSpecification specification){
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
