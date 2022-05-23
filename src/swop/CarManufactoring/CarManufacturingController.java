package swop.CarManufactoring;

import java.util.*;

import swop.Car.Car;
import swop.Car.CarOrder;
import swop.Exceptions.NotAllTasksCompleteException;
import swop.Listeners.StatisticsListener;
import swop.Listeners.TaskCompletedListener;
import swop.Miscellaneous.TimeStamp;

/**
 * A controller class which handles most of the operations fe. advancing the assembly line
 */
public class CarManufacturingController {

	private final Set<CarOrder> totalOrders;
	private final LinkedList<Car> carQueue;
	private final AssemblyLine assemblyLine;
	private final Scheduler scheduler;
	private final List<StatisticsListener> statisticsListeners = new ArrayList<>();
	private final TaskCompletedListener taskCompletedListener = () -> {
				try {advanceAssemblyAndUpdateSchedular();}
				catch (NotAllTasksCompleteException ignored) {}
				};


	/**
	 * Initialises the controller with an empty car queue, a scheduler and an assemblyLine with its workstations
	 */
	public CarManufacturingController() {
		this.totalOrders = new HashSet<>();
		this.carQueue = new LinkedList<>();
		this.assemblyLine = new AssemblyLine(this.createWorkStations());
		this.scheduler = new Scheduler(this);
	}

	/**
	 * Add statisticsListener to statisticsListeners
	 * @param statisticsListener the listener to add
	 */
	public void addListener(StatisticsListener statisticsListener) {
		//TODO: defensive?
		this.statisticsListeners.add(statisticsListener);
	}

	/**
	 * for all the listeners, update the statistics class
	 * @param car a given car that finished
	 */
	private void updateDelay(Car car) {
		if (car == null)
			throw new IllegalArgumentException("no finished car");
		statisticsListeners.forEach(l -> l.updateDelay(car));
	}

	/**
	 * Function creates all the workstations that are part of the assemblyLine as a linked list so that they have the
	 * right order.
	 * @return List of all the workstations of this assemblyLine
	 */
	private LinkedList<WorkStation> createWorkStations() {
		LinkedList<WorkStation> workStations = new LinkedList<>();
		workStations.add(new WorkStation("Car Body Post"));
		workStations.add(new WorkStation("Drivetrain Post"));
		workStations.add(new WorkStation("Accessories Post"));
		workStations.forEach(s -> s.addListener(this.taskCompletedListener));
		return workStations;
	}

	/**
	 * @return the assembly line for this controller
	 */
	public AssemblyLine getAssembly() {
		return this.assemblyLine;
	}
	
	/**
	 * Main method to advance the assembly line + update the scheduler.
	 * @throws NotAllTasksCompleteException if all available tasks are not completed
	 */
	public void advanceAssemblyAndUpdateSchedular() throws NotAllTasksCompleteException { 
		int maxWorkingMinutes = this.getLongestWorkTimeOfWorkStations();
		Car nextCar = this.getNextCarFromQueue(maxWorkingMinutes);
		Car finishedCar = this.advanceAssemblyLine(nextCar);
		this.updateTime(maxWorkingMinutes, finishedCar);
	}

	/**
	 * This method is the main method for updating all the time aspects based on passedMinutes 
	 * after a successful advance
	 * @param passedMinutes the minutes that have passed since last update
	 * @param finishedCar the car that has been finished or null if there is no finished car
	 */
	private void updateTime(int passedMinutes, Car finishedCar) {
		//Finalise the Delivery Time of a finished car
		if (finishedCar != null) this.setFinishedCarDeliveryTime(passedMinutes, finishedCar);
		//update scheduler
		this.updateScheduleTime(passedMinutes);
		// For every car in queue and workstation update the estimated completion time
		this.updateEstimatedCompletionTime();
	}

	/**
	 * This method will try to advance the assembly line
	 * @param nextCar Car the put on assembly line
	 * @return the finished car leaving the assembly line
	 * @throws NotAllTasksCompleteException if not all tasks are completed
	 */
	private Car advanceAssemblyLine(Car nextCar) throws NotAllTasksCompleteException {
		//first try to advance before removing since it can throw a NotAllTasksCompleteException
		Car finishedCar = this.assemblyLine.advance(nextCar);
		this.removeCarFromQueue(nextCar);
		return finishedCar;
	}

