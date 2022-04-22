package swop.UI.Generators;

import swop.Car.CarOrder;
import swop.UI.Builders.FormBuilder;

import java.util.*;

public class GarageHolderGenerator extends UserGenerator {

    public void generateCarModels(FormBuilder builder, Set<String> carModels) {
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

    public void generateOrderingForm(FormBuilder builder, Map<String, List<String>> optionsMap, String carModel) {
        builder.appendTitle("Ordering form");
        builder.appendSubTitle(carModel);
        for (Map.Entry<String, List<String>> entry : optionsMap.entrySet()) {
            builder.inputInfo(String.format("%s: %s", entry.getKey(), optionListToString(entry.getValue())));
        }
        builder.endInfo();
    }

    public String optionListToString(List<String> options) {
        StringBuilder builder = new StringBuilder();
        ListIterator<String> it = options.listIterator();
        while (it.hasNext()) {
            builder.append(String.format("[%d] %s ", it.nextIndex(), it.next()));
        }
        return builder.toString();
    }

    public void generateOrderStatus(FormBuilder builder, Set<CarOrder> carOrders){
        builder.appendTitle("Orders");
        builder.appendSubTitle("Pending");

        carOrders.stream()
            .filter(o -> !o.isCompleted())
            .forEach(p ->{
                builder.inputInfo(String.format("Order: %s -> %s", p.getID(), p.getEstimatedCompletionTime()));
            });

        builder.appendSubTitle("Completed");
        Set<CarOrder> completedSet = new TreeSet<>();

        // Add to sorted set
        carOrders.stream()
                .filter(CarOrder::isCompleted)
                .forEach(completedSet::add);
        completedSet.forEach(c -> builder.inputInfo(String.format("Order: %s %s %s",
                c.getID(), c.getCompletionTime().get("day"), c.getCompletionTime().get("minutes"))));
        builder.endInfo();
    }


    public void generateEstimatedTime(FormBuilder builder, CarOrder order){
        builder.appendTitle("Estimated Completion Time");
        builder.inputInfo(order.getEstimatedCompletionTime());
        builder.endInfo();
    }
}
