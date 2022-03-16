package swop.CarManufactoring;

import java.util.*;

import swop.Parts.Part;

public class Car {
	private Set<Task> uncompletedTasks;
    private CarModel carModel;
    private CarOrder order;
    
    public Car(CarModel model, CarOrder order){
		this.setOrder(order);
        this.setCarModel(model);  
		this.initiateUncompletedTasks();
    }

	public void completeTask(Task task) {
		if (task == null)
			throw new IllegalArgumentException("task is null");
		if (!uncompletedTasks.contains(task))
			throw new IllegalArgumentException("task not in todo list");
		for(Task t: uncompletedTasks) { //moet het met een forloop doen, want anders error door subclass bullshit.
			if (Objects.equals(task.getName(), t.getName())) {
				this.uncompletedTasks.remove(t);
				break;
			}
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
		if (carModel == null)
			throw new IllegalArgumentException("car model is null");
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

	public CarOrder getOrder() {
		return order;
	}

	public void setOrder(CarOrder order) {
		if (order == null)
			throw new IllegalArgumentException("Order is null");
		this.order = order;
	}
}
