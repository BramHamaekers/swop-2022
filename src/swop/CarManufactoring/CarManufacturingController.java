package swop.CarManufactoring;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import swop.Car.Car;
import swop.Car.CarOrder;
import swop.Exceptions.NotAllTasksCompleteException;

public class CarManufacturingController {

	private final LinkedList<Car> carQueue;
	private final AssemblyLine assemblyLine;
	private final Scheduler scheduler;
	
	
	
	public CarManufacturingController() {
		this.carQueue = new LinkedList<>();
		this.assemblyLine = new AssemblyLine(createWorkStations());
		this.scheduler = new Scheduler(this);
	}
	
	/**
	 * Function creates all the workstations that are part of the assemblyLine as a linked list so that they have the
	 * right order.
	 * @return LinkedList<WorkStation> of all the workstations of this assemblyLine
	 */
	private LinkedList<WorkStation> createWorkStations() {
		LinkedList<WorkStation> workStations = new LinkedList<>();
		workStations.add(new WorkStation("Car Body Post"));
		workStations.add(new WorkStation("Drivetrain Post"));
		workStations.add(new WorkStation("Accessories Post"));
		return workStations;
	}

	public AssemblyLine getAssembly() {
		return this.assemblyLine;
	}
	
	/**
	 * Will try to advance the assemblyline + update the scheduler.
	 * @param minutes that have passed
	 * @throws NotAllTasksCompleteException if all available tasks are not completed
	 */
	public void advanceAssembly(int minutes) throws NotAllTasksCompleteException {
		//there is time to finish another car + there are cars on the queue
		Car finishedCar = null;
		if(canFinishNewCar() && !this.getCarQueue().isEmpty()) {
			Car nextCar = this.getScheduler().getNextScheduledCar();
			finishedCar = this.assemblyLine.advance(nextCar);
			this.carQueue.remove(nextCar);
		}
		//else just advance
		else {
			finishedCar = this.assemblyLine.advance(null);
		}

		if (finishedCar != null) {
			setFinishedcarDeliverytime(minutes, finishedCar);
		}
		//update schedular time
		this.updateScheduleTime(minutes);

		// For every car in queue and workstation update the estimated completion time
		updateEstimatedCompletionTime();
	}

	/**
	 * For every car in the carqueue, update the estimated completiontime according to the minutes passed.
	 */
	private void updateEstimatedCompletionTime() {
		this.carQueue.forEach(car -> car.setEstimatedCompletionTime(scheduler.getEstimatedCompletionTime(car)));
		this.assemblyLine.getWorkStations().forEach(w -> {
			if (w.getCar() != null)
			w.getCar().setEstimatedCompletionTime(scheduler.getEstimatedCompletionTime(w.getCar()));
		});
	}

	/**
	 * Set the completion time of the finished car according to scheduler
	 * @param minutes the minutes passed in the last workstation rotation
	 * @param finishedCar the car to be finished
	 * @throws IllegalStateException if the car is not completed
	 */
	private void setFinishedcarDeliverytime(int minutes, Car finishedCar) {
		if (!finishedCar.isCompleted()){
			throw new IllegalStateException("Car is not completed");
		}
		finishedCar.setDeliveryTime(Map.of(
				"day", scheduler.getDay(),
				"minutes", scheduler.getMinutes()+ minutes
		));
	}

	/**
	 * Checks if a new car could be finished if it was added to the assemblyLine
	 * @return whether a new car can be finished in time
	 */
	private boolean canFinishNewCar() {
		return this.getScheduler().canAddCarToAssemblyLine();
	}

	/**
	 * updates the scheduler.
	 * @param minutes
	 */
	private void updateScheduleTime(int minutes) {
		this.getScheduler().addTime(minutes);
		//if all work is done for today, skip to next day
		if (!canFinishNewCar() && this.assemblyLine.isEmptyAssemblyLine()) {
			this.getScheduler().advanceDay();
		}
		
	}

	//this method is to fix bug for current calculation of AdvancedStatus of assembly line
	//TODO ??
	public List<String> getAdvancedStatus() {
		if(this.getCarQueue().isEmpty()) return this.assemblyLine.getAdvancedStatus(null);
		return this.assemblyLine.getAdvancedStatus(this.getScheduler().getNextScheduledCar());
	}

	/**
	 * add the cars of a specific order 2 the queue
	 * @param carOrder
	 */
	public void addOrderToQueue(CarOrder carOrder) {
		Car car = carOrder.getCar();
		if(car == null) throw new IllegalArgumentException("car is null");
		this.carQueue.add(car);
		car.setEstimatedCompletionTime(getScheduler().getEstimatedCompletionTime(car));
		
		
	}

	/**
	 * Returns the scheduler associated with this controller
	 * @return
	 */
	public Scheduler getScheduler() {
		return scheduler;
	}

	/**
	 * Returns how many cars are still waiting
	 * @return carQueue.size()
	 */
	public int getCarQueueSize() {
		return carQueue.size();
	}

	/**
	 * returns copy of carqueue
	 * @return List.copyOf(this.carQueue)
	 */
	public List<Car> getCarQueue() {
		return List.copyOf(this.carQueue);
	}
}
