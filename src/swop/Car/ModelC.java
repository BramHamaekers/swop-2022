package swop.Car;

import java.util.List;
import java.util.Map;

public class ModelC extends CarModel{
    private static final List<String> modelBMandatory = List.of("Body","Color", "Engine", "Gearbox", "Seats", "Wheels");
    //TODO: need to rewrite
    private static final Map<String, List<String>> modelBOptions = Map.of(
            "Body", List.of("sedan", "break"),
            "Color", List.of("red", "blue", "black", "white"),
            "Engine", List.of("standard 2l v4", "performance 2.5l v6"),
            "Gearbox", List.of("6 speed manual", "5 speed manual", "5 speed automatic"),
            "Seats", List.of("leather white", "leather black", "vinyl grey"),
            "Airco", List.of("manual","automatic", "None"),
            "Wheels", List.of("winter", "comfort", "sports")
    );
    public ModelC(){
        this.name = "ModelC";
        this.validOptions = modelBOptions;
        this.mandatoryParts = modelBMandatory;
    }
}
