package swop.UI;

import swop.Car.CarModel.CarModel;
import swop.Car.CarOrder;
import swop.Exceptions.CancelException;
import swop.UI.Builders.DisplayStatus;
import swop.UI.Generators.GarageHolderGenerator;
import swop.Users.GarageHolder;

import java.util.*;

import static swop.UI.UI.scanner;

public class TempGarUI {

    private static final GarageHolderGenerator garageHolderGenerator = new GarageHolderGenerator();

    private static final List<String> actions = Arrays.asList("Place new order", "Check order details", "Exit");

    private GarageHolder garageHolder;

    public void setGarageHolder(GarageHolder garageHolder){
        this.garageHolder = garageHolder;
    }

    /**
     * order a new car
     */
    public void run(GarageHolder gh) throws CancelException {
        if (gh == null)
            throw new IllegalArgumentException("TODO");

        System.out.println("Welcome Garage Holder, (You can cancel any action by typing: CANCEL)");
        this.setGarageHolder(gh);
        Set<CarOrder> orders = this.garageHolder.getOrders();
        this.displayOrders(orders);

        selectAction();
    }

    private void selectAction() throws CancelException {
        int action = UI.selectAction(garageHolderGenerator, actions,"What would you like to do?");
        switch (action) {
            case 0 -> this.generateOrder();
            case 1 -> {
                if (this.garageHolder.getOrders().isEmpty()) {
                    printError("No orders available to check!");
                    this.selectAction();
                }
                else this.checkOrderDetails();
            }
            case 2 -> {
                // Do Nothing
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    /**
     * Will handle all the steps for generating a new valid order.
     */
    private void generateOrder() {
        try {
            List<String> models =  this.garageHolder.getModels();
            // Select Model
            int choice = indicateCarModel(models);
            CarModel model = this.garageHolder.createCarModel(choice);

            // Ordering From
            SortedMap<String, List<String>> options = this.garageHolder.getValidOptions(model);
            displayOrderingForm(options, model.getName());

            Map<String,Integer> carConfig = new HashMap<>();
            for (var entry : options.entrySet()) {
                int option = askOption(0, entry.getValue().size(), entry.getKey());
                carConfig.put(entry.getKey(), option);
            }
            printEmptyLine();

            try{
                CarOrder order = this.garageHolder.placeOrder(carConfig, model);
                displayEstimatedTime(order);
            }catch (IllegalArgumentException e){
                UI.printError(e.getMessage());
            }
        } catch (CancelException e) {
            e.printMessage();
        }
    }

    /**
     * Handles the selection of the order to view the details
     * @throws CancelException when the user wants to cancel viewing details of orders
     */
    private void checkOrderDetails() throws CancelException {
        String question = "n";
        do {
            Set<CarOrder> orders = this.garageHolder.getOrders();
            if (question.equals("y")){
                displayOrders(orders);
            }
            String orderID = selectOrderID();;
            while (!isValidOrderID(orders, orderID)){
                printError("Please provide a valid orderID");
                orderID = selectOrderID();
            }

            CarOrder carOrder = getOrderFromID(orders, orderID);

            showOrderDetails(carOrder.toString());
            question = UI.indicateYesNo("Would you like to view another order?");
        } while (question.equals("y"));

    }

    /**
     * Ask which carModel the user wants to order
     * @param carModels available carModels to choose from
     * @return int indicating the chosen carModel
     * @throws CancelException when the user types 'Cancel'
     */
    private static int indicateCarModel(List<String> carModels) throws CancelException {
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
    private static void displayOrderingForm(Map<String, List<String>> optionsMap, String name) {
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
    private static int askOption (int leftBound, int rightBound, String option) throws CancelException {
        System.out.print("Choose " + option + ": ");
        return scanner.scanNextLineOfTypeInt(leftBound, rightBound);
    }

    /**
     * Prints the given estimated completion time.
     * @param order the estimated completion time to be displayed
     */
    private static void displayEstimatedTime(CarOrder order) {
        DisplayStatus builder = new DisplayStatus();
        garageHolderGenerator.generateEstimatedTime(builder, order);
        System.out.println(builder.getDisplay());
    }

    /**
     * Checks whether the given orderID is a valid ID
     * @param orderID the given orderID
     * @return whether the orderID is valid
     */
    private boolean isValidOrderID(Set<CarOrder> orders, String orderID) {
        return orders.stream().anyMatch(o -> o.getID().equalsIgnoreCase(orderID));
    }

    /**
     * Returns a carOrder from this user given its orderID
     * @param orderID the given orderID
     * @return the CarOrder with the orderID
     */
    private CarOrder getOrderFromID(Set<CarOrder> orders, String orderID) {
        return orders.stream()
                .filter(o -> o.getID().equalsIgnoreCase(orderID))
                .findFirst().orElse(null);
    }


    /**
     * Prints an empty line to the UI
     */
    private static void printEmptyLine() {
        System.out.println();
    }

    /**
     * Lets the user input an orderID
     * @return the orderID that the user gave as input
     * @throws CancelException when the user types 'Cancel'
     */
    private static String selectOrderID() throws CancelException {
        System.out.println("Input an orderID: ");
        return scanner.scanNextLineOfTypeString();
    }

    /**
     * Prints an error message
     * @param e the error message to print
     */
    private static void printError(String e) {
        UI.printError(e);
    }

    /**
     * prints the details of an order
     * @param string details of an order
     */
    private static void showOrderDetails(String string) {
        System.out.println(string);
    }

    /**
     * Displays all orders ordered, splits unfinished and finished cars
     * @param carOrders a set of all the carorders
     */
    private void displayOrders(Set<CarOrder> carOrders) {
        if (carOrders == null) {
            UI.printErrorln("No carOrders placed yet.");
            return;
        }
        DisplayStatus builder = new DisplayStatus();
        garageHolderGenerator.generateOrderStatus(builder, carOrders);
        System.out.println(builder.getDisplay());
    }
}
