package swop.UI.Generators;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.UI.Builders.FormBuilder;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class CarMechanicGenerator extends UserGenerator {

    //TODO: More descriptive name? + documentation
    public void generateStationList(FormBuilder builder, List<WorkStation> workStations) {
        builder.appendTitle("Current Stations");
        ListIterator<WorkStation> w = workStations.listIterator();
        while (w.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", w.next().getName(), (w.nextIndex() - 1)));
        }
        builder.endInfo();
      //builder.addOption("Select station", workStations.size()); // breaks the use case tests
    }

    public void generateWorkStationStatus(FormBuilder builder, String name, List<Task> pendingTasks, List<Task> finishedTasks) {
        builder.appendTitle(name);
        builder.appendSubTitle("Pending");
        if (pendingTasks != null) pendingTasks.forEach(t -> builder.inputInfo(t.getName()));
        builder.appendSubTitle("Finished");
        if (finishedTasks != null) finishedTasks.forEach(t -> builder.inputInfo(t.getName()));
    }

    public void generateAvailableTasks(FormBuilder builder, List<Task> taskList){
        builder.appendTitle("Available tasks");
        ListIterator<Task> it = taskList.listIterator();
        // cant be empty check CarMechanic.selectTask
        while (it.hasNext()){
            builder.inputInfo(String.format("%s [%d] ",it.next().getName(), (it.nextIndex()-1)));
        }
        builder.endInfo();
    }

    public void generateTaskInfo(FormBuilder builder, List<String> info){
        builder.appendSubTitle("Info For Task");
        for (String s : info) {
            builder.inputInfo(s);
        }
        //TODO: kind of janky need to rewrite
        builder.addOption("Finished?", 1);
    }
}
