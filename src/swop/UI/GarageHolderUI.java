package swop.UI;
import swop.CarManufactoring.CarOrder;

import java.util.*;
import java.util.List;

public class GarageHolderUI implements UI {

	// Dummy init
	public static void init(String id) {
		System.out.println("Welcome Garage Holder: " + id);
	}

	public static void displayOrders(Set<CarOrder> carOrders) {
		if (carOrders == null) {
			System.out.println("No carOrders placed yet.");
			return;
		}
		System.out.printf("%n============ Orders ============%n");
		System.out.println("Pending:");
		carOrders.stream()
				.filter(o -> !o.isCompleted())
				.forEach(p -> System.out.println(p.getCar().getParts()));
		System.out.println();
		System.out.println("Completed:");
		carOrders.stream()
				.filter(o -> o.isCompleted())
				.forEach(c -> System.out.println(c.getCar().getParts()));
		System.out.println("=======================================");
	}

	public static String indicatePlaceOrder() {
		return UI.indicateYesNo("place an order");
	}

	public static int indicateCarModel() {
		System.out.printf("%n============ Car Models ============%n");
		System.out.println("[0] car");
		System.out.println("=======================================");
		System.out.println();
		System.out.printf("Which model would you like to order?%n");
		return scanner.scanNextLineOfTypeInt(0,1); ///we kunnen ook hun de naam laten typen
	}

	/**
	 * Displays the ordering from given a list of components and its options
	 * @param optionsMap list of components and its options
	 */
	public static void displayOrderingForm(Map<String, List<String>> optionsMap) {
		System.out.printf("%n============ Ordering Form ============%n");
		optionsMap.forEach((key, value) -> {
			System.out.print(key + ": ");
			final int[] itemNumber = {-1};
			value.forEach(v -> System.out.printf("[%s] %s, ", itemNumber[0] += 1, v));
			System.out.printf("%n");
		});
		System.out.println("=======================================");
	}


	public static Map<String,Integer> fillOrderingForm(Map<String, List<String>> optionsMap) {
		System.out.println("Choose options:");
		//This can handle 1 car for each order.	
		Map<String,Integer> carConfig = new HashMap<>();

		optionsMap.forEach((option, options) -> {
			System.out.print(option + ": ");
			int input = scanner.scanNextLineOfTypeInt(0, optionsMap.get(option).size());
			carConfig.put(option,input);
		});
		return carConfig;
	}

}
