package swop.Car.CarModel;

import java.util.List;
import java.util.Map;

public class ModelB extends CarModel {
    private static final List<String> modelBMandatory = List.of("Body","Color", "Engine", "Gearbox", "Seats", "Wheels");
    private static final Map<String, List<String>> modelBOptions = Map.of(
            "Body", List.of("sedan", "break", "sport"),
            "Color", List.of("red", "blue", "green", "yellow"),
            "Engine", List.of("standard 2l v4", "performance 2.5l v6", "ultra 3l v8"),
            "Gearbox", List.of("6 speed manual", "5 speed automatic"),
            "Seats", List.of("leather white", "leather black", "vinyl grey"),
            "Airco", List.of("manual","automatic", "None"),
            "Wheels", List.of("winter", "comfort", "sports"),
            "Spoiler", List.of("low", "None")
    );
    public ModelB(){
        this.name = "ModelB";
        this.validOptions = modelBOptions;
        this.mandatoryParts = modelBMandatory;
    }
}
