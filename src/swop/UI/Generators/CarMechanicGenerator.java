package swop.UI.Generators;

import swop.CarManufactoring.Task;
import swop.CarManufactoring.WorkStation;
import swop.UI.Builders.FormBuilder;

import java.util.List;
import java.util.ListIterator;

/**
 * A generator for all the output of CarMechanic
 */
public class CarMechanicGenerator extends UserGenerator {

    /**
     * Given a list of workstations display this list
     * @param builder the builder used in displaying the workStations
     * @param workStations the workstations to display
     */
    public void generateStationList(FormBuilder builder, List<WorkStation> workStations) {
        builder.appendTitle("Current Stations");
        ListIterator<WorkStation> w = workStations.listIterator();
        while (w.hasNext()) {
            builder.inputInfo(String.format("%s [%s]", w.next().getName(), (w.nextIndex() - 1)));
        }
        builder.endInfo();
      //builder.addOption("Select station", workStations.size()); // breaks the use case tests
    }

    /**
     * Given a list of tasks of a workstation, display the status of these tasks
     * @param builder the builder used in displaying the status of tasks
     * @param name name of the workstation
     * @param pendingTasks list of pending tasks on a workstation
     * @param finishedTasks list of finished tasks on a workstation
     */
    public void generateWorkStationStatus(FormBuilder builder, String name, List<Task> pendingTasks, List<Task> finishedTasks) {
        builder.appendTitle(name);
        builder.appendSubTitle("Pending");
        if (pendingTasks != null) pendingTasks.forEach(t -> builder.inputInfo(t.getName()));
        builder.appendSubTitle("Finished");
        if (finishedTasks != null) finishedTasks.forEach(t -> builder.inputInfo(t.getName()));
    }

    /**
     * Given a list of availableTasks, display these tasks
     * @param builder the builder used in displaying the tasks
     * @param taskList list of available Tasks to display
     */
    public void generateAvailableTasks(FormBuilder builder, List<Task> taskList){
        builder.appendTitle("Available tasks");
        ListIterator<Task> it = taskList.listIterator();
        // cant be empty check CarMechanic.selectTask
        while (it.hasNext()){
            builder.inputInfo(String.format("%s [%d] ",it.next().getName(), (it.nextIndex()-1)));
        }
        builder.endInfo();
    }

    /**
     * Given a list of info, display this info
     * @param builder the builder used in displaying the info
     * @param info the info to display
     */
    public void generateTaskInfo(FormBuilder builder, List<String> info){
        builder.appendSubTitle("Info For Task");
        for (String s : info) {
            builder.inputInfo(s);
        }
        builder.appendSubTitle("-------------");
        //TODO: kind of janky need to rewrite
        builder.inputInfo(String.format("Finished -> Press Enter"));
    }
}
