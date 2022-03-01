package swop.UI;
import swop.CarManufactoring.Order;

import java.awt.*;
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
		System.out.println();
		System.out.println("============ Orders ============");
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

	public static String indicateCarModels() {
		System.out.println();
		System.out.println("============ Car Models ============");
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
		System.out.println();
		System.out.println("============ Ordering Form ============");
		optionsMap.forEach((key, value) -> {
			System.out.print(key + ": ");
			final int[] itemNumber = {-1};
			value.forEach(v -> System.out.printf("[%s] %s, ", itemNumber[0] += 1, v));
			System.out.printf("%n");
		});
		System.out.println("=======================================");
	}


	public static List<Integer> fillOrderingForm(Map<String, List<String>> optionsMap) {
		System.out.println();
		List<Integer> result = new ArrayList<>();
		optionsMap.forEach((option, options) -> {
			System.out.print(option + ": ");
			int input = inputScanner.nextInt(); //TODO: catch nextInt Error
			while (!isValidOption(input, optionsMap.get("body"))) {
				System.out.print(option + ": ");
				input = inputScanner.nextInt();
			}
			result.add(input);
		});
		return result;
	}

	private static boolean isValidOption(int option, List<String> options) {
		return option < options.size() && option > -1;
	}
}
