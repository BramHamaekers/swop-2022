package swop.UI.Generators;

import swop.CarManufactoring.WorkStation;
import swop.UI.Builders.FormBuilder;

import java.util.List;
import java.util.ListIterator;

public class CarMechanicGenerator extends UserGenerator {
    public void generateMechanic(FormBuilder builder, List<WorkStation> workStations) {
        builder.appendTitle("Current Stations");
        ListIterator<WorkStation> w = workStations.listIterator();
        while (w.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", w.next().getName(), (w.nextIndex() - 1)));
        }
        builder.endInfo();
        builder.addOption("Select station", workStations.size());
    }
}
