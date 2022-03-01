package swop.UI;
import swop.CarManufactoring.Order;

import java.util.*;
import java.util.List;

public class GarageHolderUI implements UI {

	// Dummy init
	public static void init(String id) {
		System.out.println("Welcome Garage Holder: " + id);
	}

	public static void displayOrders(Set<Order> orders) {
		if (orders == null) {
			System.out.println("No orders placed yet.");
			return;
		}
		System.out.printf("%n============ Orders ============%n");
		System.out.println("Pending:");
		orders.stream()
				.filter(o -> !o.isCompleted())
				.forEach(p -> System.out.println(p.getUniqueID()));
		System.out.println();
		System.out.println("Completed:");
		orders.stream()
				.filter(o -> o.isCompleted())
				.forEach(c -> System.out.println(c.getUniqueID()));
		System.out.println("=======================================");
	}

	public static String confirmAction() {
		System.out.println();
		System.out.printf("Do you want to place an order? %n[y] Place Order [n] Leave%n");
		return inputScanner.nextLine();
	}

	public static String indicateCarModel() {
		System.out.printf("%n============ Car Models ============%n");
		System.out.println("[0] car");
		System.out.println("=======================================");
		System.out.println();
		System.out.printf("Which model would you like to order?%n");
		return inputScanner.nextLine();
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


	public static List<Integer> fillOrderingForm(Map<String, List<String>> optionsMap) {
		System.out.println("Choose options:");
		List<Integer> result = new ArrayList<>();

		optionsMap.forEach((option, options) -> {
			int input = -1; //init on !isValidOption
			while (!isValidOption(input, optionsMap.get("body"))) {
				System.out.print(option + ": ");
				input = inputScanner.nextInt(); //TODO: catch nextInt Error
			}
			result.add(input);
		});
		inputScanner.nextLine(); // Fixes small problem with .nextInt() not clearing "\n" from its buffer
		return result;
	}

	private static boolean isValidOption(int option, List<String> options) {
		return option < options.size() && option > -1;
	}
}
