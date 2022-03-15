package swop.CarManufactoring;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import swop.Parts.Part;

public class Car {
	private Set<Task> uncompletedTasks;
    private CarModel carModel;
    
    public Car(CarModel model){
		if (model == null)
			throw new IllegalArgumentException("carmodel is null");
        this.setCarModel(model);  
		this.initiateUncompletedTasks();
    }

	public void completeTask(Task task) {
		if (task == null)
			throw new IllegalArgumentException("task is null");
		if (!uncompletedTasks.contains(task))
			throw new IllegalArgumentException("task not in todo list");
		for(Task t: uncompletedTasks) { //moet het met een forloop doen, want anders error door subclass bullshit.
			if(task.getName() == t.getName()) {
				List<Task> temp = List.copyOf(uncompletedTasks);
				temp.remove(t); //TODO fix dit probleem, wilt niet element verweideren van set
				uncompletedTasks = Set.copyOf(temp);
			}
			break;
		}
	}

	public boolean isCompleted() {
		return getUncompletedTasks().isEmpty();
	}

	public Set<Task> getUncompletedTasks() {
		return uncompletedTasks;
	}

	private void initiateUncompletedTasks() {
		uncompletedTasks = Task.getAllTasks();
	}


	public CarModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}
	public String getValueOfPart(Part part) {
		if (this.carModel == null) {
			throw new IllegalArgumentException("The car has no carmodel");
		}
		return this.getCarModel().getValueOfPart(part);
	}
	public  Map<String, String> getPartsMap() {
		if (this.carModel == null) {
			throw new IllegalArgumentException("The car has no carmodel");
		}
		return this.getCarModel().getPartsMap();
	}
}
