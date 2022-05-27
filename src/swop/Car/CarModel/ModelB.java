package swop.Car.CarModel;

import java.util.List;
import java.util.Map;

/**
 * A carModel subclass
 */
public class ModelB extends CarModel {

    /**
     * A list of mandatory Parts of a model B Car
     */
    private static final List<String> modelBMandatory = List.of("Body","Color", "Engine", "Gearbox", "Seats", "Wheels");

    /**
     * A Map that maps all valid options of a carModel B to every part
     */
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

    /**
     * initialize the carModel with a name, valid options and a list of mandatory parts
     */
    public ModelB(){
        this.name = "ModelB";
        this.validOptions = modelBOptions;
        this.mandatoryParts = modelBMandatory;
    }
}
