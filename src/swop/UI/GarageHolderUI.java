package swop.UI;

import swop.Car.CarOrder;
import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
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
	 * Displays all orders ordered, splits unfinished and finished cars
	 * @param carOrders a set of all the carorders
	 */
	public static void displayOrders(Set<CarOrder> carOrders) {
		if (carOrders == null) {
			System.out.println("No carOrders placed yet.");
			return;
		}
		DisplayStatus builder = new DisplayStatus();
		garageHolderGenerator.generateOrderStatus(builder, carOrders);
		System.out.println(builder.getDisplay());
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
	public static int selectAction(List<String> actions, String question) throws CancelException {
		return UI.selectAction(garageHolderGenerator, actions, question);
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
		System.out.println(builder.getDisplay());
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
		DisplayStatus builder = new DisplayStatus();
		garageHolderGenerator.generateEstimatedTime(builder, order);
	}
    public static void printEmptyLine() {
    	System.out.println("");
    }

	/**
	 * Lets the user input an orderID
	 * @return the orderID that the user gave as input
	 */
	public static String selectOrderID() throws CancelException {
		System.out.print("Input an orderID: ");
		return scanner.scanNextLineOfTypeString();
	}

	public static void showOrderDetails(String string) {
		System.out.println(string);
		
	}
}
