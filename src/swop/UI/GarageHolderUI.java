package swop.UI;

import swop.Car.CarOrder;
import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.GarageHolderGenerator;

import java.util.*;
import java.util.List;

/**
 * Class handles all UI interaction with the user for GarageHolder
 */
public class GarageHolderUI implements UI {

	private static final GarageHolderGenerator garageHolderGenerator = new GarageHolderGenerator();

	/**
	 * welcomes user with a welcome message
	 * @param id string of the id of the user
	 */
	public static void init(String id) {
		System.out.println("Welcome Garage Holder: " + id + " (You can cancel any action by typing: CANCEL)");
	}

	/**
	 * Prints an error message
	 * @param e the error message to print
	 */
	public static void printError(String e) {
		UI.printError(e);
	}

	/**
	 * Displays all orders ordered, splits unfinished and finished cars
	 * @param carOrders a set of all the carorders
	 */
	public static void displayOrders(Set<CarOrder> carOrders) {
		if (carOrders == null) {
			UI.printErrorln("No carOrders placed yet.");
			return;
		}
		DisplayStatus builder = new DisplayStatus();
		garageHolderGenerator.generateOrderStatus(builder, carOrders);
		System.out.println(builder.getDisplay());
	}

	/**
	 * Ask user a Yes/No question and return the response
	 * @param action the question to ask
	 * @return "y" if yes, "n" if no
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static String indicateYesNo(String action) throws CancelException {
		return UI.indicateYesNo(action);
	}
	
	/**
	 * Asks which action the user wants to take
	 * @return int indicating the chosen action
	 * @param actions available actions for the user
	 * @param question an initial question for which to display options
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
	 * Displays the ordering from a given list of components and its options
	 * @param name the name of the carmodel
	 * @param optionsMap list of components and its options
	 */
	public static void displayOrderingForm(Map<String, List<String>> optionsMap, String name) {
		DisplayStatus builder = new DisplayStatus();
		garageHolderGenerator.generateOrderingForm(builder, optionsMap, name);
		System.out.println(builder.getDisplay());
	}

	/**
	 * get the input from the user
	 * @param leftBound the smallest available answer
	 * @param rightBound the highest available answer
	 * @param option the option to display
	 * @return the input from the user
	 * @throws CancelException CancelException when the user types 'Cancel'
	 */
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
		System.out.println(builder.getDisplay());
	}

	/**
	 * Prints an empty line to the UI
	 */
    public static void printEmptyLine() {
    	System.out.println();
    }

	/**
	 * Lets the user input an orderID
	 * @return the orderID that the user gave as input
	 * @throws CancelException when the user types 'Cancel'
	 */
	public static String selectOrderID() throws CancelException {
		System.out.println("Input an orderID: ");
		return scanner.scanNextLineOfTypeString();
	}

	/**
	 * prints the details of an order
	 * @param string details of an order
	 */
	public static void showOrderDetails(String string) {
		System.out.println(string);
		
	}
}
