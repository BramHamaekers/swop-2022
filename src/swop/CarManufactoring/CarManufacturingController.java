package swop.CarManufactoring;

import java.util.LinkedList;
import java.util.List;

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
	 * will try to advance the assemblyline + update the scheduler
	 * @param minutes that have passed
	 * @throws NotAllTasksCompleteException
	 */
	public void advanceAssembly(int minutes) throws NotAllTasksCompleteException {
		//there is time to finish another car + there are cars on the queue
		if(canFinishNewCar() && !this.getCarQueue().isEmpty()) {
			this.getScheduler().updateIterator();
			//TODO create a different .getNextScheduledCar method so i don't need to update the iterator before and after
			Car nextCar = this.getScheduler().getNextScheduledCar();
			this.getScheduler().updateIterator();
			this.assemblyLine.advance(nextCar, minutes);
			this.carQueue.remove(nextCar);
		}
		//else just advance
		else this.assemblyLine.advance(null, minutes);
		//update schedular time
		this.updateScheduleTime(minutes);

		// For every car in queue and workstation update the estimated completion time
		this.carQueue.forEach(car -> car.setEstimatedCompletionTime(scheduler.getEstimatedCompletionTime(car)));
		this.assemblyLine.getWorkStations().forEach(w -> {
			if (w.getCar() != null)
			w.getCar().setEstimatedCompletionTime(scheduler.getEstimatedCompletionTime(w.getCar()));
		});
	}

	/**
	 * Checks if a new car could be finished if it was added to the assemblyLine
	 * @return
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
