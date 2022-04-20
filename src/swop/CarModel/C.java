package swop.CarModel;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class C {
	//hard coded
	LinkedHashMap<String, List<String>> optionsMap = new LinkedHashMap<>(){{
		put("Airco", Arrays.asList("manual", "automatic climate control"));
		put("Body",Arrays.asList("sport"));
		put("Color",Arrays.asList("black", "white"));
		put("Engine",Arrays.asList("performance 2.5l 6 cilinders", "ultra 3l v8"));
		put("GearBox",Arrays.asList("6 speed manual"));
		put("Seats",Arrays.asList("leather black", "leather white"));
		put("Wheels",Arrays.asList("comfort", "sports"));
		put("Spoiler",Arrays.asList("high", "low"));
	}};
}
