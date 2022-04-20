package swop.Car.CarModel;

import java.util.List;
import java.util.Map;

public class ModelA extends CarModel {
    private static final List<String> modelAMandatory = List.of("Body","Color", "Engine", "Gearbox", "Seats", "Wheels");
    private static final Map<String, List<String>> modelAOptions = Map.of(
            "Body", List.of("sedan", "break"),
            "Color", List.of("red", "blue", "black", "white"),
            "Engine", List.of("standard 2l v4", "performance 2.5l v6"),
            "Gearbox", List.of("6 speed manual", "5 speed manual", "5 speed automatic"),
            "Seats", List.of("leather white", "leather black", "vinyl grey"),
            "Airco", List.of("manual","automatic", "None"),
            "Wheels", List.of("winter", "comfort", "sports")
    );
    public ModelA(){
        this.name = "ModelA";
        this.validOptions = modelAOptions;
        this.mandatoryParts = modelAMandatory;
    }
}
