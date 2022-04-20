package swop.UI;

import swop.Car.CarOrder;
import swop.Exceptions.CancelException;
import swop.UI.Generators.GarageHolderGenerator;

import java.util.*;
import java.util.List;

public class GarageHolderUI implements UI {

	private static final GarageHolderGenerator garageHolderGenerator = new GarageHolderGenerator();

	// Dummy init
	public static void init(String id) {
		System.out.println("Welcome Garage Holder: " + id + " (You can cancel any action by typing: CANCEL)");
	}

	public static void printError(String e) {
		UI.printError(e);
	}

	/**
	 * Displays all orders ordered by current garage holder
	 */
	public static void displayOrders(Set<CarOrder> carOrders) {
		if (carOrders == null) {
			System.out.println("No carOrders placed yet.");
			return;
		}
		System.out.printf("%n============ Orders ============%n");
		System.out.println("Pending:");
		carOrders.stream()
				.filter(o -> !o.isCompleted())
				.forEach(p ->{
					System.out.print("Order: "+ p.getID());
					System.out.println(" -> " + p.getEstimatedCompletionTime());
				});
		System.out.println();
		System.out.println("Completed:");

		Set<CarOrder> completedSet = new TreeSet<>();

		// Add to sorted set
		carOrders.stream()
				.filter(CarOrder::isCompleted)
				.forEach(completedSet::add);

		// Print sorted set
		completedSet.forEach(c -> {
					System.out.println("Order: " + c.getID() + " " + c.getCompletionTime().get("day") + " " + c.getCompletionTime().get("minutes"));
				});
		System.out.println();
		System.out.println("=======================================");
	}

	public static String indicateYesNo(String action) throws CancelException {
		return UI.indicateYesNo(action);
	}
	
	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static int selectAction(List<String> actions) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		garageHolderGenerator.selectAction(builder, actions);
		System.out.println(builder.getDisplay());
		return scanner.scanNextLineOfTypeInt(0, actions.size());
	}

	/**
	 * Ask which carModel the user wants to order
	 * @param carModels available carModels to choose from
	 * @return int indicating the chosen carModel
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static int indicateCarModel(Set<String> carModels) throws CancelException {
		DisplayStatus builder = new DisplayStatus();
		garageHolderGenerator.generateCarModels(builder, carModels);
		System.out.println(builder.getDisplay());
		return scanner.scanNextLineOfTypeInt(0,carModels.size());
	}

	/**
	 * Displays the ordering from given a list of components and its options
	 * @param optionsMap list of components and its options
	 */
	public static void displayOrderingForm(Map<String, List<String>> optionsMap, String name) {
		DisplayStatus builder = new DisplayStatus();
		garageHolderGenerator.generateOrderingForm(builder, optionsMap, name);
		System.out.print(builder.getDisplay());
	}


	public static int askOption (int leftBound, int rightBound, String option) throws CancelException {
		System.out.print("Choose " + option + ": ");
		return scanner.scanNextLineOfTypeInt(leftBound, rightBound);
	}

	/**
	 * Prints the given estimated completion time.
	 * @param order the estimated completion time to be displayed
	 */
    public static void displayEstimatedTime(CarOrder order) {
		System.out.printf("%n============ Estimated Completion Time ============%n");
		System.out.println(order.getEstimatedCompletionTime());
		System.out.println("=======================================");
	}

	/**
	 * Lets the user input an orderID
	 * @return the orderID that the user gave as input
	 */
	public static String selectOrderID() throws CancelException {
		System.out.print("Input an orderID: ");
		return scanner.scanNextLineOfTypeString();
	}
}
