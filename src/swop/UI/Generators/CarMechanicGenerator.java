package swop.UI.Generators;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.UI.Builders.FormBuilder;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class CarMechanicGenerator extends UserGenerator {

    //TODO: More descriptive name? + documentation
    public void generateMechanic(FormBuilder builder, List<WorkStation> workStations) {
        builder.appendTitle("Current Stations");
        ListIterator<WorkStation> w = workStations.listIterator();
        while (w.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", w.next().getName(), (w.nextIndex() - 1)));
        }
        builder.endInfo();
        builder.addOption("Select station", workStations.size());
    }

    public void generateWorkStationStatus(FormBuilder builder, String name, Set<Task> pendingTasks, Set<Task> finishedTasks) {
        builder.appendTitle(name);
        builder.appendSubTitle("Pending");
        if (pendingTasks != null) pendingTasks.forEach(t -> builder.inputInfo(t.getName()));
        builder.appendSubTitle("Finished");
        if (finishedTasks != null) finishedTasks.forEach(t -> builder.inputInfo(t.getName()));
    }
}
