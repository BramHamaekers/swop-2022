package swop.Car.CarModel;

import swop.Car.CarModelSpecification;
import java.util.*;

public abstract class CarModel {
    private CarModelSpecification carModelSpecification = null;
    //todo check if still necessary
    protected String name;
    protected Map<String, List<String>> validOptions;
    protected List<String> mandatoryParts;
    public static final Set<String> types = new HashSet<>(List.of("ModelA", "ModelB", "ModelC"));

    public void setCarModelSpecification(CarModelSpecification selected){
        if (!this.isValidSpecification(selected)){
            throw new IllegalArgumentException("invalid car specification for this model");
        }
        this.carModelSpecification = selected;
    };

    public CarModelSpecification getCarModelSpecification() {
        return this.carModelSpecification;
    }

    public Map<String, List<String>> getValidOptions(){
        return this.validOptions;
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

    private boolean satisfiesConstraints(CarModelSpecification specification){
        for(String part : this.mandatoryParts){
            if (!specification.getAllParts().containsKey(part))
                return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }
}
