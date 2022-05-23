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
        this.setGarageHolder(gh);
        Set<CarOrder> orders = this.garageHolder.getCarOrders();
        this.displayOrders(this.garageHolder.getCarOrders());
        List<String> actions = Arrays.asList("Place new order", "Check order details", "Exit");
        int action = UI.selectAction(garageHolderGenerator,actions, "What would you like to do?");

        switch (action) {
            case 0 -> this.generateOrder();
            case 1 -> {
                if (orders.isEmpty()) {
                    GarageHolderUI.printError("No orders available to check!");
                    this.run(gh);
                }
//                else this.checkOrderDetails();
            }
            case 2 -> {
                // Do Nothing
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + action);
        }
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

    /**
     * Will handle all the steps for generating a new valid order.
     */
    private void generateOrder() {
        try {
            List<Class<? extends CarModel>> models =  this.garageHolder.getModels();
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
            GarageHolderUI.printEmptyLine();

            CarOrder order = this.garageHolder.placeOrder(carConfig, model);

            // Create & Place Order
            displayEstimatedTime(order);
        } catch (CancelException e) {
            e.printMessage();
        }
    }

    /**
     * Ask which carModel the user wants to order
     * @param carModels available carModels to choose from
     * @return int indicating the chosen carModel
     * @throws CancelException when the user types 'Cancel'
     */
    private static int indicateCarModel(List<Class<? extends CarModel>> carModels) throws CancelException {
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
}
