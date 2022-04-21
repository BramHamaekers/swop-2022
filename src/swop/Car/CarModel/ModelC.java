package swop.Car.CarModel;

import java.util.List;
import java.util.Map;

public class ModelC extends CarModel {
    private static final List<String> modelBMandatory = List.of("Body","Color", "Engine", "Gearbox", "Seats", "Wheels");
    private static final Map<String, List<String>> modelBOptions = Map.of(
            "Body", List.of("sport"),
            "Color", List.of("black", "white"),
            "Engine", List.of("performance 2.5l v6", "ultra 3l v8"),
            "Gearbox", List.of("6 speed manual"),
            "Seats", List.of("leather white", "leather black"),
            "Airco", List.of("manual","automatic", "None"),
            "Wheels", List.of("winter", "sports"),
            "Spoiler", List.of("high", "low")
    );
    public ModelC(){
        this.name = "ModelC";
        this.validOptions = modelBOptions;
        this.mandatoryParts = modelBMandatory;
    }
}