	/**
	 * if the parameter car is not null remove the car from the waiting queue
	 * @param car that needs to be removed
	 */
	private void removeCarFromQueue(Car car) {
		if (car != null) this.carQueue.remove(car);
	}

	/**
	 * Get the next from queue considering the car is able to finish and the scheduling algorithm
	 * @param maxWorkingMinutes The remaining time at one station for the car with the average highest working time
	 * @return the next car in the queue or null if there is no car to be completed
	 */
	private Car getNextCarFromQueue(int maxWorkingMinutes) {
		if(!this.canFinishNewCar(maxWorkingMinutes) || this.getCarQueue().isEmpty()) {	
			return null;
		}
		return this.getScheduler().getNextScheduledCar();
	}

	/**
	 * Will return total of minutes from the workstation that
	 * has been working the longest on there current car 
	 * @return maxWorkingMinutes
	 */
	private int getLongestWorkTimeOfWorkStations() {
		List<WorkStation> workStations = this.getAssembly().getWorkStations();
		List<Integer> workingTimes = workStations.stream()
				.map(WorkStation::getCurrentWorkingTime).toList();
		return Collections.max(workingTimes);
	}

	/**
	 * For every car in the car queue, update the estimated completion time according to the minutes passed.
	 */
	public void updateEstimatedCompletionTime() {
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
	private void setFinishedCarDeliveryTime(int minutes, Car finishedCar) {
		if(finishedCar == null) throw new IllegalArgumentException("Can't set the delivery time, car is null");
		if (!finishedCar.isCompleted()) throw new IllegalStateException("Car is not completed");
		finishedCar.setDeliveryTime(new TimeStamp(scheduler.getDay(), scheduler.getMinutes() + minutes));
		//update the delay map with the new finished car
		this.updateDelay(finishedCar);
	}

	/**
	 * Checks if a new car could be finished if it was added to the assemblyLine
	 * @param minutes the minutes that need to be added to the time before checking
	 * @return whether a new car can be finished in time
	 */
	private boolean canFinishNewCar(int minutes) {
		return this.getScheduler().canAddCarToAssemblyLine(minutes);
	}

	/**
	 * updates the scheduler.
	 * @param minutes minutes to add to the current time
	 */
	private void updateScheduleTime(int minutes) {
		this.getScheduler().addTime(minutes);
		//if all work is done for today, skip to next day
		if (!this.canFinishNewCar(0) && this.assemblyLine.isEmptyAssemblyLine()) {
			this.getScheduler().advanceDay();
			try {
				advanceAssemblyAndUpdateSchedular();
			} catch (NotAllTasksCompleteException ignored) {}
		}
		
	}

	/**
	 * add the cars of a specific order 2 the queue
	 * @param carOrder order added to the Queue
	 */
	public void addOrderToQueue(CarOrder carOrder) {
		if (carOrder == null)
			throw new IllegalArgumentException("car order is null");
		Car car = carOrder.getCar();
		if(car == null) throw new IllegalArgumentException("car is null");
		this.totalOrders.add(carOrder);
		this.carQueue.add(car);
		carOrder.setOrderTime(getScheduler().getTime());
		car.setEstimatedCompletionTime(getScheduler().getEstimatedCompletionTime(car));
		// if it is the only order in queue and the first spot is empty -> put it on the assembly line (if possible)
		try {
			advanceAssemblyAndUpdateSchedular();
		} catch (NotAllTasksCompleteException ignored) {
		}
	}

	/**
	 * Get the set of orders from the manufactoring controller
	 * @return the set of orders placed
	 */
	public Set<CarOrder> getTotalOrders(){
		return this.totalOrders;
	}

	/**
	 * Returns the scheduler associated with this controller
	 * @return this.scheduler
	 */
	public Scheduler getScheduler() {
		return this.scheduler;
	}

	/**
	 * returns copy of carqueue
	 * @return List.copyOf(this.carQueue)
	 */
	public List<Car> getCarQueue() {
		return List.copyOf(this.carQueue);
	}
}
