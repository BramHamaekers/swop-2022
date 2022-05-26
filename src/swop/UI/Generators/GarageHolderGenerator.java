package swop.UI.Generators;

import swop.Car.CarModel.CarModel;
import swop.Car.CarOrder;
import swop.UI.Builders.FormBuilder;

import java.util.*;

/**
 * A generator for all the output of GarageHolder
 */
public class GarageHolderGenerator extends UserGenerator {

    /**
     * Generate the output text for all the current carModels
     * @param builder the builder used in displaying the carModels
     * @param carModels the carModels to display
     */
    public void generateCarModels(FormBuilder builder, List<String> carModels) {
        builder.appendTitle("Car Models");
        Iterator<String> it = carModels.iterator();
        int i = 0;
        while (it.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", it.next(), i));
            i++;
        }
        builder.endInfo();
        builder.addOption("Select model", carModels.size());
    }

    /**
     * Generate the output text for the ordering form for a specific carModel
     * @param builder the builder used in displaying the options
     * @param optionsMap the possible options for this carmodel
     * @param carModel the chosen carmodel
     */
    public void generateOrderingForm(FormBuilder builder, Map<String, List<String>> optionsMap, String carModel) {
        builder.appendTitle("Ordering form");
        builder.appendSubTitle(carModel);
        for (Map.Entry<String, List<String>> entry : optionsMap.entrySet()) {
            builder.inputInfo(String.format("%s: %s", entry.getKey(), optionListToString(entry.getValue())));
        }
        builder.endInfo();
    }

    /**
     * Generate the output text for a list of options
     * @param options a list of options as a string
     * @return a string containing the output text
     */
    public String optionListToString(List<String> options) {
        StringBuilder builder = new StringBuilder();
        ListIterator<String> it = options.listIterator();
        while (it.hasNext()) {
            builder.append(String.format("[%d] %s ", it.nextIndex(), it.next()));
        }
        return builder.toString();
    }

    /**
     * Generates an overview of all carOrders
     * @param builder the builder used in displaying the carOrders
     * @param carOrders a set of all carorders, finished or pending
     */
    public void generateOrderStatus(FormBuilder builder, Set<CarOrder> carOrders){
        builder.appendTitle("Orders");
        builder.appendSubTitle("Pending");

        Set<CarOrder> pendingSet = new TreeSet<>();
        carOrders.stream()
                .filter(o -> !o.isCompleted())
                .forEach(pendingSet::add);

        pendingSet.stream()
            .filter(o -> !o.isCompleted())
            .forEach(p -> builder.inputInfo(String.format("Order: %s -> %s",
                    p.getID(), p.getEstimatedCompletionTime().toString())));

        builder.appendSubTitle("Completed");
        Set<CarOrder> completedSet = new TreeSet<>();

        // Add to sorted set
        carOrders.stream()
                .filter(CarOrder::isCompleted)
                .forEach(completedSet::add);
        completedSet.forEach(c -> builder.inputInfo(String.format("Order: %s -> %s",
                c.getID(), c.getCompletionTime().toString())));
        builder.endInfo();
    }

    /**
     * generate an estimated time for this order
     * @param builder the builder used in displaying the estimated time
     * @param order a carorder
     */
    public void generateEstimatedTime(FormBuilder builder, CarOrder order){
        builder.appendTitle("Estimated Completion Time");
        builder.inputInfo(order.getEstimatedCompletionTime().toString());
        builder.endInfo();
    }
}
