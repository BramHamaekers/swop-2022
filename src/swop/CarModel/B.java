package swop.CarModel;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class B {
	//hard coded
	LinkedHashMap<String, List<String>> optionsMap = new LinkedHashMap<>(){{
		put("Airco", Arrays.asList("manual", "automatic climate control"));
		put("Body",Arrays.asList("sedan", "break", "sport"));
		put("Color",Arrays.asList("red", "blue", "green", "yellow"));
		put("Engine",Arrays.asList("standard 2l 4 cilinders", "performance 2.5l 6 cilinders", "ultra 3l v8"));
		put("GearBox",Arrays.asList("6 speed manual", "5 speed automatic"));
		put("Seats",Arrays.asList("leather black", "leather white", "vinyl grey"));
		put("Wheels",Arrays.asList("comfort", "sports", "winter"));
		put("Spoiler",Arrays.asList("low"));
	}};
}
