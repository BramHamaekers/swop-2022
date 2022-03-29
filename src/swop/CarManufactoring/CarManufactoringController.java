package swop.CarManufactoring;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import swop.Exceptions.NotAllTasksCompleteException;

public class CarManufactoringController {
	private LinkedList<Car> carQueue; //msschien static maken, dan hoeft de schedular this niet meer bij te houden
	private AssemblyLine assemblyLine;
	private final Scheduler scheduler;
	
	
	
	public CarManufactoringController() {
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
	
	/**
	 * ads a new car to waiting car queue
	 * @param car
	 */
	public void addToCarQueue(Car car) {
		if(car == null) throw new IllegalArgumentException("car is null");
		this.carQueue.add(car);
		
	}
	
	/**
	 * Returns how many cars are still waiting
	 * @return carQueue.size()
	 */
	public int getCarQueuesize() {
		return carQueue.size();
	}

	public List<Car> getCarQueue() {
		return List.copyOf(this.carQueue);
	}
	public AssemblyLine getAssembly() {
		return this.assemblyLine;
	}
	
	public void advanceAssembly(int minutes) throws NotAllTasksCompleteException {
		if(!carQueue.isEmpty() && canFinishNewCar()) {
			this.assemblyLine.advance(carQueue.removeFirst(), minutes);
			this.updateScheduleTime(minutes);
		}
		if (!canFinishNewCar() && this.assemblyLine.isEmptyAssemblyLine()) {
			this.scheduler.advanceDay();
		}
		
	}

	private boolean canFinishNewCar() {
		return this.scheduler.canAddCarToAssemblyLine();
	}

	private void updateScheduleTime(int minutes) {
		this.scheduler.addTime(minutes);
		
	}

	public List<String> getAdvancedStatus() {
		return this.assemblyLine.getAdvancedStatus(this.carQueue.getFirst());
	}

	public void addOrderToQueue(CarOrder carOrder) {
		Car car = carOrder.getCar();
		if(car == null) throw new IllegalArgumentException("car is null");
		car.setEstimatedCompletionTime(scheduler.getEstimatedCompletionTime());
		this.carQueue.add(car);
		
		
	}
	
}
