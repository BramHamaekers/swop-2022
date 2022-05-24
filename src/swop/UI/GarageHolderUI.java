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

//	/**
//	 * Ask user a Yes/No question and return the response
//	 * @param action the question to ask
//	 * @return "y" if yes, "n" if no
//	 * @throws CancelException when the user types 'Cancel'
//	 */
//	public static String indicateYesNo(String action) throws CancelException {
//		return UI.indicateYesNo(action);
//	}
	
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
//	public static int indicateCarModel(Set<String> carModels) throws CancelException {
//		DisplayStatus builder = new DisplayStatus();
//		garageHolderGenerator.generateCarModels(builder, carModels);
//		System.out.println(builder.getDisplay());
//		return scanner.scanNextLineOfTypeInt(0,carModels.size());
//	}

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
}
